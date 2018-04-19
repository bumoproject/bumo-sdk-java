package org.bumo.sdk.core.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bumo.sdk.core.adapter.bc.RpcService;
import org.bumo.sdk.core.balance.NodeManager;
import org.bumo.sdk.core.balance.RpcServiceLoadBalancer;
import org.bumo.sdk.core.balance.model.RpcServiceConfig;
import org.bumo.sdk.core.event.EventBusService;
import org.bumo.sdk.core.event.SimpleEventBusService;
import org.bumo.sdk.core.event.bottom.BlockchainMqHandler;
import org.bumo.sdk.core.event.bottom.TxFailManager;
import org.bumo.sdk.core.event.bottom.TxMqHandleProcess;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.operation.BcOperation;
import org.bumo.sdk.core.pool.SponsorAccountPoolManager;
import org.bumo.sdk.core.pool.defaults.DefaultSponsorAccountConfig;
import org.bumo.sdk.core.pool.defaults.DefaultSponsorAccountFactory;
import org.bumo.sdk.core.seq.AbstractSequenceManager;
import org.bumo.sdk.core.seq.SimpleSequenceManager;
import org.bumo.sdk.core.seq.redis.DistributedLock;
import org.bumo.sdk.core.seq.redis.RedisClient;
import org.bumo.sdk.core.seq.redis.RedisConfig;
import org.bumo.sdk.core.seq.redis.RedisSequenceManager;
import org.bumo.sdk.core.spi.BcOperationService;
import org.bumo.sdk.core.spi.BcOperationServiceImpl;
import org.bumo.sdk.core.spi.BcQueryService;
import org.bumo.sdk.core.spi.BcQueryServiceImpl;
import org.bumo.sdk.core.transaction.TransactionContent;
import org.bumo.sdk.core.transaction.model.Signature;
import org.bumo.sdk.core.transaction.support.RedisTransactionContentSupport;
import org.bumo.sdk.core.transaction.support.TransactionContentSupport;
import org.bumo.sdk.core.transaction.sync.TransactionSyncManager;
import org.bumo.sdk.core.utils.PropUtil;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Providing basic configuration capabilities
 */
public class SDKEngine{

    private BcOperationService operationService;
    private BcQueryService queryService;
    private NodeManager nodeManager;
    private RpcService rpcService;
    private EventBusService eventBusService;
    private AbstractSequenceManager sequenceManager;
    private TransactionSyncManager transactionSyncManager;
    private SponsorAccountPoolManager sponsorAccountPoolManager;
    private SDKProperties sdkProperties = new SDKProperties();
    
    volatile private static SDKEngine instance = null;
    private SDKEngine(){}
    public static SDKEngine getInstance() {
    	try {  
    		synchronized(SDKEngine.class){
    			if(instance == null){
    				instance = new SDKEngine();
    				instance.configSdk();
    			}
    		}
    	} catch(Exception e)  {
    		// ignore ..
    		e.printStackTrace();
    	}
    	return instance;
    }

    private SDKEngine configSdk() throws SdkException{
    	
    	try {
    		sdkProperties = PropUtil.newInstanceByConf( SDKProperties.class,"/config.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
        // Parsing native configuration parameters
        List<RpcServiceConfig> rpcServiceConfigList = Stream.of(sdkProperties.getIps().split(","))
                .map(ip -> {
                    if (!ip.contains(":") || ip.length() < 5) {
                        return null;
                    }
                    return new RpcServiceConfig(ip.split(":")[0], Integer.valueOf(ip.split(":")[1]));
                })
                .filter(Objects:: nonNull).collect(Collectors.toList());

        // 1 Configure nodeManager
        nodeManager = new NodeManager(rpcServiceConfigList);

        // 2 Configure rpcService
        rpcService = new RpcServiceLoadBalancer(rpcServiceConfigList, nodeManager);

        // 3 Configure an internal message bus
        eventBusService = new SimpleEventBusService();

        // 4 Configure MQ and supporting facilities can configure multiple node monitoring, and receive any monitoring results can be processed
        TxFailManager txFailManager = new TxFailManager(rpcService, eventBusService);


        // Configure transactionSyncManager
        transactionSyncManager = new TransactionSyncManager();
        transactionSyncManager.init();

        sponsorAccountPoolManager = new SponsorAccountPoolManager(new DefaultSponsorAccountFactory(new DefaultSponsorAccountConfig(){
            @Override
            public List<BcOperation> provideBcOperations(String address) throws SdkException{
                return super.provideBcOperations(address);
            }

            @Override
            public List<Signature> provideSignature() throws SdkException{
                return super.provideSignature();
            }
        }));

        TxMqHandleProcess mqHandleProcess = new TxMqHandleProcess(txFailManager, eventBusService, transactionSyncManager, sponsorAccountPoolManager);
        for (String uri : sdkProperties.getEventUtis().split(",")) {
            new BlockchainMqHandler(uri, mqHandleProcess, eventBusService).init();
        }

        // 5 Configure seqManager
        RedisClient redisClient = null;
        if (sdkProperties.isRedisSeqManagerEnable()) {
            // use of redis
            RedisConfig redisConfig = new RedisConfig(sdkProperties.getHost(), sdkProperties.getPort(), sdkProperties.getPassword(), sdkProperties.getRedisDatabase());

            List<RedisConfig> redisConfigs = new ArrayList<>();
            redisConfigs.add(redisConfig);
            redisClient = new RedisClient(redisConfigs);
            redisClient.init();
            DistributedLock distributedLock = new DistributedLock(redisClient.getPool());

            sequenceManager = new RedisSequenceManager(rpcService, redisClient, distributedLock);
        } else {
            // use of memory
            sequenceManager = new SimpleSequenceManager(rpcService);
            sequenceManager.init();
        }


        // 7 Configure event bus
        eventBusService.addEventHandler(nodeManager);
        eventBusService.addEventHandler(txFailManager);
        eventBusService.addEventHandler(sequenceManager);
        eventBusService.addEventHandler(transactionSyncManager);
        eventBusService.addEventHandler(sponsorAccountPoolManager);

        // 8 初始化spi
        BcOperationService operationService = new BcOperationServiceImpl(sequenceManager, rpcService, transactionSyncManager, nodeManager, txFailManager, sponsorAccountPoolManager);
        /**
         * fix:屏蔽掉账户池的操作
         *
         * if (sdkProperties.isAccountPoolEnable()) {
            sponsorAccountPoolManager.initPool(operationService, sdkProperties.getAddress(), sdkProperties.getPublicKey(), sdkProperties.getPrivateKey(), sdkProperties.getSize(), sdkProperties.getPoolFilepath(), sdkProperties.getMark());
        }**/

        if (sdkProperties.isRedisSeqManagerEnable()) {
            TransactionContentSupport redisSupport = new RedisTransactionContentSupport(redisClient, operationService);
            TransactionContent.switchSupport(redisSupport);
        }

        BcQueryService queryService = new BcQueryServiceImpl(rpcService);

        this.operationService = operationService;
        this.queryService = queryService;
        
        return this;
    }

    public BcOperationService getOperationService(){
        return operationService;
    }

    public BcQueryService getQueryService(){
        return queryService;
    }

    public NodeManager getNodeManager(){
        return nodeManager;
    }

    public RpcService getRpcService(){
        return rpcService;
    }

    public EventBusService getEventBusService(){
        return eventBusService;
    }

    public AbstractSequenceManager getSequenceManager(){
        return sequenceManager;
    }

    public TransactionSyncManager getTransactionSyncManager(){
        return transactionSyncManager;
    }

    public SponsorAccountPoolManager getSponsorAccountPoolManager(){
        return sponsorAccountPoolManager;
    }
	public SDKProperties getSdkProperties() {
		return sdkProperties;
	}
    
}
