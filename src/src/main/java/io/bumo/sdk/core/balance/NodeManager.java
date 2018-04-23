package io.bumo.sdk.core.balance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.bumo.sdk.core.adapter.bc.RpcService;
import io.bumo.sdk.core.balance.model.RpcServiceConfig;
import io.bumo.sdk.core.event.handle.AbstractEventHandler;
import io.bumo.sdk.core.event.message.LedgerSeqEventMessage;
import io.bumo.sdk.core.event.source.EventSourceEnum;
import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.transaction.model.HashType;
import io.bumo.sdk.core.utils.http.agent.HttpServiceAgent;
import io.bumo.sdk.core.utils.http.agent.ServiceEndpoint;
import io.bumo.sdk.core.utils.spring.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Node manager controls the access priority of all nodes and dynamic routing
 */
public class NodeManager extends AbstractEventHandler<LedgerSeqEventMessage>{

    private Logger logger = LoggerFactory.getLogger(NodeManager.class);

    private final Object lock = new Object();
    private final Set<String> hosts;
    private volatile String host;
    private volatile long seq;

    private HashType hashType = HashType.SHA256;// default sha256

    // Specify a default host as the initial value
    public NodeManager(List<RpcServiceConfig> rpcServiceConfigs) throws SdkException{
        super(EventSourceEnum.LEDGER_SEQ_INCREASE.getEventSource(), LedgerSeqEventMessage.class);
        this.hosts = rpcServiceConfigs.stream().map(RpcServiceConfig:: getHost).collect(Collectors.toSet());

        for (RpcServiceConfig rpcServiceConfig : rpcServiceConfigs) {
            try {
                logger.info("node manager init try host :" + rpcServiceConfig.getHost());
                HttpServiceAgent.clearMemoryCache();
                ServiceEndpoint serviceEndpoint = new ServiceEndpoint(rpcServiceConfig.getHost(), rpcServiceConfig.getPort(), rpcServiceConfig.isHttps());
                RpcService rpcService = HttpServiceAgent.createService(RpcService.class, serviceEndpoint);
                this.seq = rpcService.getLedger().getHeader().getSeq();
                this.host = rpcServiceConfig.getHost();
                this.hashType = HashType.getHashType(rpcService.hello().getHashType());
                break;
            } catch (Exception e) {
                logger.error("node manager init found Exception:", e);
            }
        }

        if (StringUtils.isEmpty(host)) {
            throw new SdkException(SdkError.NODE_MANAGER_INIT_ERROR);
        }
    }

    /**
     * Get all the nodes
     */
    public Set<String> getAllHosts(){
        return Collections.unmodifiableSet(hosts);
    }

    /**
     * Get the highest node
     */
    public String getLastHost(){
        return host;
    }

    /**
     * Get the latest seq
     */
    public long getLastSeq(){
        return seq;
    }

    public HashType getCurrentSupportHashType(){
        return hashType;
    }

    /**
     * Notification update
     */
    public void notifySeqUpdate(String host, long newSeq){
        synchronized (lock) {
            if (seq < newSeq) {
                seq = newSeq;
                this.host = host;
            }
        }
    }

    @Override
    public void processMessage(LedgerSeqEventMessage message){
        notifySeqUpdate(message.getHost(), message.getSeq());
    }

}
