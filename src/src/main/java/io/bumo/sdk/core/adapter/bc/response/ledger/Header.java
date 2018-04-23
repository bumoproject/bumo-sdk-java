package io.bumo.sdk.core.adapter.bc.response.ledger;

import com.alibaba.fastjson.annotation.JSONField;

import io.bumo.sdk.core.adapter.bc.response.ConsensusValue;

/**
 * 区块头信息
 *
 * @author 布萌
 */
public class Header{
    @JSONField(name = "account_tree_hash")
    private String accountTreeHash;
    @JSONField(name = "close_time")
    private long closeTime;
    @JSONField(name = "consensus_value_hash")
    private String consensusValueHash;
    private String hash;
    @JSONField(name = "previous_hash")
    private String previousHash;
    private long seq;
    @JSONField(name = "tx_count")
    private long txCount;
    @JSONField(name = "validators_hash")
    private String validatorsHash;
    private long version;

    @JSONField(name = "consensus_value")
    private ConsensusValue consensusValue;

    public String getAccountTreeHash(){
        return accountTreeHash;
    }

    public void setAccountTreeHash(String accountTreeHash){
        this.accountTreeHash = accountTreeHash;
    }

    public long getCloseTime(){
        return closeTime;
    }

    public void setCloseTime(long closeTime){
        this.closeTime = closeTime;
    }

    public String getConsensusValueHash(){
        return consensusValueHash;
    }

    public void setConsensusValueHash(String consensusValueHash){
        this.consensusValueHash = consensusValueHash;
    }

    public String getHash(){
        return hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    public String getPreviousHash(){
        return previousHash;
    }

    public void setPreviousHash(String previousHash){
        this.previousHash = previousHash;
    }

    public long getSeq(){
        return seq;
    }

    public void setSeq(long seq){
        this.seq = seq;
    }

    public long getTxCount(){
        return txCount;
    }

    public void setTxCount(long txCount){
        this.txCount = txCount;
    }

    public String getValidatorsHash(){
        return validatorsHash;
    }

    public void setValidatorsHash(String validatorsHash){
        this.validatorsHash = validatorsHash;
    }

    public long getVersion(){
        return version;
    }

    public void setVersion(long version){
        this.version = version;
    }

    public ConsensusValue getConsensusValue(){
        return consensusValue;
    }

    public void setConsensusValue(ConsensusValue consensusValue){
        this.consensusValue = consensusValue;
    }
}
