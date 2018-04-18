package org.bumo.sdk.core.adapter.bc.response.converter;

import org.bumo.sdk.core.adapter.bc.response.Account;
import org.bumo.sdk.core.utils.http.util.SerializeUtils;

/**
 * @author 布萌
 * @since 18/03/12 下午3:03.
 */
public class GetAccountMetadataResponseConverter extends AbstractResponseConverter{

    @Override
    public Object dealResult(ServiceResponse serviceResponse){
        Account account = SerializeUtils.deserializeAs(serviceResponse.getResult(), Account.class);
        if (account == null || account.getMetadatas() == null || account.getMetadatas().length < 1) {
            return null;
        }
        return account.getMetadatas()[0];
    }

}
