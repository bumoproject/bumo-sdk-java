package io.bumo.sdk.core.event.message;

public class TransactionExecutedEventMessage implements EventMessage{

    private String hash;			// Transaction hash

    private String sponsorAddress;	// Initiator's account address

    private long sequenceNumber;	// Transaction sequence number

    private Boolean success;		// Success failure marker

    private String errorCode;		// Transaction error code
    private String errorMessage;	// Transaction error message


    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getHash(){
        return hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    public String getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }

    public Boolean getSuccess(){
        return success;
    }

    public void setSuccess(Boolean success){
        this.success = success;
    }

    public String getSponsorAddress(){
        return sponsorAddress;
    }

    public void setSponsorAddress(String sponsorAddress){
        this.sponsorAddress = sponsorAddress;
    }

    public long getSequenceNumber(){
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber){
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof TransactionExecutedEventMessage)) return false;

        TransactionExecutedEventMessage that = (TransactionExecutedEventMessage) o;

        if (getSequenceNumber() != that.getSequenceNumber()) return false;
        if (getHash() != null ? !getHash().equals(that.getHash()) : that.getHash() != null) return false;
        if (getSponsorAddress() != null ? !getSponsorAddress().equals(that.getSponsorAddress()) : that.getSponsorAddress() != null)
            return false;
        if (getSuccess() != null ? !getSuccess().equals(that.getSuccess()) : that.getSuccess() != null) return false;
        if (getErrorCode() != null ? !getErrorCode().equals(that.getErrorCode()) : that.getErrorCode() != null)
            return false;
        return getErrorMessage() != null ? getErrorMessage().equals(that.getErrorMessage()) : that.getErrorMessage() == null;
    }

    @Override
    public int hashCode(){
        int result = getHash() != null ? getHash().hashCode() : 0;
        result = 31 * result + (getSponsorAddress() != null ? getSponsorAddress().hashCode() : 0);
        result = 31 * result + (int) (getSequenceNumber() ^ (getSequenceNumber() >>> 32));
        result = 31 * result + (getSuccess() != null ? getSuccess().hashCode() : 0);
        result = 31 * result + (getErrorCode() != null ? getErrorCode().hashCode() : 0);
        result = 31 * result + (getErrorMessage() != null ? getErrorMessage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return "TransactionExecutedEventMessage{" +
                "hash='" + hash + '\'' +
                ", sponsorAddress='" + sponsorAddress + '\'' +
                ", sequenceNumber=" + sequenceNumber +
                ", success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
