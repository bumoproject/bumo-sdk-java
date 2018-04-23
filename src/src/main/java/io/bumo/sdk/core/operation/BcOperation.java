package io.bumo.sdk.core.operation;

import com.alibaba.fastjson.JSONObject;

import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.extend.protobuf.Chain;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public interface BcOperation{

    /**
     * Integration operation
     */
    void buildTransaction(Chain.Transaction.Builder builder, long lastSeq) throws SdkException;

    /**
     * Anti serialization operation
     */
    BcOperation generateOperation(JSONObject originJson);


}
