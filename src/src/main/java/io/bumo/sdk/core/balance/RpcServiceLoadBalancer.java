package io.bumo.sdk.core.balance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
//import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bumo.sdk.core.adapter.bc.RpcService;
import io.bumo.sdk.core.adapter.bc.request.SubTransactionRequest;
import io.bumo.sdk.core.adapter.bc.request.test.EvalTXReq;
import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.adapter.bc.response.Hello;
import io.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import io.bumo.sdk.core.adapter.bc.response.converter.ServiceResponse;
import io.bumo.sdk.core.adapter.bc.response.ledger.Ledger;
import io.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import io.bumo.sdk.core.adapter.bc.response.test.EvalTxResult;
import io.bumo.sdk.core.adapter.exception.BlockchainException;
import io.bumo.sdk.core.balance.model.RpcServiceConfig;
import io.bumo.sdk.core.balance.model.RpcServiceContent;
import io.bumo.sdk.core.exception.ExceptionUtil;
import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.exec.ExecutorsFactory;
import io.bumo.sdk.core.utils.http.HttpServiceException;
import io.bumo.sdk.core.utils.http.agent.HttpServiceAgent;
import io.bumo.sdk.core.utils.http.agent.ServiceEndpoint;

/**
 * @author bumo
 * @since 18/03/12 P.M. 3:03.
 * Load access to the underlying node
 * Load policy: access the maximum block node for access
 */
public class RpcServiceLoadBalancer implements RpcService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServiceLoadBalancer.class);

    private RpcService rpcServiceProxy;

    public RpcServiceLoadBalancer(List<RpcServiceConfig> rpcServiceConfigs, NodeManager nodeManager){
        if (rpcServiceConfigs == null || rpcServiceConfigs.isEmpty())
            throw new BlockchainException("Origin RpcServiceConfig at least one!！");

        List<RpcServiceContent> rpcServiceContents = rpcServiceConfigs.stream().map(rpcServiceConfig -> {
            HttpServiceAgent.clearMemoryCache();
            ServiceEndpoint serviceEndpoint = new ServiceEndpoint(rpcServiceConfig.getHost(), rpcServiceConfig.getPort(), rpcServiceConfig.isHttps());
            RpcService rpcService = HttpServiceAgent.createService(RpcService.class, serviceEndpoint);
            return new RpcServiceContent(rpcServiceConfig.getHost(), rpcService);
        }).collect(Collectors.toList());

        this.rpcServiceProxy = (RpcService) Proxy.newProxyInstance(RpcServiceLoadBalancer.class.getClassLoader(), new Class[] {RpcService.class},
                new RpcServiceInterceptor(rpcServiceContents, nodeManager));
    }

    @Override
    public Account getAccount(String address){
        return rpcServiceProxy.getAccount(address);
    }

    @Override
    public Account getAccountBase(String address) {
        return rpcServiceProxy.getAccountBase(address);
    }

    @Override
    public Hello hello(){
        return rpcServiceProxy.hello();
    }

    @Override
    public SetMetadata getAccountMetadata(String address, String key){
        return rpcServiceProxy.getAccountMetadata(address, key);
    }

    @Override
    public Ledger getLedger(){
        return rpcServiceProxy.getLedger();
    }

    @Override
    public Ledger getLedgerBySeq(long seq){
        return rpcServiceProxy.getLedgerBySeq(seq);
    }

    @Override
    public String submitTransaction(SubTransactionRequest request){
        return rpcServiceProxy.submitTransaction(request);
    }

    @Override
    public TransactionHistory getTransactionHistoryByAddress(String address){
        return rpcServiceProxy.getTransactionHistoryByAddress(address);
    }

    @Override
    public TransactionHistory getTransactionHistoryBySeq(Long seq, int start, int limit){
        return rpcServiceProxy.getTransactionHistoryBySeq(seq, start, limit);
    }

    @Override
    public TransactionHistory getTransactionHistoryByHash(String hash){
        return rpcServiceProxy.getTransactionHistoryByHash(hash);
    }

    @Override
    public ServiceResponse getTransactionResultByHash(String hash){
        return rpcServiceProxy.getTransactionResultByHash(hash);
    }

    private class RpcServiceInterceptor implements InvocationHandler{

        private Map<String, RpcService> hostRpcMapping = new HashMap<>();
        private NodeManager nodeManager;


        RpcServiceInterceptor(List<RpcServiceContent> originRpcServiceContent, NodeManager nodeManager){
            originRpcServiceContent.forEach(rpcServiceContent -> this.hostRpcMapping.put(rpcServiceContent.getHost(), rpcServiceContent.getRpcService()));
            this.nodeManager = nodeManager;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
            Set<String> usedHosts = new HashSet<>();
            String firstHost = nodeManager.getLastHost();
            usedHosts.add(firstHost);

            Object result;
            String useHost = firstHost;

            while (true) {
                try {
                    result = doInvoke(useHost, method, args);
                    break;
                } catch (Throwable t) {
                    useHost = processThrowable(usedHosts, useHost, t);
                }
            }

            return result;
        }

        private String processThrowable(final Set<String> usedHosts, String useHost, Throwable t) throws Throwable{
        	io.bumo.sdk.core.utils.spring.Assert.notNull(t, "Throwable must not null!");
            

            if (needTryAgain(t)) {

                Set<String> allHosts = new HashSet<>(nodeManager.getAllHosts());
                usedHosts.forEach(allHosts:: remove);

                if (allHosts.iterator().hasNext()) {
                    String nowHost = allHosts.iterator().next();
                    LOGGER.error("route host " + useHost + " error. now switch to host : " + nowHost + " , usedHosts : " + usedHosts);
                    LOGGER.error("router fail error:", t);
                    usedHosts.add(nowHost);
                    return nowHost;
                }
            }

            throw t;
        }

        private boolean needTryAgain(Throwable t){
            return t instanceof HttpServiceException && t.getCause() != null && !(t.getCause() instanceof BlockchainException);
        }

        @SuppressWarnings("rawtypes")
		private Object doInvoke(String useHost, Method method, Object[] args) throws Throwable{
            try {
                // Accessing the bumo node
                RpcService rpcService = hostRpcMapping.get(useHost);
                if (rpcService == null) {
                    LOGGER.warn("router host : " + useHost + " ,but hostRpcMapping not found. hostRpcMapping keys:" + hostRpcMapping.keySet());
                    throw new SdkException(SdkError.EVENT_ERROR_ROUTER_HOST_FAIL);
                }

                LOGGER.info("load balance call rpc service router host : " + useHost + " , method : " + method.getName());

                // Increase the time control of timeout connection
                Future future = ExecutorsFactory.getExecutorService().submit(() -> method.invoke(rpcService, args));
                return future.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                if (e instanceof TimeoutException) {
                    throw new SdkException(SdkError.RPC_INVOKE_ERROR_TIMEOUT);
                }
                throw ExceptionUtil.unwrapThrowable(e);
            }

        }
    }

	@Override
	public EvalTxResult testTransaction(EvalTXReq request) {
		return rpcServiceProxy.testTransaction(request);
	}

	@Override
	public TransactionHistory getTransactionHistoryByLedgerSeq(Long ledgerSeq) {
		return rpcServiceProxy.getTransactionHistoryByLedgerSeq(ledgerSeq);
	}

}
