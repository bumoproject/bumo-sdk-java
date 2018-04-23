package io.bumo.sdk.core.transaction.sync;

import io.bumo.sdk.core.utils.concurrent.AsyncFutureBase;

public class AsyncFutureTx extends AsyncFutureBase<String>{

    public AsyncFutureTx(String source){
        super(source);
    }

    private long timestamp;


    public void setErrorFlag(String errorCode){
        super.setError(errorCode);
    }

    public void setErrorFlag(String errorCode, String errorMEssage){
        super.setError(errorCode, errorMEssage);
    }

    public void setSuccessFlag(){
        super.setSuccess();
    }

    public long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

}
