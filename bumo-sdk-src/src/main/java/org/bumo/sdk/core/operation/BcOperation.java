package org.bumo.sdk.core.operation;

import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.extend.protobuf.Chain;

import com.alibaba.fastjson.JSONObject;

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
