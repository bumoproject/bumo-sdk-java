package io.bumo.sdk.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.encryption.utils.hash.HashUtil;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.request.operation.ContractCreateOperation;
import io.bumo.model.request.operation.ContractInvokeByBUOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.ContractCallResult;
import io.bumo.model.response.result.TransactionBuildBlobResult;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Atp61TokenDemo {
    /* Bumo 1.2.0 test version */
	public SDK sdk = SDK.getInstance("http://13.112.159.231");

    public String atp61Address = "buQtCAY2Q92SiXaMwXdKKXhe4dVrmJnx1CfR";
    public String entryAddress = "buQep2Eq8rVCxyb7NHtZ15Y77n6AcZufpkan";

    @Test
    public void createAtp61Contract() {
        // The seller public key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";

        createContract(sellerPrivateKey, sellerAddress, "ATP61.js");
    }

    @Test
    public void createEntryContract() {
        // The seller public key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";

        createContract(sellerPrivateKey, sellerAddress, "Entry.js");
    }

    @Test
    public void getContractAddress() {
        String atp61Hash = "407227f74c310db3a6e05ad2d719099deb5b830030e7f497fc829bd82ca02a13";
        String entryHash = "ce6312b096f7c19a9e4f19616cd5e95712c5d18f2995e7521b6240e633911c00";
        getContractAddressQuery(atp61Hash);
        getContractAddressQuery(entryHash);;
    }


    /**
	 * First: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) registers the information in ATP 60.
	 */
	@Test
    public void register() {
        // The seller public key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company full name.
        String fullName = "BUMO社区";
        // Compnay short name.
        String shortName = "bumo";
        // Company contact.
        String contact = "Contact@bumo.io";
        // Company organizational code.
        String organizationalCode = "N5464**";
        // Company corporate name.
        String corporateName = "某某";
        String confirmPeriod = "7";
        // Company corporate identity card number
        String cardNumber = "1**";
        String businessLicenseIconType = "png";
        String businessLicenseUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560172631149&di=36e2c4a9539a82fb97e4bb29f97a5e81&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01acc45607d5826ac7251df87e05b8.jpg%401280w_1l_2o_100sh.png";
        String businessLicenseHashType = "sha256";
        String businessLicenseHash = HashUtil.GenerateHashHex(getContentFromUrl(businessLicenseUrl));
        String businessLicense = businessLicenseIconType + "|" + businessLicenseUrl + "|" + businessLicenseHashType + "|" + businessLicenseHash;//"[图片类型|url|hash类型|hash值]";
        // Company corporate identity card front photo.
        String cardFrontPhoto = "[图片类型|url|hash类型|hash值]";
        // Company corporate identity card back photo.
        String cardBackPhoto = "[图片类型|url|hash类型|hash值]";

        // Registerring.
        registerTx(sellerPrivateKey, sellerAddress, fullName, shortName, contact, confirmPeriod, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);
    }

    @Test
    public void contractInfo() {
	    contractInfoQuery();
    }

    @Test
    public void setSeller() {
        // The seller public key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company full name.
        String fullName = "BUMO社区";
        // Compnay short name.
        String shortName = "bumo111";
        // Company contact.
        String contact = "Contact@bumo.io";
        // Company organizational code.
        String organizationalCode = "N5464**";
        // Company corporate name.
        String corporateName = "某某";
        // Company corporate identity card number
        String cardNumber = "1**";
        // Company business license photo.
        String businessLicense = "[图片类型|url|hash类型|hash值]";
        // Company corporate identity card front photo.
        String cardFrontPhoto = "[图片类型|url|hash类型|hash值]";
        // Company corporate identity card back photo.
        String cardBackPhoto = "[图片类型|url|hash类型|hash值]";

        setSellerTx(sellerPrivateKey, sellerAddress, fullName, shortName, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);
    }

    @Test
    public void sellInfo() {
        sellInfoQuery();
    }


    /**
     * Second: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) sets the document.
     */
    @Test
    public void setDocument() {
        // The seller public key to set document.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The document id.
        String documentId = "1";
        // The document name.
        String documentName = "BUMO白皮书";
        // The document url.
        String documentUrl = "https://BUMO.io/BUMO-Technology-White-Paper-cn";
        // The document hash type.
        String documentHashType = "md5";
        // The document hash.
        String documentHash = "31be016368639ba1a7ae7b63247807a1";

        // Setting the document.
        setDocumentTx(sellerPrivateKey, sellerAddress, documentId, documentName, documentUrl, documentHashType, documentHash);
    }

    @Test
    public void documentInfo() {
        // The document id.
        String documentId = "1";

        documentInfoQuery(documentId);
    }

    @Test
    public void allDocuments() {
        allDocumentsQuery();
    }


    /**
     * Third: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) creates the SPU.
     */
    @Test
    public void createSPU() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "000000001";
        // The spu name.
        String spuName = "苹果 5s";
        // The spu type.
        String spuType = "手机";
        // The spu reference price.
        String spuPrice = "3000.00";
        // The spu brand.
        String spuBrand = "苹果";
        // The spu model.
        String spuModel = "5s";

        // Creating SPU.
        createSPUTx(sellerPrivateKey, sellerAddress, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);
    }

    @Test
    public void setSPU() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "000000001";
        // The spu name.
        String spuName = "苹果 5s 1111";
        // The spu type.
        String spuType = "手机";
        // The spu reference price.
        String spuPrice = "3000.00";
        // The spu brand.
        String spuBrand = "苹果";
        // The spu model.
        String spuModel = "5s";

        // Creating SPU.
        setSPUTx(sellerPrivateKey, sellerAddress, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);
    }

    @Test
    public void SPUInfo() {
        // The spu id.
        String spuId = "000000001";

        SPUInfoQuery(spuId);
    }


    /**
     * Fourth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) creates the Tranche.
     */
    @Test
    public void createTranche() {
        // The seller public key to create tranche.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The tranche id.
        String trancheId = "1";
        // The tranche description.
        String trancheDesc = "有效期";
        // The start time, the unit is ms.
        String startTime = "1517032155872949";
        // The end time, the unit is ms.
        String endTime = "1517470155872949";

        // Creating tranche.
        createTrancheTx(sellerPrivateKey, sellerAddress, trancheId, trancheDesc, startTime, endTime);
    }

    @Test
    public void trancheInfo() {
        // The tranche id.
        String trancheId = "1";

        trancheInfoQuery(trancheId);
    }

    /**
     * Fifth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) sets acceptance.
     */
    @Test
    public void setAcceptance() {
        // The seller public key to set acceptance.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The acceptance id.
        String acceptanceId = "2";
        // The acceptor address.
        String acceptorPublicKey = "b0014d4d8338f743631f4b04c49a693c6757962d888fbc4188a6cf4bacf99b585806bd335194";
        // The acceptance full name.
        String acceptanceFullName = "北京贸易集中处理中心";
        // The acceptance short name.
        String acceptanceShortName = "MC";
        // The acceptance logo.
        String acceptanceLogo = "png|data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjwhLS0gR2VuZXJhdG9yOiBBZG9iZSBJbGx1c3RyYXRvciAyMC4wLjAsIFNWRyBFeHBvcnQgUGx1Zy1JbiAuIFNWRyBWZXJzaW9uOiA2LjAwIEJ1aWxkIDApICAtLT4NCjxzdmcgdmVyc2lvbj0iMS4wIiBpZD0i5Zu+5bGCXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgMTEyLjQgNDAiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDExMi40IDQwOyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+DQo8c3R5bGUgdHlwZT0idGV4dC9jc3MiPg0KCS5zdDB7ZmlsbDojRkZGRkZGO30NCgkuc3Qxe2ZpbGw6dXJsKCNTVkdJRF8xXyk7fQ0KCS5zdDJ7ZmlsbDp1cmwoI1NWR0lEXzJfKTt9DQoJLnN0M3tmaWxsOnVybCgjU1ZHSURfM18pO30NCgkuc3Q0e2ZpbGw6dXJsKCNTVkdJRF80Xyk7fQ0KPC9zdHlsZT4NCjxnPg0KCTxwYXRoIGNsYXNzPSJzdDAiIGQ9Ik03MS43LDExLjl2MTIuNGMwLDAuNS0wLjQsMC44LTAuOCwwLjhoLTIuM2MtMC41LDAtMC44LTAuNC0wLjgtMC44VjExLjljMC0wLjEtMC4xLTAuMi0wLjItMC4yaC0zLjINCgkJYy0wLjEsMC0wLjIsMC4xLTAuMiwwLjJWMjdjMCwwLjksMC43LDEuNywxLjcsMS43aDcuN2MwLjksMCwxLjctMC43LDEuNy0xLjdWMTEuOWMwLTAuMS0wLjEtMC4yLTAuMi0wLjJoLTMuMg0KCQlDNzEuOCwxMS43LDcxLjcsMTEuOCw3MS43LDExLjl6Ii8+DQoJPHBhdGggY2xhc3M9InN0MCIgZD0iTTc5LjEsMTEuN3YyMGwzLjUtMy4xVjE1LjRjMC0wLjEsMC4xLTAuMiwwLjItMC4yaDIuNmMwLjEsMCwwLjIsMC4xLDAuMiwwLjJ2MTNjMCwwLjEsMC4xLDAuMiwwLjIsMC4ySDg5DQoJCWMwLjEsMCwwLjItMC4xLDAuMi0wLjJ2LTEzYzAtMC4xLDAuMS0wLjIsMC4yLTAuMmgyYzAuNSwwLDAuOCwwLjQsMC44LDAuOHYxMi40YzAsMC4xLDAuMSwwLjIsMC4yLDAuMmgzLjJjMC4xLDAsMC4yLTAuMSwwLjItMC4yDQoJCXYtMTVjMC0wLjktMC43LTEuNy0xLjctMS43SDc5LjF6Ii8+DQoJPHBhdGggY2xhc3M9InN0MCIgZD0iTTk5LjUsMTMuNFYyN2MwLDAuOSwwLjcsMS43LDEuNywxLjdoNy43YzAuOSwwLDEuNy0wLjcsMS43LTEuN1YxMy40YzAtMC45LTAuNy0xLjctMS43LTEuN2gtNy43DQoJCUMxMDAuMiwxMS43LDk5LjUsMTIuNCw5OS41LDEzLjR6IE0xMDYuMSwyNC44aC0yLjNjLTAuNSwwLTAuOC0wLjQtMC44LTAuOHYtNy43YzAtMC41LDAuNC0wLjgsMC44LTAuOGgyLjNjMC41LDAsMC44LDAuNCwwLjgsMC44DQoJCVYyNEMxMDcsMjQuNSwxMDYuNiwyNC44LDEwNi4xLDI0Ljh6Ii8+DQoJPHBhdGggY2xhc3M9InN0MCIgZD0iTTU5LjIsMjBsMS4xLTEuMWMwLjEtMC4xLDAuMS0wLjEsMC4xLTAuMnYtNS40YzAtMC45LTAuNy0xLjctMS43LTEuN2gtOS4zdjE2LjloOS4zYzAuOSwwLDEuNy0wLjcsMS43LTEuNw0KCQl2LTUuNGMwLTAuMSwwLTAuMi0wLjEtMC4yTDU5LjIsMjBDNTkuMSwyMC4yLDU5LjEsMjAuMSw1OS4yLDIweiBNNTYsMjUuMWgtMy4xdi0zLjVINTZjMC41LDAsMC44LDAuNCwwLjgsMC44djEuOQ0KCQlDNTYuOCwyNC43LDU2LjQsMjUuMSw1NiwyNS4xeiBNNTYuOCwxNy45YzAsMC41LTAuNCwwLjgtMC44LDAuOGgtMy4xbDAsMHYtMy41SDU2YzAuNSwwLDAuOCwwLjQsMC44LDAuOFYxNy45eiIvPg0KPC9nPg0KPGc+DQoJPGc+DQoJCQ0KCQkJPGxpbmVhckdyYWRpZW50IGlkPSJTVkdJRF8xXyIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiIHgxPSI5LjkiIHkxPSIzNjAuNzM3MiIgeDI9IjkuOSIgeTI9IjM5My41NzEzIiBncmFkaWVudFRyYW5zZm9ybT0ibWF0cml4KDEgMCAwIDEgMCAtMzU4KSI+DQoJCQk8c3RvcCAgb2Zmc2V0PSIwIiBzdHlsZT0ic3RvcC1jb2xvcjojMDBEMDgwIi8+DQoJCQk8c3RvcCAgb2Zmc2V0PSIxIiBzdHlsZT0ic3RvcC1jb2xvcjojMEJERDYwIi8+DQoJCTwvbGluZWFyR3JhZGllbnQ+DQoJCTxwYXRoIGNsYXNzPSJzdDEiIGQ9Ik0xNy45LDEwLjhWMC42YzAtMC4xLTAuMS0wLjItMC4yLTAuMUwyLDkuNUwxLjksOS42djEwLjJDMS45LDIwLDIuMSwyMCwyLjIsMjBsMTUuNi05DQoJCQlDMTcuOCwxMC45LDE3LjksMTAuOSwxNy45LDEwLjh6Ii8+DQoJPC9nPg0KCTxnPg0KCQkNCgkJCTxsaW5lYXJHcmFkaWVudCBpZD0iU1ZHSURfMl8iIGdyYWRpZW50VW5pdHM9InVzZXJTcGFjZU9uVXNlIiB4MT0iMTQuNjYyNSIgeTE9IjM2MC43MzcyIiB4Mj0iMTQuNjYyNSIgeTI9IjM5My41NzEzIiBncmFkaWVudFRyYW5zZm9ybT0ibWF0cml4KDEgMCAwIDEgMCAtMzU4KSI+DQoJCQk8c3RvcCAgb2Zmc2V0PSIwIiBzdHlsZT0ic3RvcC1jb2xvcjojMDBEMDgwIi8+DQoJCQk8c3RvcCAgb2Zmc2V0PSIxIiBzdHlsZT0ic3RvcC1jb2xvcjojMEJERDYwIi8+DQoJCTwvbGluZWFyR3JhZGllbnQ+DQoJCTxwYXRoIGNsYXNzPSJzdDIiIGQ9Ik0xNy45LDIzLjZ2LTcuMWMwLTAuMS0wLjEtMC4yLTAuMi0wLjFMMTEuNSwyMGMtMC4xLDAuMS0wLjEsMC4yLDAsMC4zbDYuMSwzLjUNCgkJCUMxNy43LDIzLjgsMTcuOSwyMy44LDE3LjksMjMuNnoiLz4NCgk8L2c+DQoJPGc+DQoJCQ0KCQkJPGxpbmVhckdyYWRpZW50IGlkPSJTVkdJRF8zXyIgZ3JhZGllbnRVbml0cz0idXNlclNwYWNlT25Vc2UiIHgxPSIzMC41NSIgeTE9IjM2MC43MzcyIiB4Mj0iMzAuNTUiIHkyPSIzOTMuNTcxMyIgZ3JhZGllbnRUcmFuc2Zvcm09Im1hdHJpeCgxIDAgMCAxIDAgLTM1OCkiPg0KCQkJPHN0b3AgIG9mZnNldD0iMCIgc3R5bGU9InN0b3AtY29sb3I6IzAwRDA4MCIvPg0KCQkJPHN0b3AgIG9mZnNldD0iMSIgc3R5bGU9InN0b3AtY29sb3I6IzBCREQ2MCIvPg0KCQk8L2xpbmVhckdyYWRpZW50Pg0KCQk8cGF0aCBjbGFzcz0ic3QzIiBkPSJNMzMuNSw2Ljl2MjAuNWMwLDAuMSwwLDAuMS0wLjEsMC4xTDI5LDMwLjFjLTAuMSwwLjEtMC4yLDAtMC4yLTAuMVY0LjFjMC0wLjEsMC0wLjEtMC4xLTAuMWwtNS45LTMuNA0KCQkJYy0wLjEtMC4xLTAuMiwwLTAuMiwwLjF2Ny42bDAsMFYzMmwwLDB2Ny42YzAsMC4xLDAuMSwwLjIsMC4yLDAuMWwxNS42LTlsMC4xLTAuMVY5LjdjMC0wLjEsMC0wLjEtMC4xLTAuMWwtNC43LTIuNw0KCQkJQzMzLjYsNi44LDMzLjUsNi44LDMzLjUsNi45eiIvPg0KCTwvZz4NCgk8Zz4NCgkJDQoJCQk8bGluZWFyR3JhZGllbnQgaWQ9IlNWR0lEXzRfIiBncmFkaWVudFVuaXRzPSJ1c2VyU3BhY2VPblVzZSIgeDE9IjkuOTUiIHkxPSIzNjAuNzM3MiIgeDI9IjkuOTUiIHkyPSIzOTMuNTcxMyIgZ3JhZGllbnRUcmFuc2Zvcm09Im1hdHJpeCgxIDAgMCAxIDAgLTM1OCkiPg0KCQkJPHN0b3AgIG9mZnNldD0iMCIgc3R5bGU9InN0b3AtY29sb3I6IzAwRDA4MCIvPg0KCQkJPHN0b3AgIG9mZnNldD0iMSIgc3R5bGU9InN0b3AtY29sb3I6IzBCREQ2MCIvPg0KCQk8L2xpbmVhckdyYWRpZW50Pg0KCQk8cGF0aCBjbGFzcz0ic3Q0IiBkPSJNMTcuOCwyOS4zbC0xNS42LTljLTAuMS0wLjEtMC4yLDAtMC4yLDAuMXYxMC4yYzAsMC4xLDAsMC4xLDAuMSwwLjFsMTUuNiw5YzAuMSwwLjEsMC4yLDAsMC4yLTAuMVYyOS40DQoJCQlDMTcuOSwyOS4zLDE3LjgsMjkuMywxNy44LDI5LjN6Ii8+DQoJPC9nPg0KPC9nPg0KPC9zdmc+DQo=|md5|1231231231231231";
        // The acceptance contact.
        String acceptanceContact = "contact@my.com";

        setAcceptanceTx(sellerPrivateKey, sellerAddress, acceptanceId, acceptorPublicKey, acceptanceFullName, acceptanceShortName, acceptanceLogo, acceptanceContact);
    }

    @Test
    public void acceptanceInfo() {
        // The acceptance id.
        String acceptanceId = "2";

        acceptanceInfoQuery(acceptanceId);
    }


    /**
     * Sixth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) issues the SKU Tokens.
     *
     * Notice:  The spu and tranche can be ignored.
     *    1. That the spu is ignored means that the sku does not have spu.
     *    2. If the tranche is ignored, the sku will issue to the default tranche which id is '0'.
     */
    @Test
    public void issueSKUTokens() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "1";
        // The default tranche.
        // Notice: If this is not setting, the tranche of id '0' will be used.
        String trancheId = null;
        // Whether setting the tranche id as default tranche.
        boolean isDefaultTranche = false;
        // The spu id.
        // Notice: If this is not setting, this sku tokens don't have spu.
        String spuId = null;
        // The sku label.
        JSONArray skuLabel = new JSONArray();
        skuLabel.add("iphone");
        skuLabel.add("iphone 5s");
        skuLabel.add("iphone 5s 白色");
        skuLabel.add("iphone 5s 白色 64G");
        skuLabel.add("iphone 5s 白色 64G 中国大陆版");
        // The address that will be sent SKU Tokens when the redemption finishes.
        String redemptionAddress = "buQqudnoPPV2utx92jfdcLkFGDaB7v3iasPM";
        // The redemption peroid.
        String redemptionPeriod = "7";
        // The sku reference price.
        String skuPrice = "3000";
        // The sku color.
        String skuColor = "白色";
        // The sku memory.
        String skuMemory = "64";
        // The sku model.
        String skuModel = "中国大陆";
        // The sku abstract
        JSONArray skuAbstract = new JSONArray();
        skuAbstract.add("1"); // id "1" in attributes
        skuAbstract.add("3"); // id "3" in attributes
        skuAbstract.add("2"); // id "2" in attributes
        // The token name
        String skuName = "iphone 5s 白色 64G 中国大陆版";
        // The token symbol.
        String tokenSymbol = "IPWSFC";
        // The token id.
        String tokenId = "1";
        // The token info.
        String tokenInfo = "1111111111";
        // The token description.
        String skuDesc = "iphone 5s 白色 64G 中国大陆版";
        // The main icon
        String mainIcon = "png|https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557751338635&di=3f6b989903ddf1cf9c10cc530c849d93&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01815657c24f1b0000012e7eb901a1.jpg|md5|2938472190312847";
        // The vice icons
        JSONArray viceIcons = new JSONArray();
        viceIcons.add(mainIcon);
        // The acceptance id
        String acceptanceId = "2";

        // The sku attributes
        JSONObject attributes = new JSONObject();
        attributes.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        attributes.put("1", buildAdditionIndex("0",         "参考价", "int",  skuPrice,     "-",       "CNY"));
        attributes.put("2", buildAdditionIndex("0",         "颜色",  "text",  skuColor,   "-",        "-"));
        attributes.put("3", buildAdditionIndex("0",         "内存",  "int",   skuMemory,       "-",        "G"));
        attributes.put("4", buildAdditionIndex("0",         "型号",  "text",  skuModel, "-",       "-"));

        // Issuing SKU Tokens.
        issueSKUTokensTx(sellerPrivateKey, sellerAddress, skuId, trancheId, isDefaultTranche, spuId, skuName, tokenSymbol, tokenId, tokenInfo, skuDesc, mainIcon, viceIcons, skuLabel, redemptionAddress, redemptionPeriod, acceptanceId, skuAbstract, attributes);
    }

    @Test
    public void setLogicAddress() {
        // The seller public key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The new logic address
        String logicAddress = "buQYJZ4e5MASDBXcMGSxzdWEZ3eej5voHCxo";

        setLogicAddressTx(sellerPrivateKey, sellerAddress, logicAddress);
    }

    @Test
    public void setSku() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "2";
        // The spu id.
        // The sku label.
        JSONArray skuLabel = new JSONArray();
        skuLabel.add("iphone");
        skuLabel.add("iphone 5s");
        skuLabel.add("iphone 5s 白色");
        skuLabel.add("iphone 5s 白色 64G");
        skuLabel.add("iphone 5s 白色 64G 中国大陆版");
        // The address that will be sent SKU Tokens when the redemption finishes.
        String redemptionAddress = "buQqudnoPPV2utx92jfdcLkFGDaB7v3iasPM";
        String acceptanceId = "adfqwqwq";
        // The sku reference price.
        String skuPrice = "3000";
        // The sku color.
        String skuColor = "白色";
        // The sku memory.
        String skuMemory = "64";
        // The sku model.
        String skuModel = "中国大陆";
        // The sku abstract
        JSONArray skuAbstract = new JSONArray();
        skuAbstract.add("1"); // id "1" in attributes
        skuAbstract.add("3"); // id "3" in attributes
        skuAbstract.add("2"); // id "2" in attributes
        // The token name
        String skuName = "1111111111111";
        // The token symbol.
        String tokenSymbol = "IPWSFC";
        // The sku description.
        String skuDesc = "iphone 5s 白色 64G 中国大陆版";
        // The main icon
        String mainIcon = "png|https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557751338635&di=3f6b989903ddf1cf9c10cc530c849d93&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01815657c24f1b0000012e7eb901a1.jpg|md5|2938472190312847";
        // The vice icons
        JSONArray viceIcons = new JSONArray();
        viceIcons.add(mainIcon);

        // The sku attributes
        JSONObject attributes = new JSONObject();
        attributes.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        attributes.put("1", buildAdditionIndex("0",         "参考价", "int",  skuPrice,     "-",       "CNY"));
        attributes.put("2", buildAdditionIndex("0",         "颜色",  "text",  skuColor,   "-",        "-"));
        attributes.put("3", buildAdditionIndex("0",         "内存",  "int",   skuMemory,       "-",        "G"));
        attributes.put("4", buildAdditionIndex("0",         "型号",  "text",  skuModel, "-",       "-"));

        setSKUTx(sellerPrivateKey, sellerAddress, skuId, skuName, tokenSymbol, skuDesc, mainIcon, viceIcons, skuLabel, redemptionAddress, acceptanceId, skuAbstract, attributes);
    }

    @Test
    public void skuTokenInfo() {
        // The sku id.
        String skuId = "1";

        SKUTokenInfoQuery(skuId);
    }

    @Test
    public void addAcceptanceToSku() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "2";
        // The acceptance id.
        String acceptanceId = "2";

        addAcceptanceToSkuTx(sellerPrivateKey, sellerAddress, skuId, acceptanceId);
    }

    @Test
    public void delAcceptanceFromSku() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "2";
        // The acceptance id.
        String acceptanceId = "2";

        delAcceptanceFromSkuTx(sellerPrivateKey, sellerAddress, skuId, acceptanceId);
    }

    @Test
    public void skusOfSpu() {
        // The spu id.
        String spuId = "000000001";

        skusOfSpuQuery(spuId);
    }

    @Test
    public void skusOfTranche() {
        // The tranche id.
        String trancheId = "1";

        skusOfTrancheQuery(trancheId);
    }

    @Test
    public void tranchesOfSku() {
        // The sku id.
        String skuId = "2";

        tranchesOfSkuQuery(skuId);
    }

    @Test
    public void tranchesOf() {
        // The sku id.
        String skuId = "2";
        // The account address.
        String address = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";

        tranchesOfQuery(skuId, address);
    }

    @Test
    public void acceptancesOfSku() {
        // The sku id.
        String skuId = "2";

        acceptancesOfSkuQuery(skuId);
    }

    /**
     * Seventh: If the SKU hash default tranche, Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) assigns the token (2) to tranche (0).
     */
    @Test
    public void assignToTranche() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "1";
        // The target tranche.
        String toTrancheId = "0";
        // The token id.
        String tokenId = "2";

        assignToTrancheTx(sellerPrivateKey, sellerAddress, skuId, toTrancheId, tokenId);
    }

    /**
     * Eighth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) sets the choice for several skus.
     */
    @Test
    public void setSkusChoice() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id
        String spuId = "000000001";
        // The choice
        JSONObject choice = new JSONObject();
        JSONArray skuId = new JSONArray();
        skuId.add("颜色");
        skuId.add("内存");
        skuId.add("型号");
        choice.put("skuId", skuId);
        JSONArray sku1 = new JSONArray();
        sku1.add("2");
        sku1.add("3");
        sku1.add("4");
        choice.put("1", sku1);
        JSONArray sku2 = new JSONArray();
        sku2.add("2");
        sku2.add("3");
        sku2.add("4");
        choice.put("2", sku2);

        setSkusChoiceTx(sellerPrivateKey, sellerAddress, spuId, choice);
    }

    /**
     * NinTh: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) adds issuance of the SKU Tokens.
     *
     * Notice: The tranche can be ignored.
     *    If the trancheId is ignored, SKU Tokens will be sent to default tranche which id is '0'.
     */
    @Test
    public void additionalIssuance() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche which id is '0'.
        String trancheId = null; //"1";
        // The token supply.
        String tokenId = "3";
        // The token info
        String tokenInfo = "其他信息";

        // Adding issuance by tranche.
        additionalIssuanceTx(sellerPrivateKey, sellerAddress, skuId, trancheId, tokenId, tokenInfo);
    }


    /**
     * Tenth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) destroys SKU Tokens.
     */
    @Test
    public void destroy() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The address.
        String address= "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The token supply.
        String tokenId = "1";

        destroyTx(sellerPrivateKey, sellerAddress, address, tokenId);
    }


    /**
     * Eleventh: Manufacturer (buQamKpa9vmNwA7PTknnbgWRhyVZLPWy2bCu) authorizes the issuance of SKU Tokens.
     */
    @Test
    public void setAuthorizers() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "2";
        // The authorizers
        JSONArray authorizers = new JSONArray();
        authorizers.add("buQamKpa9vmNwA7PTknnbgWRhyVZLPWy2bCu");

        setAuthorizersTx(sellerPrivateKey, sellerAddress, skuId, authorizers);
    }

    @Test
    public void authorizeSKU() {
        // The manufacturer public key to authrize sku tokens.
        String manufacturerPrivateKey = "privbyGpWyTTBgJoj3WezCv2D5cCCuPoL1dHYpMCmaFVSTvQmuUpgz6j";
        // The seller address.
        String manufacturerAddress = "buQamKpa9vmNwA7PTknnbgWRhyVZLPWy2bCu";
        // The sku id.
        String skuId = "2";
        // The tranche id.
        String trancheId = "1";


        // Autorizing SKU.
        autorizeSKUTx(manufacturerPrivateKey, manufacturerAddress, skuId, trancheId);
    }

    @Test
    public void authorizedSku() {
        // The sku id.
        String skuId = "2";
        // The tranche id.
        String trancheId = "1";
        // The authorizer.
        String authorizer = "buQamKpa9vmNwA7PTknnbgWRhyVZLPWy2bCu";

        authorizedSkuQuery(skuId, trancheId, authorizer);
    }


    /**
     * Twelfth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) transfers the token id (1) to an account (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP).
     */
    @Test
    public void transfer() {
        // The seller public key to transfer sku tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The target address.
        String toAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The sku id.
        String tokenId = "1";

        // Transferring tokens.
        transferTx(sellerPrivateKey, sellerAddress, toAddress, tokenId);
    }

    @Test
    public void balanceOf() {
        // The sku id.
        String skuId = "1";
        // The address to be queried.
        String address = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";

        balanceOfQuery(address, skuId);
    }

    @Test
    public void balanceOfByTranche() {
        // The sku id.
        String skuId = "1";
        // The tranche id.
        String trancheId = null; //"1";
        // The address to be queried.
        String address = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        balanceOfByTrancheQuery(address, skuId, trancheId);
    }

    @Test
    public void balanceOfByLockup() {
        // The sku id.
        String skuId = "1";
        // The tranche id.
        String trancheId = null; //"1";
        // The lockup id.
        String lockupId = "d8c6bee358347953cda5cde0a35695ed76bb777edef23492f84a750f7572d18f";
        // The address
        String address = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        balanceOfByLockupQuery(address, skuId, trancheId, lockupId);
    }

    /**
     * Thirteenth: Token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) approve other account (buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK) token id (1).
     */
    @Test
    public void approve() {
        // The token holder public key to approve sku tokens.
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";

        String holderPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The token holder address.
        String holderAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spender address.
        String spender = "buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK";
        // The sku token id.
        String tokenId = "1";

        // Approving
        approveTx(holderPrivateKey, holderAddress, spender, tokenId);
    }

    @Test
    public void allowance() {
        // The token owner.
        String owner = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spender.
        String spender = "buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK";
        // The sku id.
        String skuId = "1";
        // The tranche id.
        String trancheId = null;

        allowanceQuery(owner, spender, skuId, trancheId);
    }


    /**
     * Fourteenth: The spender (buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK) tranfers the tokens of the token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) token id (1) to other account (buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor).
     */
    @Test
    public void transferFrom() {
        // The spender public key to transfer sku tokens.
        String spenderPrivateKey = "privbwMxt4BFNjN6vbjizrFEMhiFJyxMydWCb3sr1utDokApEMGXB9mD";
        // The spender address.
        String spenderAddress = "buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK";
        // The from address.
        String fromAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The target address.
        String toAddress = "buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor";
        // The sku token id.
        String tokenId = "1";

        // Transferring the Tokens.
        transferFromTx(spenderPrivateKey, spenderAddress, fromAddress, toAddress, tokenId);
    }

    /**
     * Fifteenth: The token holder (buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor) requests redemption.
     */
    @Test
    public void requestRedemption() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The token holder public key to apply dispute.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The redemption id.
        String redemptionId = "5";
        // The token id.
        String tokenId = "1";
        // The description.
        String description = "要好的";
        // The addition
        JSONObject addition = new JSONObject();
        addition.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        addition.put("1", buildAdditionIndex("0",         "参考价", "int",  "100",     "-",       "CNY"));
        addition.put("2", buildAdditionIndex("0",         "颜色",  "text",  "白色",   "-",        "-"));
        addition.put("3", buildAdditionIndex("0",         "内存",  "int",   "256",       "-",        "G"));
        addition.put("4", buildAdditionIndex("0",         "型号",  "text",  "Plus", "-",       "-"));



        // Requesting redemption.
        requestRedemptionTx(holderPrivateKey, holderAddress, redemptionId, tokenId, description, addition);
    }

    @Test
    public void redemptionInfo() {
        // The redemption id.
        String redemptionId = "4";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        redemptionInfoQuery(redemptionId, applicant);
    }


    /**
     * Sixteenth: The acceptor (buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor) redeems.
     */
    @Test
    public void redeem() {
        // The acceptor public key to redemption.
        String acceptorPrivateKey = "privbUbdoe6co99ykomqQRUDiD8rh3XvWvmexNUS1bZbu5gb8RuKJA8Y";
        // The acceptor address.
        String acceptorAddress = "buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor";
        // The redemption id.
        String redemptionId = "5";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The logistics.
        String logistics = "1234134";

        // Redeeming.
        redeemTx(acceptorPrivateKey, acceptorAddress, redemptionId, applicant, logistics);
    }


    /**
     * Seventeenth: The redemption finished or causes dispute.
     * Seventeenth1: The token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) confirms redemption right, then finishes the redemption.
     */
    @Test
    public void confirmRedemption() {
        // The token holder public key to confirm redemption.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The redemption id.
        String redemptionId = "5";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        // Confirming redemption.
        confirmRedemptionTx(holderPrivateKey, holderAddress, redemptionId, applicant);
    }

    @Test
    public void lockupInfo() {
        // The redemption id.
        String lockupId = "d8c6bee358347953cda5cde0a35695ed76bb777edef23492f84a750f7572d18f";

        lockupInfoQuery(lockupId);
    }

    @Test
    public void cancelRedemption() {
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The seller address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        //String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        //String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The token seller public key to confirm redemption.
        String sellerPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP"; // The seller public key to transfer sku tokens
        // The seller address.
        String sellerAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The redemption id.
        String redemptionId = "4";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The reason.
        String reason = "12341234";

        // Confirming redemption.
        cancelRedemptionTx(sellerPrivateKey, sellerAddress, redemptionId, applicant, reason);
    }

    public String createContract(String sourcePrivateKey, String sourceAddress, String fileName) {
        // The contract account init balance.
        Long initBalance = ToBaseUnit.BU2MO("0.01");
        // The contract codes
        String payLoad = getContractCodeFromFile(fileName);
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 10.03BU
        Long feeLimit = ToBaseUnit.BU2MO("12");

        // 1. Getting the account nonce, and the nonce must add 1.
        Long nonce = getAccountNonce(sourceAddress) + 1;

        // 2. Building ContractCreateOperation.
        ContractCreateOperation operation = new ContractCreateOperation();
        operation.setSourceAddress(sourceAddress);
        operation.setInitBalance(initBalance);
        operation.setPayload(payLoad);

        BaseOperation[] operations = { operation };

        // 4. Broadcasting the transaction
        String txHash = broadcastTransaction(sourcePrivateKey, sourceAddress, operations, nonce, gasPrice, feeLimit, null);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Registering.
     * @return The register tx hash.
     */
    public String registerTx(String sourcePrivateKey, String sourceAddress, String fullName, String shortName, String contact, String confirmPeriod, String organizationalCode, String corporateName, String cardNumber, String businessLicense, String cardFrontPhoto, String cardBackPhoto) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 10.03BU
        Long feeLimit = ToBaseUnit.BU2MO("12");

        // 1. Building the initInput
        JSONObject input = new JSONObject();
        input.put("method", "register");
        JSONObject params = new JSONObject();
        params.put("logicAddress", atp61Address);
        params.put("companyFullName", fullName);
        params.put("companyShortName", shortName);
        params.put("companyContact", contact);
        params.put("confirmPeriod", confirmPeriod);
        JSONObject companyCertification = new JSONObject();
        companyCertification.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        companyCertification.put("1", buildAdditionIndex("0", "企业组织机构代码", "text", organizationalCode, "-", "-"));
        companyCertification.put("2", buildAdditionIndex("0", "法人名称", "text", corporateName, "-", "-"));
        companyCertification.put("3", buildAdditionIndex("0", "法人身份证号", "text", cardNumber, "-", "-"));
        companyCertification.put("4", buildAdditionIndex("0", "营业执照照片", "image", businessLicense, "-", "-"));
        companyCertification.put("5", buildAdditionIndex("0", "法人身份证正面照片", "image", cardFrontPhoto, "-", "-"));
        companyCertification.put("6", buildAdditionIndex("0", "法人身份证反面照片", "image", cardBackPhoto, "-", "-"));
        params.put("companyCertification", companyCertification);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Getting the contract address.
     * @return The contract address.
     */
    public String getContractAddressQuery(String hash) {
        // Making sure the register tx success.
        if (!MakeSureTxSuccess(hash)) {
            System.out.println("The register tx runs failed!");
            return null;
        }

        // Initializing the request.
        ContractGetAddressRequest request = new ContractGetAddressRequest();
        request.setHash(hash);

        // Getting the contract address.
        ContractGetAddressResponse response = sdk.getContractService().getAddress(request);
        String address = null;
        if (response.getErrorCode() == 0) {
            address = response.getResult().getContractAddressInfos().get(0).getContractAddress();
            System.out.println("Contract address: " + address);
        } else {
            System.out.println("Error: " + response.getErrorDesc());
        }

        return address;
    }

    public String contractInfoQuery() {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "contractInfo");

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }

    /**
     * Setting seller.
     * @return The tx hash.
     */
    public String setSellerTx(String sourcePrivateKey, String sourceAddress, String fullName, String shortName, String contact, String organizationalCode, String corporateName, String cardNumber, String businessLicense, String cardFrontPhoto, String cardBackPhoto) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("1");

        // 1. Getting the account nonce, and the nonce must add 1.
        Long nonce = getAccountNonce(sourceAddress) + 1;

        // 2. Building the initInput
        JSONObject input = new JSONObject();
        input.put("method", "setSeller");
        JSONObject params = new JSONObject();
        params.put("companyFullName", fullName);
        params.put("companyShortName", shortName);
        params.put("companyContact", contact);
        JSONObject companyCertification = new JSONObject();
        companyCertification.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        companyCertification.put("1", buildAdditionIndex("0", "企业组织机构代码", "text", organizationalCode, "-", "-"));
        companyCertification.put("2", buildAdditionIndex("0", "法人名称", "text", corporateName, "-", "-"));
        companyCertification.put("3", buildAdditionIndex("0", "法人身份证号", "text", cardNumber, "-", "-"));
        companyCertification.put("4", buildAdditionIndex("0", "营业执照照片", "image", businessLicense, "-", "-"));
        companyCertification.put("5", buildAdditionIndex("0", "法人身份证正面照片", "image", cardFrontPhoto, "-", "-"));
        companyCertification.put("6", buildAdditionIndex("0", "法人身份证反面照片", "image", cardBackPhoto, "-", "-"));
        params.put("companyCertification", companyCertification);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    public String sellInfoQuery() {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "sellerInfo");

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Setting document.
     * @return The tx hash.
     */
    public String setDocumentTx(String sourcePrivateKey, String sourceAddress, String documentId, String documentName, String documentUrl, String documentHashType, String documentHash) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("1");

        // 1. Building the input of 'setDocument'.
        JSONObject input = new JSONObject();
        input.put("method", "setDocument");
        JSONObject params = new JSONObject();
        params.put("id", documentId);
        params.put("name", documentName);
        params.put("url", documentUrl);
        params.put("hashType", documentHashType);
        params.put("hash", documentHash);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the document information.
     * @return The document information.
     */
    public String documentInfoQuery(String documentId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "documentInfo");
        JSONObject params = new JSONObject();
        params.put("documentId", documentId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }

    public String allDocumentsQuery() {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "allDocuments");

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Creating the SPU.
     * @return The hash.
     */
    public String createSPUTx(String sourcePrivateKey, String sourceAddress, String spuId, String spuName, String spuType, String spuPrice, String spuBrand, String spuModel) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");


        // 1. Building the input of 'createSpu'.
        JSONObject input = new JSONObject();
        input.put("method", "createSpu");
        JSONObject params = new JSONObject();
        params.put("id", spuId);
        params.put("name", spuName);
        params.put("type", spuType);
        JSONObject attributes = new JSONObject();
        attributes.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        attributes.put("1", buildAdditionIndex("0", "参考号", "float", spuPrice, "2", "CNY"));
        attributes.put("2", buildAdditionIndex("0", "品牌", "text", spuBrand, "-", "-"));
        attributes.put("3", buildAdditionIndex("0", "型号", "text", spuModel, "-", "-"));
        params.put("attributes", attributes);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    /**
     * Setting the SPU.
     * @return The hash.
     */
    public String setSPUTx(String sourcePrivateKey, String sourceAddress, String spuId, String spuName, String spuType, String spuPrice, String spuBrand, String spuModel) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");


        // 1. Building the input of 'createSpu'.
        JSONObject input = new JSONObject();
        input.put("method", "setSpu");
        JSONObject params = new JSONObject();
        params.put("spuId", spuId);
        params.put("name", spuName);
        params.put("type", spuType);
        JSONObject attributes = new JSONObject();
        attributes.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        attributes.put("1", buildAdditionIndex("0", "参考号", "float", spuPrice, "2", "CNY"));
        attributes.put("2", buildAdditionIndex("0", "品牌", "text", spuBrand, "-", "-"));
        attributes.put("3", buildAdditionIndex("0", "型号", "text", spuModel, "-", "-"));
        params.put("attributes", attributes);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the spu information.
     * @return The spu information.
     */
    public String SPUInfoQuery(String spuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "spuInfo");
        JSONObject params = new JSONObject();
        params.put("spuId", spuId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Creating the tranche.
     * @return The tx hash.
     */
    public String createTrancheTx(String sourcePrivateKey, String sourceAddress, String trancheId, String trancheDesc, String startTime, String endTime) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("1.4");

        // 1. Building the input of 'createTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "createTranche");
        JSONObject params = new JSONObject();
        params.put("id", trancheId);
        params.put("description", trancheDesc);
        JSONObject limits = new JSONObject();
        JSONObject validityPeriod = new JSONObject();
        validityPeriod.put("startTime", startTime);
        validityPeriod.put("endTime", endTime);
        limits.put("validityPeriod", validityPeriod);
        params.put("limits", limits);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the tranche information.
     * @return The tranche information.
     */
    public String trancheInfoQuery(String trancheId) {
         // Init input
        JSONObject input = new JSONObject();
        input.put("method", "trancheInfo");
        JSONObject params = new JSONObject();
        params.put("trancheId", trancheId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Issuing the SKU tokens
     * @return The tx hash.
     */
    public String issueSKUTokensTx(String sourcePrivateKey, String sourceAddress, String skuId, String trancheId, Boolean isDefaultTranche, String spuId, String skuName, String tokenSymbol, String tokenId, String tokenInfo, String skuDesc, String mainIcon, JSONArray viceIcons, JSONArray skuLabel, String redemptionAddress, String redemptionPeriod, String acceptanceId, JSONArray skuAbstract, JSONObject attributes) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("1.5");

        // 1. Building the input of 'issueByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "issue");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("isDefaultTranche", isDefaultTranche);
        params.put("spuId", spuId);
        params.put("name", skuName);
        params.put("symbol", tokenSymbol);
        params.put("tokenId",  tokenId);
        params.put("tokenInfo", tokenInfo);
        params.put("description", skuDesc);
        params.put("mainIcon", mainIcon);
        params.put("viceIcons", viceIcons);
        params.put("labels", skuLabel);
        params.put("redemptionAddress", redemptionAddress);
        params.put("redemptionPeriod", redemptionPeriod);
        params.put("acceptanceId", acceptanceId);
        params.put("abstract", skuAbstract);
        params.put("attributes", attributes);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    /**
     * Set new logic address.
     * @return The register tx hash.
     */
    public String setLogicAddressTx(String sourcePrivateKey, String sourceAddress, String logicAddress) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("1");

        // 1. Building the input
        JSONObject input = new JSONObject();
        input.put("method", "setLogicContract");
        JSONObject params = new JSONObject();
        params.put("newLogicAddress", logicAddress);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    /**
     * Setting the SKU.
     * @return The tx hash.
     */
    public String setSKUTx(String sourcePrivateKey, String sourceAddress, String skuId, String skuName, String tokenSymbol, String skuDesc, String mainIcon, JSONArray viceIcons, JSONArray skuLabel, String redemptionAddress, String acceptanceId, JSONArray skuAbstract, JSONObject attributes) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'authorizeSku'.
        JSONObject input = new JSONObject();
        input.put("method", "setSku");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("name", skuName);
        params.put("symbol", tokenSymbol);
        params.put("description", skuDesc);
        params.put("mainIcon", mainIcon);
        params.put("viceIcons", viceIcons);
        params.put("labels", skuLabel);
        params.put("redemptionAddress", redemptionAddress);
        params.put("acceptanceId", acceptanceId);
        params.put("abstract", skuAbstract);
        params.put("attributes", attributes);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    public String skusOfSpuQuery(String spuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "skusOfSpu");
        JSONObject params = new JSONObject();
        params.put("spuId", spuId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    public String skusOfTrancheQuery(String trancheId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "skusOfTranche");
        JSONObject params = new JSONObject();
        params.put("trancheId", trancheId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }

    public String tranchesOfSkuQuery(String skuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "tranchesOfSku");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }

    public String tranchesOfQuery(String skuId, String address) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "tranchesOf");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("address", address);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }

    public String acceptancesOfSkuQuery(String skuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "acceptancesOfSku");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    public String assignToTrancheTx(String sourcePrivateKey, String sourceAddress, String skuId, String toTrancheId, String tokenId) {

        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'authorizeSku'.
        JSONObject input = new JSONObject();
        input.put("method", "assignToTranche");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("toTrancheId", toTrancheId);
        params.put("tokenId", tokenId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    /**
     * set the skus choice of spu
     * @return
     */
    public String setSkusChoiceTx(String sourcePrivateKey, String sourceAddress, String spuId, Object choice) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'authorizeSku'.
        JSONObject input = new JSONObject();
        input.put("method", "setSkusChoice");
        JSONObject params = new JSONObject();
        params.put("spuId", spuId);
        params.put("choice", choice);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Adding the issuance of SKU Tokens
     * @return The tx hash.
     */
    public String additionalIssuanceTx(String sourcePrivateKey, String sourceAddress, String skuId, String trancheId, String tokenId, String tokenInfo) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'additionalIssuance'.
        JSONObject input = new JSONObject();
        input.put("method", "additionalIssuance");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("tokenId", tokenId);
        params.put("tokenInfo", tokenInfo);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    public String destroyTx(String sourcePrivateKey, String sourceAddress, String address, String tokenId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'additionalIssuance'.
        JSONObject input = new JSONObject();
        input.put("method", "destroy");
        JSONObject params = new JSONObject();
        params.put("address", address);
        params.put("tokenId", tokenId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    /**
     * Setting the autohrizers.
     * @return The tx hash.
     */
    public String setAuthorizersTx(String sourcePrivateKey, String sourceAddress, String skuId, JSONArray authorizers) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'authorizeSku'.
        JSONObject input = new JSONObject();
        input.put("method", "setAuthorizers");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("authorizers", authorizers);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Autorizing the issuance of sku tokens.
     * @return The tx hash.
     */
    public String autorizeSKUTx(String sourcePrivateKey, String sourceAddress, String skuId, String trancheId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'authorizeSku'.
        JSONObject input = new JSONObject();
        input.put("method", "authorizeSku");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    public String authorizedSkuQuery(String skuId, String trancheId, String authorizer) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "authorizedSku");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("authorizer", authorizer);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Querying the SKU Token information.
     * @return The SKU Token information
     */
    public String SKUTokenInfoQuery(String skuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "tokenInfo");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }

    /**
     * Adding the acceptance to sku.
     * @return The tx hash.
     */
    public String addAcceptanceToSkuTx(String sourcePrivateKey, String sourceAddress, String skuId, String acceptanceId) {

        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 3. Building the input of 'setController'.
        JSONObject input = new JSONObject();
        input.put("method", "addAcceptanceToSku");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("acceptanceId", acceptanceId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    /**
     * Deleting the acceptance to sku.
     * @return The tx hash.
     */
    public String delAcceptanceFromSkuTx(String sourcePrivateKey, String sourceAddress, String skuId, String acceptanceId) {

        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 3. Building the input of 'setController'.
        JSONObject input = new JSONObject();
        input.put("method", "delAcceptanceFromSku");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("acceptanceId", acceptanceId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Setting controller.
     * @return The tx hash.
     */
    public String setControllerTx(String sourcePrivateKey, String sourceAddress, String controllerAddress, String controllerName, String controllerContact) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 3. Building the input of 'setController'.
        JSONObject input = new JSONObject();
        input.put("method", "setController");
        JSONObject params = new JSONObject();
        params.put("address", controllerAddress);
        params.put("name", controllerName);
        params.put("contact", controllerContact);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the controller information.
     * @return The controller information.
     */
    public String controllerInfoQuery(String controllerAddress) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "controllerInfo");
        JSONObject params = new JSONObject();
        params.put("address", controllerAddress);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }

    /**
     * Transferring the sku tokens of specified tranche to other account.
     * @return The tx hash.
     */
    public String transferTx(String sourcePrivateKey, String sourceAddress, String toAddress, String tokenId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'transfer'.
        JSONObject input = new JSONObject();
        input.put("method", "transfer");
        JSONObject params = new JSONObject();
        params.put("to", toAddress);
        params.put("tokenId", tokenId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the balance of an account.
     * @return The balance.
     */
    public String balanceOfQuery(String address, String skuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "balanceOf");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("address", address);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Querying the balance of specified tranche.
     * @return The tranche balance.
     */
    public String balanceOfByTrancheQuery(String address, String skuId, String trancheId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "balanceOfByTranche");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("address", address);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }

    public String balanceOfByLockupQuery(String address, String skuId, String trancheId, String lockupId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "balanceOfByLockup");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("lockupId", lockupId);
        params.put("address", address);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Approving the SKU Tokens to spender.
     * @return The tx hash.
     */
    public String approveTx(String sourcePrivateKey, String sourceAddress, String spender, String tokenId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'approve'.
        JSONObject input = new JSONObject();
        input.put("method", "approve");
        JSONObject params = new JSONObject();
        params.put("spender", spender);
        params.put("tokenId", tokenId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the allowance.
     * @return The allowance.
     */
    public String allowanceQuery(String owner, String spender, String skuId, String trancheId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "allowance");
        JSONObject params = new JSONObject();
        params.put("owner", owner);
        params.put("spender", spender);
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * The spender move the allownce to other account.
     * @return The tx hash.
     */
    public String transferFromTx(String sourcePrivateKey, String sourceAddress, String fromAddress, String toAddress, String tokenId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'transferFrom'.
        JSONObject input = new JSONObject();
        input.put("method", "transferFrom");
        JSONObject params = new JSONObject();
        params.put("from", fromAddress);
        params.put("to", toAddress);
        params.put("tokenId", tokenId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Setting an accetance.
     * @return The tx hash.
     */
    public String setAcceptanceTx(String sourcePrivateKey, String sourceAddress, String acceptanceId, String acceptorPublicKey, String acceptanceFullName, String acceptanceShortName, String acceptanceLogo, String acceptanceContact) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("1");

        // 1. Building the input of 'setAcceptance'.
        JSONObject input = new JSONObject();
        input.put("method", "setAcceptance");
        JSONObject params = new JSONObject();
        params.put("id", acceptanceId);
        params.put("publicKey", acceptorPublicKey);
        params.put("fullName", acceptanceFullName);
        params.put("shortName", acceptanceShortName);
        params.put("logo", acceptanceLogo);
        params.put("contact", acceptanceContact);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the acceptance information.
     * @return The acceptance information.
     */
    public String acceptanceInfoQuery(String acceptanceId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "acceptanceInfo");
        JSONObject params = new JSONObject();
        params.put("acceptanceId", acceptanceId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Requesting the redemption.
     * @return The tx hash.
     */
    public String requestRedemptionTx(String sourcePrivateKey, String sourceAddress, String redemptionId, String tokenId, String description, Object addition) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'requestRedemption'.
        JSONObject input = new JSONObject();
        input.put("method", "requestRedemption");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
        params.put("tokenId", tokenId);
        params.put("description", description);
        params.put("addition", addition);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the redemption information.
     * @return The redemption information.
     */
    public String redemptionInfoQuery(String redemptionId, String applicant) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "redemptionInfo");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
        params.put("applicant", applicant);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * The acceptor pays redemption.
     * @return The tx hash.
     */
    public String redeemTx(String sourcePrivateKey, String sourceAddress, String redemptionId, String applicant, String logistics) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'redemption'.
        JSONObject input = new JSONObject();
        input.put("method", "redeem");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
        params.put("applicant", applicant);
        params.put("logistics", logistics);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * The token holder confirm the redemption right.
     * @return The tx hash.
     */
    public String confirmRedemptionTx(String  sourcePrivateKey, String sourceAddress, String redemptionId, String applicant) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'confirmRedemption'.
        JSONObject input = new JSONObject();
        input.put("method", "confirmRedemption");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
        params.put("applicant", applicant);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    public String lockupInfoQuery(String lockupId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "lockupInfo");
        JSONObject params = new JSONObject();
        params.put("lockupId", lockupId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;

    }

    public String cancelRedemptionTx(String  sourcePrivateKey, String  sourceAddress, String  redemptionId, String  applicant, String reason) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'confirmRedemption'.
        JSONObject input = new JSONObject();
        input.put("method", "cancelRedemption");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
        params.put("applicant", applicant);
        params.put("reason", reason);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }

    /**
     * 查询指定合约文件的合约代码
     * @param fileName　合约文件名称
     * @return String
     */
    public String getContractCodeFromFile(String fileName) {
        StringBuilder result = new StringBuilder();
        try{
            String classPath = this.getClass().getClassLoader().getResource("").getPath();
            File file = new File(classPath + fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * Getting the nonce of an account.
     * @param address The address of an account.
     * @return long
     */
    public long getAccountNonce(String address) {
        long nonce = 0;
        // Init request
        AccountGetNonceRequest request = new AccountGetNonceRequest();
        request.setAddress(address);

        // Call getNonce
        AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
        if (0 == response.getErrorCode()) {
            nonce = response.getResult().getNonce();
        }

        return nonce;
    }


    /**
     * Building custom attribute data
     * @param parentId The id of parent
     * @param name     The name of attribute
     * @param type     The type of attribute
     * @param value    The data of attribute
     * @param decimals The decimals of data
     * @param unit     The uint of data
     * @return JSONArray
     */
	public JSONArray buildAdditionIndex(String parentId, String name, String type, String value, String decimals, String unit) {
		JSONArray index = new JSONArray();
        index.add(parentId);
        index.add(name);
        index.add(type);
        index.add(value);
        index.add(decimals);
        index.add(unit);
        return index;
	}


    /**
     * Querying information on chain.
     * @param input The query function parameter in contract.
     * @return The information.
     */
	public String queryInfo(String input) {

        // Init request
        ContractCallRequest request = new ContractCallRequest();
        request.setContractAddress(entryAddress);
        request.setFeeLimit(1000000000L);
        request.setOptType(2);
        request.setInput(input);

        // Call call
        JSONObject queryResult = new JSONObject();
        ContractCallResponse response = sdk.getContractService().call(request);
        if (response.getErrorCode() == 0) {
            ContractCallResult result = response.getResult();
            JSONObject errorResult = result.getQueryRets().getJSONObject(0).getJSONObject("error");
            if (errorResult != null) {
                String dataException = errorResult.getJSONObject("data").getString("exception");
                queryResult = JSON.parseObject(dataException);
                System.out.println("Error: " + queryResult.getString("msg"));
            } else {
                queryResult.put("code", 0);
                queryResult.put("msg", result.getQueryRets().getJSONObject(0).getJSONObject("result").getString("value"));
                System.out.println(queryResult.getString("msg"));
            }
        } else {
            queryResult.put("code", response.getErrorCode());
            queryResult.put("msg", response.getErrorDesc());
            System.out.println("error: " + response.getErrorDesc());
        }

        return queryResult.toJSONString();
    }


    /**
     * Submitting transaction.
     * @param privateKey The source public key to submit transaction.
     * @param sourceAddress The source address to submit transaction.
     * @param input The main function parameter in contract.
     * @param transMetadata The transaction metadata.
     * @param gasPrice The gas price.
     * @param feeLimit The fee limit.
     * @return The tx hash.
     */
	public String submitTrasaction(String privateKey, String sourceAddress, String input, String transMetadata, Long gasPrice, Long feeLimit) {
        // 1. Transaction initiation account's Nonce + 1
        Long nonce = getAccountNonce(sourceAddress) + 1;

        // 2. Building ContractInvokeByBUOperation
        ContractInvokeByBUOperation operation = new ContractInvokeByBUOperation();
        operation.setContractAddress(entryAddress);
        operation.setInput(input);
        operation.setBuAmount(0L);

        BaseOperation[] operations = { operation };

        // 4. Broadcasting the transaction
        String txHash = broadcastTransaction(privateKey, sourceAddress, operations, nonce, gasPrice, feeLimit, transMetadata);

        return txHash;
    }


    /**
     * @param senderPrivateKey The account public key to start transaction
     * @param senderAddresss   The account address to start transaction
     * @param operations       operations
     * @param senderNonce      Transaction initiation account's Nonce
     * @param gasPrice         Gas price
     * @param feeLimit         fee limit
     * @return java.lang.String transaction hash
     * @author riven
     */
    public String broadcastTransaction(String senderPrivateKey, String senderAddresss, BaseOperation[] operations, Long senderNonce, Long gasPrice, Long feeLimit, String transMetadata) {
        // 1. Build transaction
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(senderNonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        for (int i = 0; i < operations.length; i++) {
            transactionBuildBlobRequest.addOperation(operations[i]);
        }

        transactionBuildBlobRequest.setMetadata(transMetadata);

        // 2. Build transaction BLob
        String transactionBlob;
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() != 0) {
            System.out.println("Error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
        TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        transactionBlob = transactionBuildBlobResult.getTransactionBlob();

        // 3. Sign transaction BLob
        String[] signerPrivateKeyArr = {senderPrivateKey};
        TransactionSignRequest transactionSignRequest = new TransactionSignRequest();
        transactionSignRequest.setBlob(transactionBlob);
        for (int i = 0; i < signerPrivateKeyArr.length; i++) {
            transactionSignRequest.addPrivateKey(signerPrivateKeyArr[i]);
        }
        TransactionSignResponse transactionSignResponse = sdk.getTransactionService().sign(transactionSignRequest);
        if (transactionSignResponse.getErrorCode() != 0) {
            System.out.println("Error: " + transactionSignResponse.getErrorDesc());
            return null;
        }

        // 4. Broadcast transaction
        String Hash = null;
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResponse.getResult().getSignatures());
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (0 == transactionSubmitResponse.getErrorCode()) {
            Hash = transactionSubmitResponse.getResult().getHash();
        } else {
            System.out.println("Error: " + transactionSubmitResponse.getErrorDesc());
        }
        return Hash;
    }

    /**
     * Getting the tx status.
     * @param txHash The hash of a tx.
     * @return 1 Success, 0 Failure, -1 Timeout.
     */
    public JSONObject getTxStatus(String txHash) {
        long startTime = System.currentTimeMillis();
        JSONObject txStatus;
        while (true) {
            JSONObject result = checkTxStatusByHash(txHash);
            int code = result.getIntValue("code");
            if (0 == code || code != 4) {
                txStatus = result;
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 50000) {
                txStatus = result;
                txStatus.put("code", -1);
                txStatus.put("msg", "Time out");
            }
        }
        return txStatus;
    }


    /**
     * Checking whether the tx succeeded.
     * @param txHash The hash of a tx.
     * @return boolean
     */
    public boolean MakeSureTxSuccess(String txHash) {
        long startTime = System.currentTimeMillis();
        while (true) {
            JSONObject result = checkTxStatusByHash(txHash);
            int code = result.getIntValue("code");
            if (0 == code) {
                break;
            } else if (code != 4) {
                System.out.println("error: 交易(" + txHash + ") 执行失败");
                return false;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 50000) {
                System.out.println("error: 交易(" + txHash + ") 执行超时");
                return false;
            }
        }
        return true;
    }

    /**
     * Checking the tx status
     * @param txHash The hash of tx
     * @return int 1: Success, 0: Failure, -1: Not Found
     */
    public JSONObject checkTxStatusByHash(String txHash) {
        // Initializing the request.
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(txHash);

        // Getting the contract address.
        TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
        JSONObject result = new JSONObject();
        result.put("code", 0);
        result.put("msg", "SUCCESS");
        if (response.getErrorCode() == 0) {
            int errorCode = response.getResult().getTransactions()[0].getErrorCode();
            if (errorCode != 0){
                String errorDesc = response.getResult().getTransactions()[0].getErrorDesc();
                JSONObject errorResult = null;
                try {
                    errorResult = (JSONObject)JSON.parse(errorDesc);
                } catch (Exception e) {
                    result.put("code", errorCode);
                    result.put("msg", errorDesc);
                }
                if (errorResult != null) {
                    try {
                        result = (JSONObject)JSON.parse(errorResult.getString("exception"));
                    } catch (Exception e) {
                        result.put("msg", errorDesc);
                        result.put("code", errorCode);
                    }
                }
            }
        } else {
            result.put("code", response.getErrorCode());
            result.put("msg", response.getErrorDesc());
        }

        return result;
    }

    public byte[] getContentFromUrl(String urlPath) {
        byte[] ret = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            InputStream inStream = connection.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while( (len=inStream.read(buffer)) != -1 ){
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inStream.close();
            //把outStream里的数据写入内存
            ret = outStream.toByteArray();
        } catch (Exception e) {

        }
        return ret;
    }

}
