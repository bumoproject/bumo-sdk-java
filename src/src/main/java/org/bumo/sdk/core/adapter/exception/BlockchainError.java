package org.bumo.sdk.core.adapter.exception;

import java.util.HashMap;
import java.util.Map;

public enum BlockchainError{


    SUCCESS(0, "成功"),

    INTERNAL(1, "服务内部错误"),

    WRONG_ARGUMENT(2, "参数错误"),

    TARGET_EXIST(3, "对象已存在， 如重复提交交易"),

    TARGET_NOT_EXIST(4, "对象不存在，如查询不到账号、TX、区块等"),

    TX_TIMEOUT(5, "TX 超时，指该 TX 已经被当前节点从 TX 缓存队列去掉，但并不代表这个一定不能被执行"),

    EXPR_CONDITION_RESULT_FALSE(20, "指表达式执行结果为 false，意味着该 TX 当前没有执行成功，但这并不代表在以后的区块不能成功"),

    EXPR_CONDITION_SYNTAX_ERROR(21, "指表达式语法分析错误，代表该 TX 一定会失败"),

    ILLEGAL_PUB_KEY(90, "公钥非法"),

    ILLEGAL_PRIV_KEY(91, "私钥非法"),

    ILLEGAL_ASSET(92, "资产issue 地址非法"),

    WRONG_SIGNATURE(93, "签名权重不够，达不到操作的门限值"),

    ILLEGAL_ADDRESS(94, "地址非法"),

    OUT_OF_TIME_SPAN(95, "不在时间范围内"),

    NO_CONSENSUS(96, "没有共识"),

    TX_EMPTY_OPERATIONS(97, "交易中缺少操作"),

    TX_OUT_OF_MAX_OPERATIONS(98, "交易中包含的操作数量超过限制"),

    TX_WRONG_SEQUENCE_NO(99, "交易的序号非法"),

    NO_MONEY(100, "可用内置币余额不足"),

    ILLEGAL_TARGET_EQ_SOURCE(101, "目标地址等于源地址"),

    TARGET_ACCOUNT_EXIST(102, "目标帐号已经存在"),

    TARGET_ACCOUNT_NOT_EXIST(103, "目标账户不存在"),

    ASSET_NO_AMOUNT(104, "可用资产余额不足"),

    ASSET_AMOUNT_TOO_LARGE(105, "资产数量过大，超出了int64的范围"),

    LACK_FEE(111, "提供的手续费不足"),

    TX_TOO_EARLY(112, "交易提交过早"),

    TX_TOO_LATE(113, "交易提交过晚"),

    TX_TOO_MANY(114, "服务器收到的交易数过多,正在处理"),

    ILLEGAL_WEIGHT(120, "权重值无效"),

    NO_INPUT(130, "输入不存在"),

    ILLEGAL_INPUT(131, "输入非法"),

    IS_NOT_SCF_TX(132, "非供应链类型交易"),

    ILLEGAL_VERSION(144, "账户的metadata版本号错误"),

    CONTRACT_EXECUTE_FAIL(151, "合约执行失败"),

    CONTRACT_SYNTAX_ERROR(152, "合约语法分析失败"),;

    private int code;

    private String description;

    private static Map<Integer, BlockchainError> maps = new HashMap<>();

    static{
        for (BlockchainError error : BlockchainError.values()) {
            maps.put(error.getCode(), error);
        }
    }

    private BlockchainError(int code, String desc){
        this.code = code;
        this.description = desc;
    }

    public int getCode(){
        return code;
    }

    public String getDescription(){
        return description;
    }

    public static boolean isError(int code){
        return code != SUCCESS.code && maps.containsKey(code);
    }

    public static String getDescription(int code){
        BlockchainError error = maps.get(code);
        if (error == null) {
            return null;
        }
        return error.getDescription();
    }
}
