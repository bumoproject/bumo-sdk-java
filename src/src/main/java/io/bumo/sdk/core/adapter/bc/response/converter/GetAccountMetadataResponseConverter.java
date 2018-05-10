package io.bumo.sdk.core.adapter.bc.response.converter;

import io.bumo.sdk.core.adapter.bc.response.Account;
import io.bumo.sdk.core.utils.http.util.SerializeUtils;

/**
 * @author bumo
 * @since 18/03/12 P.M.3:03.
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
