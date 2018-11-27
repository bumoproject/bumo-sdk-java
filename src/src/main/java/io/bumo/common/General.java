package io.bumo.common;

import io.bumo.SDK;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author riven
 * @Date 2018/7/4 15:27
 */
public class General {
    private static General general = null;
    private String url;

    private General() {
    }

    public synchronized static General getInstance() {
        if (general == null) {
            general = new General();
        }
        general.url = SDK.getSdk().getUrl();
        return general;
    }

    public String getUrl() {
        return url;
    }

    public String accountGetInfoUrl(String address) throws UnsupportedEncodingException {
        return url + "/getAccountBase?address=" + URLEncoder.encode(address, "utf8");
    }

    public String accountGetAssetsUrl(String address) {
        return url + "/getAccount?address=" + address;
    }

    public String accountGetMetadataUrl(String address, String key) throws UnsupportedEncodingException {
        return url + "/getAccount?address=" + URLEncoder.encode(address, "utf8") + (Tools.isEmpty(key) ? "" : "&key=" + URLEncoder.encode(key, "utf8"));
    }

    public String assetGetUrl(String address, String code, String issuer) throws UnsupportedEncodingException {
        return url + "/getAccount?address=" + URLEncoder.encode(address, "utf8") + "&code=" +
                URLEncoder.encode(code, "utf8") + "&issuer=" + URLEncoder.encode(issuer, "utf8");
    }

    public String contractCallUrl() {
        return url + "/callContract";
    }


    public String transactionEvaluationFee() {
        return url + "/testTransaction";
    }

    public String transactionSubmitUrl() {
        return url + "/submitTransaction";
    }

    public String transactionGetInfoUrl(String hash) throws UnsupportedEncodingException {
        return url + "/getTransactionHistory?hash=" + URLEncoder.encode(hash, "utf8");
    }


    public String blockGetNumberUrl() {
        return url + "/getLedger";
    }

    public String blockCheckStatusUrl() {
        return url + "/getModulesStatus";
    }

    public String blockGetTransactionsUrl(Long blockNumber) {
        return url + "/getTransactionHistory?ledger_seq=" + blockNumber;
    }

    public String blockGetInfoUrl(Long blockNumber) {
        return url + "/getLedger?seq=" + blockNumber;
    }

    public String blockGetLatestInfoUrl() {
        return url + "/getLedger";
    }

    public String blockGetValidatorsUrl(Long blockNumber) {
        return url + "/getLedger?seq=" + blockNumber + "&with_validator=true";
    }

    public String blockGetLatestValidatorsUrl() {
        return url + "/getLedger?with_validator=true";
    }

    public String blockGetRewardUrl(Long blockNumber) {
        return url + "/getLedger?seq=" + blockNumber + "&with_block_reward=true";
    }

    public String blockGetLatestRewardUrl() {
        return url + "/getLedger?with_block_reward=true";
    }

    public String blockGetFeesUrl(Long blockNumber) {
        return url + "/getLedger?seq=" + blockNumber + "&with_fee=true";
    }

    public String blockGetLatestFeeUrl() {
        return url + "/getLedger?with_fee=true";
    }

}
