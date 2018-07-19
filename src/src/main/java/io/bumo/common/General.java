package io.bumo.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author riven
 * @Date 2018/7/4 15:27
 */
public class General {
    public static String url;

    public static String accountGetInfoUrl(String address) throws UnsupportedEncodingException {
        return  url + "/getAccountBase?address=" + URLEncoder.encode(address, "utf8");
    }

    public static String accountGetAssetsUrl(String address) {
        return  url + "/getAccount?address=" + address;
    }

    public static String accountGetMetadataUrl(String address, String key) throws UnsupportedEncodingException {
        return url + "/getAccount?address=" + URLEncoder.encode(address, "utf8") + "&key=" + URLEncoder.encode(key, "utf8");
    }

    public static String assetGetUrl(String address, String code, String issuer) throws UnsupportedEncodingException {
        return url + "/getAccountService?address=" + URLEncoder.encode(address, "utf8") + "&code=" +
                URLEncoder.encode(code, "utf8")  + "&issuer=" + URLEncoder.encode(issuer, "utf8");
    }

    public static String contractCallUrl() {
        return url + "/callContract";
    }


    public static String transactionEvaluationFee() {
        return url + "/testTransaction";
    }

    public static String transactionSubmitUrl() {
        return url + "/submitTransaction";
    }

    public static String transactionGetInfoUrl(String hash) throws UnsupportedEncodingException {
        return url + "/getTransactionHistory?hash=" + URLEncoder.encode(hash, "utf8");
    }


    public static String blockGetNumberUrl() {
        return url + "/getLedger";
    }

    public static String blockCheckStatusUrl() {
        return url + "/getModulesStatus";
    }

    public static String blockGetTransactionsUrl(Long blockNumber) {
        return url + "/getTransactionHistory?ledger_seq=" + blockNumber;
    }

    public static String blockGetInfoUrl(Long blockNumber) {
        return url + "/getLedger?ledger_seq=" + blockNumber;
    }

    public static String blockGetLatestInfoUrl() {
        return url + "/getLedger";
    }

    public static String blockGetValidatorsUrl(Long blockNumber) {
        return url + "/getLedger?ledger_seq=" + blockNumber + "&with_validator=true";
    }

    public static String blockGetLatestValidatorsUrl() {
        return url + "/getLedger?with_validator=true";
    }

    public static String blockGetRewardUrl(Long blockNumber) {
        return url + "/getLedger?ledger_seq=" + blockNumber + "&with_block_reward=true";
    }

    public static String blockGetLatestRewardUrl() {
        return url + "/getLedger?with_block_reward=true";
    }

    public static String blockGetFeesUrl(Long blockNumber) {
        return url + "/getLedger?seq=" + blockNumber + "&with_fee=true";
    }

    public static String blockGetLatestFeeUrl() {
        return url + "/getLedger?with_fee=true";
    }

}
