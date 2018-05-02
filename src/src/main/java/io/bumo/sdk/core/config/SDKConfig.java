package io.bumo.sdk.core.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.bumo.sdk.core.adapter.bc.RpcService;
import io.bumo.sdk.core.balance.NodeManager;
import io.bumo.sdk.core.balance.RpcServiceLoadBalancer;
import io.bumo.sdk.core.balance.model.RpcServiceConfig;
import io.bumo.sdk.core.event.EventBusService;
import io.bumo.sdk.core.event.SimpleEventBusService;
import io.bumo.sdk.core.event.bottom.BlockchainMqHandler;
import io.bumo.sdk.core.event.bottom.TxFailManager;
import io.bumo.sdk.core.event.bottom.TxMqHandleProcess;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.operation.BcOperation;
import io.bumo.sdk.core.pool.SponsorAccountPoolManager;
import io.bumo.sdk.core.pool.defaults.DefaultSponsorAccountConfig;
import io.bumo.sdk.core.pool.defaults.DefaultSponsorAccountFactory;
import io.bumo.sdk.core.seq.AbstractSequenceManager;
import io.bumo.sdk.core.seq.SimpleSequenceManager;
import io.bumo.sdk.core.seq.redis.DistributedLock;
import io.bumo.sdk.core.seq.redis.RedisClient;
import io.bumo.sdk.core.seq.redis.RedisConfig;
import io.bumo.sdk.core.seq.redis.RedisSequenceManager;
import io.bumo.sdk.core.spi.OperationService;
import io.bumo.sdk.core.spi.OperationServiceImpl;
import io.bumo.sdk.core.spi.QueryService;
import io.bumo.sdk.core.spi.QueryServiceImpl;
import io.bumo.sdk.core.transaction.TransactionContent;
import io.bumo.sdk.core.transaction.model.Signature;
import io.bumo.sdk.core.transaction.support.RedisTransactionContentSupport;
import io.bumo.sdk.core.transaction.support.TransactionContentSupport;
import io.bumo.sdk.core.transaction.sync.TransactionSyncManager;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Providing basic configuration capabilities
 */
public class SDKConfig{

    private OperationService operationService;
    private QueryService queryService;
    private NodeManager nodeManager;
    private RpcService rpcService;
    private EventBusService eventBusService;
    private AbstractSequenceManager sequenceManager;
    private TransactionSyncManager transactionSyncManager;
    private SponsorAccountPoolManager sponsorAccountPoolManager;
    
    public static boolean initBalanceEnable;

    public void configSdk(SDKProperties sdkProperties) throws SdkException{

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


        //  Configure transactionSyncManager
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
            // Use of memory
            sequenceManager = new SimpleSequenceManager(rpcService);
            sequenceManager.init();
        }


        // 7 Configure event bus
        eventBusService.addEventHandler(nodeManager);
        eventBusService.addEventHandler(txFailManager);
        eventBusService.addEventHandler(sequenceManager);
        eventBusService.addEventHandler(transactionSyncManager);
        eventBusService.addEventHandler(sponsorAccountPoolManager);

        // 8 init spi
        OperationService operationService = new OperationServiceImpl(sequenceManager, rpcService, transactionSyncManager, nodeManager, txFailManager, sponsorAccountPoolManager);
        
        //9 initBalanceEnable
        SDKConfig.initBalanceEnable = sdkProperties.isInitBalanceEnable();
        
        /**
         * fix:Remove the optimizations for concurrency
         * TODO：A new optimization scheme should be perfected
         *
        if (sdkProperties.isAccountPoolEnable()) {
            sponsorAccountPoolManager.initPool(operationService, sdkProperties.getAddress(), sdkProperties.getPublicKey(), sdkProperties.getPrivateKey(), sdkProperties.getSize(), sdkProperties.getPoolFilepath(), sdkProperties.getMark());
        }**/

        if (sdkProperties.isRedisSeqManagerEnable()) {
            TransactionContentSupport redisSupport = new RedisTransactionContentSupport(redisClient, operationService);
            TransactionContent.switchSupport(redisSupport);
        }

        QueryService queryService = new QueryServiceImpl(rpcService);

        this.operationService = operationService;
        this.queryService = queryService;
    }

    public OperationService getOperationService(){
        return operationService;
    }

    public QueryService getQueryService(){
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
}
