package org.bumo.sdk.core.pool;

import org.bumo.sdk.core.event.handle.AbstractEventHandler;
import org.bumo.sdk.core.event.message.TransactionExecutedEventMessage;
import org.bumo.sdk.core.event.source.EventSourceEnum;
import org.bumo.sdk.core.spi.BcOperationService;
import org.bumo.sdk.core.utils.spring.StringUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * Account pool operation provided by the outside world
 */
public class SponsorAccountPoolManager extends AbstractEventHandler<TransactionExecutedEventMessage>{

    private SponsorAccountFactory sponsorAccountFactory;
    private SponsorAccountPool sponsorAccountPool;

    public SponsorAccountPoolManager(SponsorAccountFactory sponsorAccountFactory){
        super(EventSourceEnum.TRANSACTION_NOTIFY.getEventSource(), TransactionExecutedEventMessage.class);
        this.sponsorAccountFactory = sponsorAccountFactory;
    }

    /**
     * Initialization
     */
    public void initPool(BcOperationService operationService, String address, String publicKey, String privateKey, Integer size, String filePath, String sponsorAccountMark){
        this.sponsorAccountPool = sponsorAccountFactory.initPool(operationService, address, publicKey, privateKey, size, filePath, sponsorAccountMark);
    }

    /**
     * Obtaining an available initiation account
     */
    public SponsorAccount getRichSponsorAccount(){
        if (sponsorAccountPool == null) {
            throw new IllegalStateException("invoke method getRichSponsorAccount must be method initPool after!");
        }
        return sponsorAccountPool.getRichSponsorAccount();
    }

    /**
     * Notification recovery
     */
    public void notifyRecover(String address){
        if (sponsorAccountPool == null || StringUtils.isEmpty(address)) {
            return;
        }
        sponsorAccountPool.notifyRecover(address);
    }

    @Override
    public void processMessage(TransactionExecutedEventMessage message){
        notifyRecover(message.getSponsorAddress());
    }

}
