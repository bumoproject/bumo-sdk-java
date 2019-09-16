'use strict';

const keys = {
    sel: 'seller',
    crt: 'contract',
    doc: 'document_',
    docspgs: 'documents_pages',
    docspg: "documents_page_",
    spu: 'spu_',
    trn: 'tranche_',
    sku: 'sku_token_',
    skutrnspy: 'sku_tranche_supply_',
    skutrnspgs: 'sku_tranches_pages_',
    skutrnspg: 'sku_tranches_page_',
    trnskuspgs: 'tranche_skus_pages_',
    trnskuspg: 'tranche_skus_page_',
    spuskuspgs: 'spu_skus_pages_',
    spuskuspg: 'spu_skus_page_',
    skuauth: 'sku_authorization_',
    ble: 'balance_',
    bletrn: 'balance_tranche_',
    bletrnspgs: 'balance_tranches_pages_',
    bletrnspg: 'balance_tranches_page_',
    bletrnlockspgs: 'balance_tranche_lockups_pages_',
    bletrnlockspg: 'balance_tranche_lockups_page_',
    bletrnlock: 'balance_tranche_lockup_',
    lock: 'lockup_',
    acp: 'acceptance_',
    alw: 'allowance_',
    repn: 'redemption_'
};

const error = {
    NOT_CRT_ERR: {
        code: 19995,
        msg: 'The register interface must be called first.'
    },
    NOT_ETY_ERR: {
        code: 19996,
        msg: 'In addition to the register interface, other interfaces must be called from the entry contract.'
    },
    REG_ERR: {
        code: 19997,
        msg: 'The register interface is already called.'
    },
    NEW_LOG_ADDR_ERR: {
        code: 19998,
        msg: 'The new logic address is invalid.'
    },
    CRT_SET_ERR: {
        code: 19999,
        msg: 'The logic contract cannot be set up repeatedly.'
    },
    CPY_FL_NM_ERR: {
        code: 20000,
        msg: 'The companyFullName must be a string and its length must be between 1 and 1024.'
    },
    CPY_ST_NM_ERR: {
        code: 20001,
        msg: 'The companyShortName must be a string and its length must be between 1 and 64.'
    },
    CPY_CNT_ERR: {
        code: 20002,
        msg: 'The companyContact must be a string and its length must be between 1 and 64.'
    },
    MTD_ERR: {
        code: 20004,
        msg: 'The method type is invalid.'
    },
    DOC_ID_ERR: {
        code: 20005,
        msg: 'The document id must be string and its length must be between 1 and 32.'
    },
    FL_NM_ERR: {
        code: 20006,
        msg: 'The fullName must be string and its length must be between 1 and 1024.'
    },
    ST_NM_ERR: {
        code: 20007,
        msg: 'The shortName must be string and its length must be between 1 and 64.'
    },
    DOC_URL_ERR: {
        code: 20008,
        msg: 'The document url must be string and its length must be between 1 and 10240.'
    },
    HASH_TYPE_ERR: {
        code: 20009,
        msg: 'The hash_type must be string and its length must be between 1 and 64.'
    },
    HASH_ERR: {
        code: 20010,
        msg: 'The hash must be string and its length must be between 1 and 2048.'
    },
    NOT_SEL: {
        code: 20011,
        msg: 'The sender should be a seller.'
    },
    DOCS_MORE_ERR: {
        code: 20012,
        msg: 'The pages of all documents cannot be bigger than 2000.'
    },
    DOC_NOT_EST: {
        code: 20013,
        msg: 'The specified document does not exist.'
    },
    SPU_ID_ERR: {
        code: 20014,
        msg: 'The spu id must be string and its length must be between 1 and 32.'
    },
    SPU_TYPE_ERR: {
        code: 20015,
        msg: 'The spu type must be string and its length must be between 1 and 64.'
    },
    SPU_EST: {
        code: 20016,
        msg: 'The spu already exists.'
    },
    SPU_NOT_EST: {
        code: 20017,
        msg: 'The spu does not exist.'
    },
    TRN_ID_ERR: {
        code: 20018,
        msg: 'The tranche id must be string and its length must be between 1 and 32.'
    },
    TRN_EST: {
        code: 20019,
        msg: 'The tranche already exists.'
    },
    TRN_NOT_EST: {
        code: 20020,
        msg: 'The tranche does not exist.'
    },
    TRN_NOT_DFT: {
        code: 20021,
        msg: 'The tranche id must be default tranche.'
    },
    TRN_DFT: {
        code: 20022,
        msg: 'The tranche id cannot be default tranche.'
    },
    SKU_ID_ERR: {
        code: 20023,
        msg: 'The sku id must be string and its length must be between 1 and 32.'
    },
    IS_DFT_TRN_ERR: {
        code: 20024,
        msg: 'The isDefaultTranche must be boolean.'
    },
    TK_BML_ERR: {
        code: 20025,
        msg: 'The token symbol must be string and its length must be between 1 and 16.'
    },
    TK_SPY_ERR: {
        code: 20026,
        msg: 'The token supply must be bigger than 0.'
    },
    TK_DCS_ERR: {
        code: 20027,
        msg: 'The token decimals must be between 0 and 8.'
    },
    MAIN_ICN_ERR: {
        code: 20028,
        msg: 'The main icon must be string, and its length must be between 1 and 10240.'
    },
    VICE_ICNS_ERR: {
        code: 20029,
        msg: 'The vice icons must be array, its size must be between 0 and 5 and each length must be between 1 and 10240.'
    },
    DES_ERR: {
        code: 20030,
        msg: 'The description must be string and its length must be between 0 and 64K.'
    },
    LBL_ERR: {
        code: 20031,
        msg: 'The labels must be array, its size must be between 0 and 20 and each length must be between 1 and 1024.'
    },
    REPN_ADDR_ERR: {
        code: 20032,
        msg: 'The redemption address is invalid.'
    },
    ABT_ERR: {
        code: 20033,
        msg: 'The abstracts must be array, its size must be between 1 and 20 and each length must be between 1 and 32.'
    },
    ACP_ID_ERR: {
        code: 20034,
        msg: 'The length of the acceptanceId must be between 1 and 32.'
    },
    SKU_EST: {
        code: 20035,
        msg: 'The sku already exists.'
    },
    NAME_ERR: {
        code: 20036,
        msg: 'The name must be string and its length must be between 1 and 1024.'
    },
    ACP_EST_SKU_ERR: {
        code: 20037,
        msg: 'The acceptance already exists in sku.'
    },
    ACP_MORE_SKU_ERR: {
        code: 20038,
        msg: 'The pages of all acceptances in sku cannot be bigger than 2000.'
    },
    ACP_NOT_EST_SKU_ERR: {
        code: 20039,
        msg: 'The acceptance does not exist in sku.'
    },
    SKU_NOT_EST: {
        code: 20040,
        msg: 'The sku does not exist.'
    },
    SKU_NO_DFT_TRN: {
        code: 20041,
        msg: 'Only default tranche can be assigned.'
    },
    FC_ERR: {
        code: 20042,
        msg: 'The faceValue must be string and its length must be between 1 and 32.'
    },
    ONR_ERR: {
        code: 20043,
        msg: 'The owner is invalid.'
    },
    VAL_ERR: {
        code: 20044,
        msg: 'The value must be bigger than 0.'
    },
    TO_TRN_DEFAULT_ERR: {
        code: 20045,
        msg: 'The target tranche cannot be default tranche.'
    },
    NO_ALW: {
        code: 20046,
        msg: 'The spender does not have the allowance.'
    },
    ALW_NOT_EGH: {
        code: 20047,
        msg: 'The allowance is not enough.'
    },
    BLE_NOT_EGH: {
        code: 20048,
        msg: 'The balance is not enough.'
    },
    ATOS_ERR: {
        code: 20049,
        msg: 'The authorizers must be array, and the size must be between 1 and 5.'
    },
    ATOR_NOT_EST: {
        code: 20050,
        msg: 'The authorizer does not exist in authorizers of the sku.'
    },
    TRN_NOT_IN_SKU: {
        code: 20051,
        msg: 'The tranche id is not in sku tranches.'
    },
    COC_ERR: {
        code: 20052,
        msg: 'The choice must be an object and the length of the choice that is converted to string must be between 1 and 40960.'
    },
    ADDR_ERR: {
        code: 20053,
        msg: 'The address is invalid.'
    },
    SEL_RDM_OTR: {
        code: 20054,
        msg: 'The seller cannot destroy other\'s tokens.'
    },
    SPR_ERR: {
        code: 20055,
        msg: 'The spender is invalid.'
    },
    FRM_ERR: {
        code: 20056,
        msg: 'The from is a invalid address.'
    },
    TO_ERR: {
        code: 20057,
        msg: 'The to is a invalid address.'
    },
    LOGO_ERR: {
        code: 20058,
        msg: 'The logo must be string, and its length must be between 1 and 10240.'
    },
    CAT_ERR: {
        code: 20059,
        msg: 'The contact must be a string and its length must be between 1 and 64.'
    },
    CFM_PRD_ERR: {
        code: 20060,
        msg: 'The period of the redemption confirmed by redemption applicant must be bigger than 0.'
    },
    ACP_NOT_EST: {
        code: 20061,
        msg: 'The acceptance does not exist.'
    },
    REPN_ID_ERR: {
        code: 20062,
        msg: 'The redemption id must be string and its length must be between 1 and 32.'
    },
    APT_ERR: {
        code: 20063,
        msg: 'The applicant is invalid.'
    },
    RPN_PRD_ERR: {
        code: 20064,
        msg: 'The period of redemption redeemed by the acceptor must be bigger than 0.'
    },
    REPN_NOT_EST: {
        code: 20065,
        msg: 'The redemption does not exist.'
    },
    REPN_REQ_ERR: {
        code: 20066,
        msg: 'The redemption status must be 0.'
    },
    NOT_APR: {
        code: 20067,
        msg: 'The sender is not acceptor.'
    },
    REPN_EST: {
        code: 20068,
        msg: 'The redemption already exists.'
    },
    NOT_APT: {
        code: 20070,
        msg: 'The sender should be the applicant of the redemption.'
    },
    RSN_ERR: {
        code: 20071,
        msg: 'The reason must be string and its length must be between 1 and 64K.'
    },
    NOT_SEL_APT: {
        code: 20072,
        msg: 'The sender must be sellr or redemption applicant.'
    },
    LMS_ERR: {
        code: 20073,
        msg: 'The limits must be json object.'
    },
    VP_ERR: {
        code: 20074,
        msg: 'The validityPeriod in limits must be json object.'
    },
    NOT_APT_SEL_APR: {
        code: 20075,
        msg: 'The provider must be applicant, seller or acceptor.'
    },
    PVR_ERR: {
        code: 20077,
        msg: 'The provider is invalid.'
    },
    SAS_ERR: {
        code: 20079,
        msg: 'The status must be 1 or 2.'
    },
    PUB_ERR: {
        code: 20083,
        msg: 'The public key of the acceptor is invalid.'
    },
    REPN_RED_ERR: {
        code: 20091,
        msg: 'The redemption status must be 1.'
    },
    LOCK_ID_ERR: {
        code: 20092,
        msg: 'The lockup id must be string and its length must be between 1 and 64.'
    },
    SRT_TM_ERR: {
        code: 20093,
        msg: 'The start time must be bigger than 0.'
    },
    END_TM_ERR: {
        code: 20094,
        msg: 'The end time must be bigger than this block time and start time.'
    },
    LOCK_EST: {
        code: 20095,
        msg: 'The lockup already exists.'
    },
    LOCK_NOT_EST: {
        code: 20096,
        msg: 'The lockup does not exist.'
    },
    REPN_NOT_EXP_ERR: {
        code: 20097,
        msg: 'Only redemption that is expired can be cancelled.'
    },
    NOT_APT_SEL: {
        code: 20098,
        msg: 'The sender should be the applicant of the redemption or seller.'
    },
    LOCK_EPD: {
        code: 20099,
        msg: 'The lockup is expired.'
    },
    LG_INFO_ERR: {
        code: 20100,
        msg: 'The logistics must be string and its length must be between 1 and 10240.'
    }
};

let gThisAddress = '';
const gTxSender = Chain.tx.sender;
const gMsgSender = Chain.msg.sender;
const gBlockTime = Chain.block.timestamp;

function _makeKey(_key, _pa1, _pa2, _pa3, _pa4, _idx) {
    if (_idx !== undefined) {
        return (_pa1 === undefined ? `${_key}${_idx}`: (_pa2 === undefined ? `${_key}${_pa1}_${_idx}` : (_pa3 === undefined ? `${_key}${_pa1}_${_pa2}_${_idx}` : (_pa4 === undefined ?  `${_key}${_pa1}_${_pa2}_${_pa3}_${_idx}` : `${_key}${_pa1}_${_pa2}_${_pa3}_${_pa4}_${_idx}`))));
    }
    return (_pa1 === undefined ? _key : (_pa2 === undefined ? `${_key}${_pa1}` : (_pa3 === undefined ? `${_key}${_pa1}_${_pa2}`: (_pa4 === undefined ? `${_key}${_pa1}_${_pa2}_${_pa3}` : `${_key}${_pa1}_${_pa2}_${_pa3}_${_pa4}`))));
}

function _makeTlogSender() {
    return `${gTxSender}_${gMsgSender}`;
}

function _toJsn(_val) {
    return JSON.parse(_val);
}

function _toStr(_val) {
    return JSON.stringify(_val);
}

function _throwErr(_errCode) {
    return _toStr(_errCode);
}

function _throwErrMsg(_err, _msg) {
    const defErr = {
        code: _err.code,
        msg: _msg
    };
    return _toStr(defErr);
}

function _load(_key) {
    return Chain.load(_key);
}

function _store(_key, _val) {
    Chain.store(_key, _val);
}

/**
 *
 * @param {类别} _type 0: 数字   1: 对象  2:余额
 * @return {对象或数字字符串}
 */
function _loadVal(_type, _key, _pa1, _pa2, _pa3, _pa4) {
    let val = _load(_makeKey(_key, _pa1, _pa2, _pa3, _pa4));
    let obj = {};
    if (val === false) {
        if (_type === 2) {
            obj.total = 0;
            obj.available = 0;
        } else if (_type === 0) {
            obj = '0';
        }
    } else {
        if (_type !== 0) {
            obj = _toJsn(val);
        } else {
            obj = val;
        }
    }
    return obj;
}

function _del(_key) {
    Chain.del(_key);
}

function _checkStr(_var, _minLen, _maxLen) {
    return (_minLen === 0 && _var === undefined) || (typeof _var === 'string' && _var.length >= _minLen && _var.length <= _maxLen);
}

function _checkInt(_var, _min, _max) {
    return Utils.stoI64Check(_var) && Utils.int64Compare(_var, _min) >= 0 && Utils.int64Compare(_var, _max) <= 0;
}

function _checkJSNObj(_var) {
    return typeof _var === 'object' && _var.length === undefined;
}

function _checkJSNArr(_var, _minSize, _maxSize) {
    return (_minSize === 0 && _var === undefined) || (typeof _var === 'object' && _var.length !== undefined && _var.length >= _minSize && (_maxSize === 0 || _var.length <= _maxSize));
}

function _checkAddr(_addr) {
    return Utils.addressCheck(_addr) && Utils.int64Compare(Chain.getBalance(_addr), 0) > 0;
}

function _dayToTime(_prd) {
    return Utils.int64Mul(Utils.int64Mul(Utils.int64Mul(_prd, '24'), '3600'), '1000000');
}

function _isExist(_key, _errCode) {
    const value = _load(_key);
    Utils.assert(value !== false, _throwErr(_errCode));

    return value;
}

function _isNotExist(_key, _errCode) {
    const value = _load(_key);
    Utils.assert(value === false, _throwErr(_errCode));
}

function _checkEntryContract() {
    // Checking whether this contract is registered.
    const crtVal = _load(keys.crt);
    Utils.assert(crtVal !== false, _throwErr(error.NOT_CRT_ERR));

    // Checking whether the interface is called from the entry contract.
    const crt = _toJsn(crtVal);
    Utils.assert(Chain.thisAddress === crt.entryAddress, _throwErr(error.NOT_ETY_ERR));
}

function _isSeller(_addr) {
    const seller = _toJsn(_load(keys.sel));
    return (seller.address === _addr);
}

function _checkTranche(_trnId, _dftTrnId, _isDft, _isChkEst) {
    if (_trnId === undefined) {
        _trnId = '0';
    } else {
        Utils.assert(_checkStr(_trnId, 1, 32), _throwErr(error.TRN_ID_ERR));
        if (_isChkEst !== true) {
            _isExist(_makeKey(keys.trn, _trnId), error.TRN_NOT_EST);
        }
        if (_isDft === true) {
            // If the sku has default tranche, checking whether the tranche is default tranche.
            Utils.assert(_dftTrnId === undefined || _trnId === _dftTrnId, _throwErr(error.TRN_NOT_DFT));
        } else if (_isDft === false) {
            // If the sku has not default tranche, checking whether the tranche is not default tranche.
            Utils.assert(_dftTrnId === undefined || _trnId !== _dftTrnId, _throwErr(error.TRN_DFT));
        }
    }
    return _trnId;
}

function _setSeller(_sender, _cpyFlNm, _cpyStNm, _cpyCat, _des, _cpyCer, _cfmPrd) {
    // Checking parameters.
    Utils.assert(_checkStr(_cpyFlNm, 1, 1024), _throwErr(error.CPY_FL_NM_ERR));
    Utils.assert(_checkStr(_cpyStNm, 1, 64), _throwErr(error.CPY_ST_NM_ERR));
    Utils.assert(_checkStr(_cpyCat, 1, 64), _throwErr(error.CPY_CNT_ERR));
    Utils.assert(_checkStr(_des, 0, 64000), _throwErr(error.DES_ERR));

    // Storing the seller.
    const sellerVal = _load(keys.sel);
    let seller = {};
    if (sellerVal !== false) {
        seller = _toJsn(sellerVal);
    }
    seller.address = _sender;
    seller.companyFullName = _cpyFlNm;
    seller.companyShortName = _cpyStNm;
    seller.companyContact = _cpyCat;
    if (_cfmPrd !== undefined) {
        seller.confirmPeriod = _cfmPrd;
    }
    seller.description = _des;
    seller.companyCertification = _cpyCer;

    _store(keys.sel, _toStr(seller));
}

/**
 * 检测数组子项是否满足要求
 * @param {Array} _arr [数组]
 * @param {int} _minLen [最小值]
 * @param {int} _maxLen [最大值]
 * @param {string} _itemName [子项名称]
 * @param {error} _err [错误码]
 */
function _checkArrayItem(_arr, _minLen, _maxLen, _itemName, _err) {
    if (_arr !== undefined) {
        const len = _arr.length;
        let i = 0;
        for (i = 0; i < len; i += 1) {
            Utils.assert(_checkStr(_arr[i], _minLen, _maxLen), _throwErrMsg(_err, `The ${_itemName} of index ${i} is not between ${_minLen} and ${_maxLen}.`));
        }
    }
}

/**
 * 检测并添加元素到列表中
 * @return -3: 添加元素超过列表允许的长度(1K)
 *         -2: 检测已存在该元素
 *         -1: 检测不存在该元素，且添加该元素成功
 */
function _checkAddArrayItem(_isAdd, _id, _key, _pa1, _pa2, _pa3, _pa4, _idx) {
    const key = _makeKey(_key, _pa1, _pa2, _pa3, _pa4, _idx);
    const val = _load(key);
    let items = [];
    if (val !== false) {
        const maxLen = "10000";
        const len = Utils.int64Add(val.length, _id.length);
        if (Utils.int64Compare(len, maxLen) > 0) {
            return -3;
        }
        items = JSON.parse(val);
        if (items.indexOf(_id) !== -1) {
            return -2;
        }
    }
    if (_isAdd) {
        items.push(_id);
        _store(key, _toStr(items));
    }
    return -1;
}

/**
 * 检测并减少列表中的元素
 * @return -1: 检测不存在该元素
 *          0: 检测存在该元素，且删除该元素成功
 */
function _checkSubArrayItem(_isSub, _id, _key, _pa1, _pa2, _pa3, _pa4, _idx) {
    const key = _makeKey(_key, _pa1, _pa2, _pa3, _pa4, _idx);
    const val = _load(key);
    if (val !== false) {
        let items = JSON.parse(val);
        const idx = items.indexOf(_id);
        if (idx === -1) {
            return -1;
        }
        if (_isSub) {
            items.splice(idx, 1);
            _store(key, _toStr(items));
        }
        return 0;
    }
    return -1;
}

/**
 * 检测并添加元素到某页的列表中
 * @return -3: 页数超出报错
 *         -2: 检测已存在该元素
 *         -1: 检测不存在该元素，并且添加该元素成功
 *         >0: 添加到元素到新页中，并返回新页数
 */
function _checkAddPageItem(_isAdd, _maxPgs, _pgcnt, _id, _key, _pa1, _pa2, _pa3, _pa4) {
    let ret = -1;
    let i = 0;
    ret = _checkAddArrayItem(_isAdd, _id, _key, _pa1, _pa2, _pa3, _pa4, _pgcnt);

    if (ret === -3 && _isAdd) {
        const newPgcnt = Utils.int64Add(_pgcnt, 1);
        if ((Utils.int64Compare(_maxPgs, 0) <= 0 && Utils.int64Compare(newPgcnt, 100000) <= 0) || Utils.int64Compare(newPgcnt, _maxPgs) <= 0) {
            _checkAddArrayItem(_isAdd, _id, _key, _pa1, _pa2, _pa3, _pa4, newPgcnt);
            ret = newPgcnt;
        }
    }
    return ret;
}

/**
 * 检测并减少某页中的元素
 * @return -1: 检测不存在该元素
 *          0: 检测存在该元素，且删除该元素成功
 */
function _checkSubPageItem(_isSub, _pgcnt, _id, _key, _pa1, _pa2, _pa3, _pa4) {
    let ret = -1;
    let i = 1;
    for (i = 1; Utils.int64Compare(i, _pgcnt) <= 0; i = Utils.int64Add(i, 1)) {
        ret = _checkSubArrayItem(_isSub, _id, _key, _pa1, _pa2, _pa3, _pa4, i);
        if (ret === 0) {
            break;
        }
    }

    return ret;
}

/**
 * 检测并添加元素到页中
 * @return -3: 添加元素时，页数也增加，但是超过了限制
 *         -2: 检测已存在该元素
 *         -1: 检测不存在该元素，并且添加该元素成功
 *          0: 添加元素成功，但是添加的是第1个元素
 *         >0: 添加到元素到新页中，并返回新页数
 */
function _checkAddPagesItem(_isAdd, _maxPgs, _id, _keyPgs, _keyPg, _pa1, _pa2, _pa3, _pa4) {
    const pgsKey = _makeKey(_keyPgs, _pa1, _pa2, _pa3, _pa4);
    let pgsVal = _load(pgsKey);
    let pgs = {};
    pgs.pages = 1;
    pgs.value = 0;
    if (pgsVal !== false) {
        pgs = _toJsn(pgsVal);
    }
    let ret = _checkAddPageItem(_isAdd, _maxPgs, pgs.pages, _id, _keyPg, _pa1, _pa2, _pa3, _pa4);
    if (_isAdd && Utils.int64Compare(ret, -1) >= 0) {
        pgs.value = Utils.int64Add(pgs.value, 1);
        if (Utils.int64Compare(ret, 1) > 0) {
            pgs.pages = ret;
        }
        _store(pgsKey, _toStr(pgs));
        if (pgsVal === false) {
            return 0;
        }
    }
    return ret;
}

/**
 * 检测并减少页中的元素
 * @return -1: 检测不存在该元素
 *        >=0: 检测存在该元素，且删除该元素成功
 */
function _checkSubPagesItem(_isSub, _id, _keyPgs, _keyPg, _pa1, _pa2, _pa3, _pa4) {
    const pgsKey = _makeKey(_keyPgs, _pa1, _pa2, _pa3, _pa4);
    let pgsVal = _load(pgsKey);
    if (pgsVal === false) {
        return -1;
    }
    let pgs = _toJsn(pgsVal);
    const ret = _checkSubPageItem(_isSub, pgs.pages, _id, _keyPg, _pa1, _pa2, _pa3, _pa4);
    if (ret === 0 && _isSub) {
        pgs.value = Utils.int64Sub(pgs.value, 1);
        if (pgs.value === '0') {
            pgs.pages = Utils.int64Sub(pgs.pages, 1);
        }
        _store(pgsKey, _toStr(pgs));
        return pgs.value;
    }
    return ret;
}

function _getPagesItemsF(_keyPgs, _keyPg, _pgsPa1, _pgsPa2, _pgsPa3, _pgsPa4, _pgPa1, _pgPa2, _pgPa3, _pgPa4) {
    const pgsKey = _makeKey(_keyPgs, _pgsPa1, _pgsPa2, _pgsPa3, _pgsPa4);
    let pgsVal = _load(pgsKey);
    if (pgsVal === false) {
        return [];
    }
    const pgs = _toJsn(pgsVal);

    let i = 0;
    let items = [];
    for (i = 1; Utils.int64Compare(i, pgs.pages) <= 0; i = Utils.int64Add(i, 1)) {
        const val = _load(_makeKey(_keyPg, _pgPa1, _pgPa2, _pgPa3, _pgPa4, i));
        const item = _toJsn(val);
        items = items.concat(item);
    }
    return items;
}

function _getPagesItems(_keyPgs, _keyPg, _pa1, _pa2, _pa3, _pa4) {
    return _getPagesItemsF(_keyPgs, _keyPg, _pa1, _pa2, _pa3, _pa4, _pa1, _pa2, _pa3, _pa4);
}

/**
 * 给metadata的value执行加法
 * @param {string} _val [数值]
 * @param {string} _key [keys的某项]
 * @param {string} _pa1 [key的参数1]
 * @param {string} _pa2 [key的参数2]
 * @param {string} _pa3 [key的参数3]
 * @return {boolean} [true: 加成功，但是从无到有, false: 加成功，但是刚开始就有值]
 */
function _addVal(_type, _val, _key, _pa1, _pa2, _pa3, _pa4) {
    let ret = false;
    const key = _makeKey(_key, _pa1, _pa2, _pa3, _pa4);
    let val = _load(key);
    let ble = {};
    if (val === false) {
        if (_type !== 0) {
            ble.total = 0;
            ble.available = 0;
        } else {
            val = 0;
        }
        ret = true;
    } else if (_type !== 0) {
        ble = _toJsn(val);
    }
    switch(_type) {
        case 0: // val是数字
            val = Utils.int64Add(val, _val);
            _store(key, val);
            break;
        case 1: // 总额和可用余额都变化（转账）
            ble.available = Utils.int64Add(ble.available, _val);
            ble.total = Utils.int64Add(ble.total, _val);
            _store(key, _toStr(ble));
            break;
        case 2: // 总额不变化，可用余额变化（解锁）
            ble.available = Utils.int64Add(ble.available, _val);
            _store(key, _toStr(ble));
            break;
    }

    return ret;
}

/**
 * 给metadata的value执行减法
 * @param {boolean} _isSub [是否执行减法运算]
 * @param {string} _val [减去的数值]
 * @param {string} _key [keys的某项]
 * @param {string} _pa1 [key的参数1]
 * @param {string} _pa2 [key的参数2]
 * @param {string} _pa3 [key的参数3]
 * @return {boolean} [-1: 执行减法失败, 0: 执行减法成功，但是值为0, 1: 执行减法成功，且值大于　0]
 */
function _subVal(_type, _isSub, _val, _key, _pa1, _pa2, _pa3, _pa4) {
    const key = _makeKey(_key, _pa1, _pa2, _pa3, _pa4);
    let val = _load(key);
    let ble = {};
    if (val === false) {
        return -1;
    }
    if (_type !== 0) {
        ble = _toJsn(val);
    }
    let num = 0;
    switch(_type) {
        case 0: // val是数字
            val= Utils.int64Sub(val, _val);
            num = val;
            break;
        case 1: // 总额和可用余额都变化（转账）
            ble = _toJsn(val);
            ble.total = Utils.int64Sub(ble.total, _val);
            ble.available = Utils.int64Sub(ble.available, _val);
            num = ble.available;
            val = _toStr(ble);
            break;
        case 2: // 总额不变化，可用余额变化（锁定）
            ble = _toJsn(val);
            ble.available = Utils.int64Sub(ble.available, _val);
            num = ble.available;
            val = _toStr(ble);
            break;
    }

    if (Utils.int64Compare(num, 0) < 0) {
        return -1;
    }

    if (_isSub === true) {
        if (val !== '0') {
            _store(key, val);
        } else {
            _del(key);
            return 0;
        }
    }
    return 1;
}

function _approve(_spr, _skuId, _trnId, _val) {
    // Checking parameters.
    Utils.assert(_checkAddr(_spr), _throwErr(error.SPR_ERR));
    Utils.assert(_checkStr(_skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(Utils.stoI64Check(_val) && Utils.int64Compare(_val, 0) > 0, _throwErr(error.VAL_ERR));

    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, _skuId);
    const skuTkVal = _isExist(skuTkKey, error.SKU_NOT_EST);
    const skuTk = _toJsn(skuTkVal);

    // Checking whether the tranche exists.
    _trnId = _checkTranche(_trnId, skuTk.defaultTrancheId, false);

    // Checking the balance of this tranche is enough.
    const bleTrn = _loadVal(2, keys.bletrn, _skuId, _trnId, gMsgSender);
    Utils.assert(Utils.int64Compare(bleTrn.available, _val) >= 0, _throwErr(error.BLE_NOT_EGH));

    // Approving the tokens.
    const alwKey = _makeKey(keys.alw, gMsgSender, _skuId, _trnId, _spr);
    _store(alwKey, _val);

    return _trnId;
}

function _checkAddBalance(_to, _skuId, _trnId, _val) {
    // Adding the total balance.
    _addVal(1, _val, keys.ble, _skuId, _to);

    // Adding the tranche balance.
    const trnAddRet = _addVal(1, _val, keys.bletrn, _skuId, _trnId, _to);
    if (trnAddRet) {
        _checkAddPagesItem(true, 60, _trnId, keys.bletrnspgs, keys.bletrnspg, _skuId, _to);
    }
}

function _checkSubBalance(_sender, _frm, _skuId, _trnId, _val, _isSub) {
    // If the sender is not from, checking whether the allowance is enough.
    if (_sender !== _frm) {
        const alwKey = _makeKey(keys.alw, _frm, _skuId, _trnId, _sender);
        let alwVal = _isExist(alwKey, error.NO_ALW);
        Utils.assert(Utils.int64Compare(alwVal, _val) >= 0, _throwErr(error.ALW_NOT_EGH));
        alwVal = Utils.int64Sub(alwVal, _val);
        if (alwVal === '0') {
            _del(alwKey);
        } else {
            _store(alwKey, alwVal);
        }
    }

    // Reducing the tranche balance, and checking whether the tranche balance is enough.
    const trnSubRet = _subVal(1, _isSub, _val, keys.bletrn, _skuId, _trnId, _frm);
    Utils.assert(trnSubRet !== -1, _throwErr(error.BLE_NOT_EGH));

    // Reducing the total balance.
    if (_isSub === true) {
        if (trnSubRet === 0) {
            _checkSubPagesItem(true, _trnId, keys.bletrnspgs, keys.bletrnspg, _skuId, _frm);
        }
        _subVal(1, _isSub, _val, keys.ble, _skuId, _frm);
    }
}

function _setLockup(_id, _eT, _sT, _sender) {
    // Loading the lockup.
    const lockKey = _makeKey(keys.lock, _id);
    let lockVal = _load(lockKey);
    let lock = {};
    if (lockVal !== false) {
        lock = _toJsn(lockVal);
    }

    // Setting the lockup.
    if (_sT !== undefined) {
        lock.startTime = _sT;
    }
    lock.endTime = _eT;
    if (_sender !== undefined) {
        lock.creator = _sender;
    }

    Chain.store(lockKey, _toStr(lock));
}

function _createLockup(_id, _sT, _eT, _sender) {
    // Checking parameters.
    Utils.assert(_checkStr(_id, 1, 64), _throwErr(error.LOCK_ID_ERR));
    Utils.assert(Utils.stoI64Check(`${_sT}`) && Utils.int64Compare(_sT, 0) > 0, _throwErr(error.SRT_TM_ERR));
    // The endTime should be bigger than block time and start time.
    Utils.assert(Utils.stoI64Check(_eT) && Utils.int64Compare(_eT, gBlockTime) > 0 && Utils.int64Compare(_sT, _eT) < 0, _throwErr(error.END_TM_ERR));

    // Checking whether the lockup does not exist.
    _isNotExist(_makeKey(keys.lock, _id), error.LOCK_EST);

    // Creating the lockup.
    _setLockup(_id, _eT, _sT, _sender);
}

function _lockup(_addr, _skuId, _trnId, _lockId, _val, _isDftTrn) {
    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, _skuId);
    const skuTkVal = _isExist(skuTkKey, error.SKU_NOT_EST);
    const skuTk = _toJsn(skuTkVal);

    // Checking whether the tranche exists.
    _trnId = _checkTranche(_trnId, skuTk.defaultTrancheId, _isDftTrn);
    _isExist(_makeKey(keys.trn, _trnId), error.TRN_NOT_EST);

    // Checking whether the lockup exists.
    const lockVal = _isExist(_makeKey(keys.lock, _lockId), error.LOCK_NOT_EST);

    // Checking whether the lockup has not expired.
    const lock = JSON.parse(lockVal);
    Utils.assert(Utils.int64Compare(lock.endTime, Chain.block.timestamp) >= 0 || lock.endTime === '-1', _throwErr(error.LOCK_EPD));

    // Reducing the tranche balance, and checking whether the tranche balance is enough.
    const trnSubRet = _subVal(2, true, _val, keys.bletrn, _skuId, _trnId, _addr);
    Utils.assert(trnSubRet !== -1, _throwErr(error.BLE_NOT_EGH));

    // Reducing the total balance.
    _subVal(2, true, _val, keys.ble, _skuId, _addr);

    // Adding the lockup balance.
    const lckAddRet = _addVal(0, _val, keys.bletrnlock, _skuId, _trnId, _lockId, _addr);
    if (lckAddRet) {
        _checkAddPagesItem(true, 60, _lockId, keys.bletrnlockspgs, keys.bletrnlockspg, _skuId, _trnId, _addr);
    }
}

function _unlock(_addr, _skuId, _trnId, _lockId, _val) {
    // Checking whether the lockup exists.
    _isExist(_makeKey(keys.lock, _lockId), error.LOCK_NOT_EST);

    // Reducing the lockup balance, and checking whether the lockup balance is enough.
    const lckSubRet = _subVal(0, true, _val, keys.bletrnlock, _skuId, _trnId, _lockId, _addr);
    Utils.assert(lckSubRet !== -1, _throwErr(error.BLE_NOT_EGH));

    // If the lockup balance is 0, deleting from the balance.
    if (lckSubRet === 0) {
        _checkSubPagesItem(true, _lockId, keys.bletrnlockspgs, keys.bletrnlockspg, _skuId, _trnId, _addr);
    }

    // Adding the total balance.
    _addVal(2, _val, keys.ble, _skuId, _addr);

    // Adding the tranche balance.
    _addVal(2, _val, keys.bletrn, _skuId, _trnId, _addr);
}

function _transferFrom(_frm, _skuId, _trnId, _to, _val) {
    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, _skuId);
    const skuTkVal = _isExist(skuTkKey, error.SKU_NOT_EST);
    const skuTk = _toJsn(skuTkVal);

    // Checking whether the tranche exists.
    _trnId = _checkTranche(_trnId, skuTk.defaultTrancheId, false);

    // If from is equal to to, then return
    if (_frm === _to) {
        return _trnId;
    }

    // Reducing the from balance, and checking whether the from balance is enough.
    _checkSubBalance(gMsgSender, _frm, _skuId, _trnId, _val, true);

    // Add the to balance
    _checkAddBalance(_to, _skuId, _trnId, _val);

    return _trnId;
}

function _transfer(_frm, _skuId, _trnId, _to, _val) {
    // Checking parameters.
    Utils.assert(_checkAddr(_to), _throwErr(error.TO_ERR));
    Utils.assert(_checkStr(_skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(Utils.stoI64Check(_val) && Utils.int64Compare(_val, 0) > 0, _throwErr(error.VAL_ERR));

    // Transferring the tokens.
    return _transferFrom(_frm, _skuId, _trnId, _to, _val);
}

function _destroy(_addr, _skuId, _trnId, _val) {
    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, _skuId);
    const skuTkVal = _isExist(skuTkKey, error.SKU_NOT_EST);
    const skuTk = _toJsn(skuTkVal);

    // Checking whether the tranche exists.
    _trnId = _checkTranche(_trnId, skuTk.defaultTrancheId, true);

    // Reducing the balance, and checking whether the from balance is enough.
    _checkSubBalance(_addr, _addr, _skuId, _trnId, _val, true);

    // Reducing the totalSupply of sku.
    skuTk.totalSupply = Utils.int64Sub(skuTk.totalSupply, _val);
    _store(skuTkKey, _toStr(skuTk));
}

/**
 * 查询商户信息
 * @return {string}
 */
function sellerInfo() {
    return _load(keys.sel);
}

/**
 * 设置文档
 * @param {string} id [文档id]
 * @param {string} name [文档名称]
 * @param {string} url [文档链接]
 * @param {string} hashType [哈希类型]
 * @param {string} hash [文档内容哈希内容]
 * @throws {error}
 */
function setDocument(id, name, url, hashType, hash) {
    // Checking parameters.
    Utils.assert(_checkStr(id, 1, 32), _throwErr(error.DOC_ID_ERR));
    Utils.assert(_checkStr(name, 1, 1024), _throwErr(error.NAME_ERR));
    Utils.assert(_checkStr(url, 1, 10240), _throwErr(error.DOC_URL_ERR));
    Utils.assert(_checkStr(hashType, 1, 64), _throwErr(error.HASH_TYPE_ERR));
    Utils.assert(_checkStr(hash, 1, 2048), _throwErr(error.HASH_ERR));

    // Checking whether the provider is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether all documents are more.
    Utils.assert(_checkAddPagesItem(true, 88, id, keys.docspgs, keys.docspg) !== -3, _throwErr(error.DOCS_MORE_ERR));


    // Storing document.
    let docInfo = {};
    docInfo.name = name;
    docInfo.url = url;
    docInfo.hashType = hashType;
    docInfo.hash = hash;
    docInfo.provider = gTxSender;
    docInfo.date = gBlockTime;
    _store(_makeKey(keys.doc, id), _toStr(docInfo));

    // Committing event.
    Chain.tlog('setDocument', _makeTlogSender(), id, name);
}

/**
 * 获取所有文档
 * @throws {error}
 * @return {string}
 */
function allDocuments() {
    // Getting the documents.
    const docs = _getPagesItems(keys.docspgs, keys.docspg);

    return _toStr(docs);
}

/**
 * 获取文档
 * @param {string} docId [文档id]
 * @return {string} [文档信息]
 * @throws {error}
 */
function documentInfo(docId) {
    // Checking parameters.
    Utils.assert(_checkStr(docId, 1, 32), _throwErr(error.DOC_ID_ERR));

    // Checking whether the document exists.
    const docVal = _isExist(_makeKey(keys.doc, docId), error.DOC_NOT_EST);

    return docVal;
}

/**
 * 创建SPU
 * @param {string} id [SPU编号]
 * @param {string} name [SPU名称]
 * @param {string} type [SPU类别]
 * @param {string} des [SPU描述信息]
 * @param {JSON} attrs [SPU属性]
 * @throws {error}
 */
function createSpu(id, name, type, des, attrs) {
    // Checking parameters.
    Utils.assert(_checkStr(id, 1, 32), _throwErr(error.SPU_ID_ERR));
    Utils.assert(_checkStr(name, 1, 1024), _throwErr(error.NAME_ERR));
    Utils.assert(_checkStr(type, 1, 64), _throwErr(error.SPU_TYPE_ERR));
    Utils.assert(_checkStr(des, 0, 64000), _throwErr(error.DES_ERR));

    // Checking whether the sender is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether the spu does not exist.
    const spuKey = _makeKey(keys.spu, id);
    _isNotExist(spuKey, error.SPU_EST);

    // Storing the spu.
    let spu = {};
    spu.name = name;
    spu.type = type;
    spu.description = des;
    spu.attributes = attrs;
    _store(spuKey, _toStr(spu));

    // Committing event.
    Chain.tlog('createSpu', _makeTlogSender(), id, name, type);
}

/**
 * 修改SPU信息
 * @param {string} spuId [SPU编号]
 * @param {string} name [SPU名称]
 * @param {string} type [SPU类别]
 * @param {string} des [SPU描述信息]
 * @param {JSON} attrs [SPU属性]
 * @throws {error}
 */
function setSpu(spuId, name, type, des, attrs) {
    // Checking parameters.
    Utils.assert(_checkStr(spuId, 1, 32), _throwErr(error.SPU_ID_ERR));
    Utils.assert(_checkStr(name, 1, 1024), _throwErr(error.NAME_ERR));
    Utils.assert(_checkStr(type, 1, 64), _throwErr(error.SPU_TYPE_ERR));
    Utils.assert(_checkStr(des, 0, 64000), _throwErr(error.DES_ERR));

    // Checking whether the issuer is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether the spu exists.
    const spuKey = _makeKey(keys.spu, spuId);
    const spuVal = _isExist(spuKey, error.SPU_NOT_EST);
    let spu = _toJsn(spuVal);

    // Modifying the spu.
    spu.name = name;
    spu.type = type;
    spu.description = des;
    spu.attributes = attrs;
    _store(spuKey, _toStr(spu));

    // Committing event.
    Chain.tlog('setSpu', _makeTlogSender(), spuId, name, type);
}

/**
 * 获取 spu 信息
 * @param {string} spuId [SPU编号]
 * @return {string} [spu信息]
 * @throws {error}
 */
function spuInfo(spuId) {
    // Checking parameters.
    Utils.assert(_checkStr(spuId, 1, 32), _throwErr(error.SPU_ID_ERR));

    // Checking whether the spu exists.
    const spuVal = _isExist(_makeKey(keys.spu, spuId), error.SPU_NOT_EST);

    return spuVal;
}

/**
 *
 * @param {string} id [Tranche的id]
 * @param {string} des [Tranche的描述]
 * @param {JSONObject} lms [Tranche的限制]
 * @throws {error}
 */
function createTranche(id, des, lms) {
    // Checking parameters.
    Utils.assert(_checkStr(id, 1, 32), _throwErr(error.TRN_ID_ERR));
    Utils.assert(_checkStr(des, 0, 64000), _throwErr(error.DES_ERR));

    // Checking whether the creator is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking the limits
    if (lms !== undefined) {
        Utils.assert(_checkJSNObj(lms), _throwErr(error.LMS_ERR));
        if (lms.validityPeriod !== undefined) {
            Utils.assert(_checkJSNObj(lms.validityPeriod), _throwErr(error.VP_ERR));
            Utils.assert(Utils.stoI64Check(lms.validityPeriod.startTime) && Utils.int64Compare(lms.validityPeriod.startTime, 0) > 0, _throwErr(error.SRT_TM_ERR));
            Utils.assert(Utils.stoI64Check(lms.validityPeriod.endTime) && Utils.int64Compare(lms.validityPeriod.endTime, lms.validityPeriod.startTime) > 0 && Utils.int64Compare(lms.validityPeriod.endTime, gBlockTime) > 0, _throwErr(error.END_TM_ERR));
        }
    }

    // Checking whether the tranche does not exist.
    const trnKey = _makeKey(keys.trn, id);
    _isNotExist(trnKey, error.TRN_EST);

    // Creating the tranche.
    let trm = {};
    trm.description = des;
    trm.limits = lms;
    _store(trnKey, _toStr(trm));

    // Committing event.
    Chain.tlog('createTranche', _makeTlogSender(), id);
}

/**
 * 获取 Tranche 信息
 * @param {string} trnId [Tranche的id]
 * @return {string} [Tranche信息]
 * @throws {error}
 */
function trancheInfo(trnId) {
    // Checking parameters.
    trnId = _checkTranche(trnId, 0, 0, true);

    // Checking whether the tranche exists.
    const trnVal = _isExist(_makeKey(keys.trn, trnId), error.TRN_NOT_EST);

    return trnVal;
}

/**
 * 发行到指定 Tranche
 * @param {string} skuId [SKU的id]
 * @param {string} trnId [Trance的id]
 * @param {boolean} isDftTrn [是否是默认Tranche]
 * @param {string} spuId [SPU的id]
 * @param {string} name [SKU的名称]
 * @param {string} symbol [Token的符号]
 * @param {string} faceVal [面值]
 * @param {string} supply [Token的发行量]
 * @param {int} decimals [Token的精度]
 * @param {string} mainIcn [SKU的主力]
 * @param {JSONArray} viceIcns [SKU的副图列表]
 * @param {JSONArray} labels [SKU标签列表]
 * @param {string} des [SKU的描述]
 * @param {string} repnAddr [兑付回购区块链账户地址]
 * @param {string} repnPrd [兑付期限]
 * @param {string} acpId [承兑方的编号]
 * @param {JSONArray} abs [SKU的摘要属性]
 * @param {JSONObject} attrs [SKU的属性]
 */
function issue(skuId, trnId, isDftTrn, spuId, name, symbol, faceVal, supply, decimals, mainIcn, viceIcns, labels, des, repnAddr, repnPrd, acpId, abs, attrs) {
    // Checking parameters.
    trnId = _checkTranche(trnId);
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(isDftTrn === undefined || typeof isDftTrn === 'boolean',_throwErr(error.IS_DFT_TRN_ERR));
    Utils.assert(_checkStr(spuId, 0, 32), _throwErr(error.SPU_ID_ERR));
    Utils.assert(_checkStr(name, 1, 1024), _throwErr(error.NAME_ERR));
    Utils.assert(_checkStr(symbol, 1, 16), _throwErr(error.TK_BML_ERR));
    Utils.assert(_checkStr(faceVal, 0, 32), _throwErr(error.FC_ERR));
    Utils.assert(Utils.stoI64Check(supply) && Utils.int64Compare(supply, 0) > 0, _throwErr(error.TK_SPY_ERR));
    Utils.assert(_checkInt(decimals, 0, 8), _throwErr(error.TK_DCS_ERR));
    Utils.assert(_checkStr(mainIcn, 0, 10240), _throwErr(error.MAIN_ICN_ERR));
    Utils.assert(_checkJSNArr(viceIcns, 0, 5), _throwErr(error.VICE_ICNS_ERR));
    Utils.assert(_checkStr(des, 0, 64000), _throwErr(error.DES_ERR));
    Utils.assert(_checkJSNArr(labels, 0, 20), _throwErr(error.LBL_ERR));
    Utils.assert(_checkAddr(repnAddr), _throwErr(error.REPN_ADDR_ERR));
    Utils.assert(Utils.stoI64Check(repnPrd) && Utils.int64Compare(repnPrd, 0) > 0, _throwErr(error.RPN_PRD_ERR));
    Utils.assert(_checkJSNArr(abs, 0, 20), _throwErr(error.ABT_ERR));
    Utils.assert(_checkStr(acpId, 1, 32), _throwErr(error.ACP_ID_ERR));

    // Checking whether the issueByTrancher is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether each length of viceIcons is between 1 and 10240.
    _checkArrayItem(viceIcns, 1, 10240, 'vice', error.VICE_ICNS_ERR);

    // Checking whether each length of labels is between 1 and 1024.
    _checkArrayItem(labels, 1, 1024, 'label', error.LBL_ERR);

    // Checking whether each length of abstracts is between 1 and 32.
    _checkArrayItem(abs, 1, 32, 'attribute', error.ABT_ERR);

    // Checking whether the sku does not exist.
    _isNotExist(_makeKey(keys.sku, skuId), error.SKU_EST);

    // Checking whether the spu exists.
    if (spuId !== undefined) {
        _isExist(_makeKey(keys.spu, spuId), error.SPU_NOT_EST);
    }

    // Checking whether the acceptance exists.
    _isExist(_makeKey(keys.acp, acpId), error.ACP_NOT_EST);

    // Issuing the sku tokens.
    let sku = {};
    sku.spuId = spuId;
    if (isDftTrn === true) {
        sku.defaultTrancheId = trnId;
    }
    sku.name = name;
    sku.symbol = symbol;
    sku.faceValue = faceVal;
    sku.totalSupply = supply;
    sku.decimals = decimals;
    sku.description = des;
    sku.mainIcon = mainIcn;
    sku.viceIcons = viceIcns;
    sku.labels = labels;
    sku.redemptionAddress = repnAddr;
    sku.redemptionPeriod = repnPrd;
    sku.acceptanceId = acpId;
    sku.attributes = attrs;
    sku.abstracts = abs;
    sku.authorizers = [];
    sku.time = Chain.block.timestamp;
    _store(_makeKey(keys.sku, skuId), _toStr(sku));

    // Setting the total supply of the specified tranche of the sku.
    _store(_makeKey(keys.skutrnspy, skuId, trnId), `${supply}`);

    // Adding the tranche to the sku.
    _checkAddPagesItem(true, 88, trnId, keys.skutrnspgs, keys.skutrnspg, skuId);

    // Adding the sku to the tranche.
    _checkAddPagesItem(true, 88, skuId, keys.trnskuspgs, keys.trnskuspg, trnId); // 1

    // Adding the sku to the spu.
    if (spuId !== undefined) {
        _checkAddPagesItem(true, 88, skuId, keys.spuskuspgs, keys.spuskuspg, spuId);
    }

    // Setting issuer balance.
    _addVal(1, supply, keys.ble, skuId, gTxSender); // 1

    // Setting issuer tranche balance.
    _addVal(1, supply, keys.bletrn, skuId, trnId, gTxSender); // 1

    // Adding the tranche to the balance.
    _checkAddPagesItem(true, 80, trnId, keys.bletrnspgs, keys.bletrnspg, skuId, gTxSender); // 1

    // Committing event.
    Chain.tlog('issue', _makeTlogSender(), skuId, `${trnId}_${spuId}`, symbol, supply);
}

/**
 * 设置筛选 SPU 中的 SKU 的信息
 * @param {string} spuId [SPU的编号]
 * @param {JSONObject} choice [SKU的筛选信息]
 */
function setSkusChoice(spuId, choice) {
    // Checking parameters.
    Utils.assert(_checkStr(spuId, 1, 32), _throwErr(error.SPU_ID_ERR));
    Utils.assert(_checkJSNObj(choice) && _checkStr(_toStr(choice), 1, 40960), _throwErr(error.COC_ERR));

    // Checking whether the issuer is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether the spu exists.
    const spuKey = _makeKey(keys.spu, spuId);
    const spuVal = _isExist(spuKey, error.SPU_NOT_EST);
    let spu = JSON.parse(spuVal);

    // Setting the choice
    spu.choice = choice;
    _store(spuKey, _toStr(spu));

    // Committing event.
    Chain.tlog('setSkusChoice', _makeTlogSender(), spuId);
}

/**
 * 修改SKU
 * @param {string} skuId [SKU的编号]
 * @param {string} name [SKU名称]
 * @param {string} symbol [Token的符号]
 * @param {string} mainIcn [SKU的主图]
 * @param {JSONArray} viceIcns [SKU的副图列表]
 * @param {JSONArray} labels [SKU的标签列表]
 * @param {string} des [SKU的描述]
 * @throws {error}
 */
function setSku(skuId, name, symbol, mainIcn, viceIcns, labels, des, repnAddr, acpId, abs, attrs) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(_checkStr(name, 1, 1024), _throwErr(error.NAME_ERR));
    Utils.assert(_checkStr(symbol, 1, 16), _throwErr(error.TK_BML_ERR));
    Utils.assert(_checkStr(mainIcn, 0, 10240), _throwErr(error.MAIN_ICN_ERR));
    Utils.assert(_checkJSNArr(viceIcns, 0, 5), _throwErr(error.VICE_ICNS_ERR));
    Utils.assert(_checkStr(des, 0, 64000), _throwErr(error.DES_ERR));
    Utils.assert(_checkJSNArr(labels, 0, 20), _throwErr(error.LBL_ERR));
    Utils.assert(_checkAddr(repnAddr), _throwErr(error.REPN_ADDR_ERR));
    Utils.assert(_checkStr(acpId, 1, 32), _throwErr(error.ACP_ID_ERR));
    Utils.assert(_checkJSNArr(abs, 0, 20), _throwErr(error.ABT_ERR));

    // Checking whether the issuer is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether each length of viceIcons is between 1 and 10240.
    _checkArrayItem(viceIcns, 1, 10240, 'vice', error.VICE_ICNS_ERR);

    // Checking whether each length of labels is between 1 and 1024.
    _checkArrayItem(labels, 1, 1024, 'label', error.LBL_ERR);

    // Checking whether each length of abstracts is between 1 and 32.
    _checkArrayItem(abs, 1, 32, 'attribute', error.ABT_ERR);

    // Checking whether the acceptance exists.
    _isExist(_makeKey(keys.acp, acpId), error.ACP_NOT_EST);

    // Checking whether the sku already exists.
    const skuTkKey = _makeKey(keys.sku, skuId);
    const skuTkVal = _isExist(skuTkKey, error.SKU_NOT_EST);
    let skuTk = _toJsn(skuTkVal);

    // Modifying the sku.
    skuTk.name = name;
    skuTk.symbol = symbol;
    skuTk.mainIcon = mainIcn;
    skuTk.viceIcons = viceIcns;
    skuTk.labels = labels;
    skuTk.description = des;
    skuTk.redemptionAddress = repnAddr;
    skuTk.attributes = attrs;
    skuTk.acceptanceId = acpId;
    skuTk.abstracts = abs;
    _store(skuTkKey, _toStr(skuTk));

    // Committing event.
    Chain.tlog('modifySku', _makeTlogSender(), skuId);
}

/**
 * 增发到指定 Tranche
 * @param {string} skuId [SKU的id]
 * @param {string} trnId [Tranche的编号]
 * @param {string} supply [增发数量]
 * @throws {error}
 */
function additionalIssuance(skuId, trnId, supply) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(Utils.stoI64Check(supply) && Utils.int64Compare(supply, 0) > 0, _throwErr(error.TK_SPY_ERR));

    // Checking whether the issuer is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether the sku already exists.
    const skuTkKey = _makeKey(keys.sku, skuId);
    const skuTkVal = _isExist(skuTkKey, error.SKU_NOT_EST);
    let skuTk = _toJsn(skuTkVal);

    // Adding the totalSupply of sku tokens.
    skuTk.totalSupply = Utils.int64Add(skuTk.totalSupply, supply);
    _store(skuTkKey, _toStr(skuTk));

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId, skuTk.defaultTrancheId, true);

    // Adding the total supply of sku tranche.
    _addVal(0, supply, keys.skutrnspy, skuId, trnId);

    // Adding the tranche to the sku.
    _checkAddPagesItem(true, 88, trnId, keys.skutrnspgs, keys.skutrnspg, skuId);

    // Adding to issuer total balance.
    _addVal(1, supply, keys.ble, skuId, gTxSender);

    // Adding to issuer tranche balance.
    _addVal(1, supply, keys.bletrn, skuId, trnId, gTxSender);

    // Adding the tranche to the balance.
    _checkAddPagesItem(true, 60, trnId, keys.bletrnspgs, keys.bletrnspg, skuId, gTxSender);

    // Committing event.
    Chain.tlog('additionalIssuance', _makeTlogSender(), skuId, supply);
}

/**
 * 将默认 Tranche 的 SKU Token 分配到指定 Tranche.
 * @param {string} skuId [SKU编号]
 * @param {string} toTrnId [目标Tranche编号]
 * @param {string} val [分配数量]
 * @param {string} dftTrnInfo [默认Tranche的token的描述]
 * @param {string} toTrnInfo [目标Tranche的token的描述]
 * @throws {error}
 */
function assignToTranche(skuId, toTrnId, val) {
    // Checking parameters.
    toTrnId = _checkTranche(toTrnId);
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(Utils.stoI64Check(val) && Utils.int64Compare(val, 0) > 0, _throwErr(error.VAL_ERR));

    // Checking whether the sender is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, skuId);
    const skuTkVal = _isExist(skuTkKey, error.SKU_NOT_EST);
    let skuTk = _toJsn(skuTkVal);

    // Checking whether the sku has default tranche.
    Utils.assert(skuTk.defaultTrancheId !== undefined, _throwErr(error.SKU_NO_DFT_TRN));

    // Checking whether the target tranche id exists and it is not default tranche.
    const dftTrnId = skuTk.defaultTrancheId;
    Utils.assert(dftTrnId !== toTrnId, _throwErr(error.TO_TRN_DEFAULT_ERR));

    // Checking whether the from balance is enough.
    _checkSubBalance(gTxSender, gTxSender, skuId, skuTk.defaultTrancheId, val, true);

    // Adding the sender target tranche balance.
    _checkAddBalance(gTxSender, skuId, toTrnId, val);

    // Committing event.
    Chain.tlog('assignToTranche', _makeTlogSender(), skuId, toTrnId, val);
}

/**
 * 设置授权者
 * @param {String} skuId [SKU的编号]
 * @param {JSONArray} autrs [授权者区块链账户地址列表]
 */
function setAuthorizers(skuId, autrs) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(_checkJSNArr(autrs, 1, 5), _throwErr(error.ATOS_ERR));

    // Checking whether the sender is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether the sku already exists.
    const skuTkKey = _makeKey(keys.sku, skuId);
    const skuTkVal = _isExist(skuTkKey, error.SKU_NOT_EST);
    let skuTk = _toJsn(skuTkVal);

    // Checking the authorizers
    let i = 0;
    const len = autrs.length;
    for(i = 0; i < len; i += 1) {
        const autr = autrs[i];
        Utils.assert(_checkAddr(autr), _throwErrMsg(error.ATOS_ERR, `The autorizer of index ${i} is invalid.`));
    }

    // Setting the authorizers
    skuTk.authorizers = autrs;
    _store(skuTkKey, _toStr(skuTk));

    // Committing event.
    Chain.tlog('setAuthorizers', _makeTlogSender(), skuId, _toStr(autrs));
}

/**
 * 授权给已发行的 SKU Token
 * @param {String} skuId [Sku的编号]
 * @param {String} trnId [Tranche的编号]
 * @throws {error}
 */
function authorizeSku(skuId, trnId) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));

    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, skuId);
    const skuTkValue = _isExist(skuTkKey, error.SKU_NOT_EST);
    let skuTk = _toJsn(skuTkValue);

    // Checking whether the authorizer is in authorizers of skuToken.
    const idx = skuTk.authorizers.indexOf(gTxSender);
    Utils.assert(idx !== -1, _throwErr(error.ATOR_NOT_EST));

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId, skuTk.defaultTrancheId, true);

    // Checking whether the tranche is in sku.
    const trnInSku = _checkAddPagesItem(false, 88, trnId, keys.skutrnspgs, keys.skutrnspg, skuId);
    Utils.assert(trnInSku !== -1, _throwErr(error.TRN_NOT_IN_SKU));

    // If the authorizer already exists.
    const autrKey =  _makeKey(keys.skuauth, skuId, trnId, gTxSender);
    const autrVal = _load(_makeKey(keys.skutrnspy, skuId, trnId));
    _store(autrKey, autrVal);

    // Committing event.
    Chain.tlog('authorizeSku', _makeTlogSender(), skuId, trnId);
}

/**
 * 查询已授权的SKU
 * @param {string} skuId [SKU编号]
 * @param {string} trnId [Tranche编号]
 * @param {string} autr [授权者]
 */
function authorizedSku(skuId, trnId, autr) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));

    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, skuId);
    const skuTkValue = _isExist(skuTkKey, error.SKU_NOT_EST);
    let skuTk = _toJsn(skuTkValue);

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId, skuTk.defaultTrancheId, true);

    // Checking whether the authorizer is in authorizers of skuToken.
    const idx = skuTk.authorizers.indexOf(autr);
    Utils.assert(idx !== -1, _throwErr(error.ATOR_NOT_EST));

    return _loadVal(0, keys.skuauth, skuId, trnId, autr);
}

/**
 * 获取 SPU 的所有 SKU
 * @param {string} spuId [SPU编号]
 * @throws {error}
 * @return {string}
 */
function skusOfSpu(spuId) {
    // Checking parameters.
    Utils.assert(_checkStr(spuId, 1, 32), _throwErr(error.SPU_ID_ERR));

    // Checking whether the spu exists.
    _isExist(_makeKey(keys.spu, spuId), error.SPU_NOT_EST);

    // Getting the skus.
    const skus = _getPagesItems(keys.spuskuspgs, keys.spuskuspg, spuId);

    return _toStr(skus);
}

/**
 * 获取  Tranche下的所有 SKU
 * @param {string} trnId [Tranche的编号]
 * @throws {error}
 * @return {string}
 */
function skusOfTranche(trnId) {
    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId);

    // Getting the skus.
    const skus = _getPagesItems(keys.trnskuspgs, keys.trnskuspg, trnId);

    return _toStr(skus);
}

/**
 * 返回指定 SKU 的所有 Tranche
 * @param {string} skuId [SKU编号]
 * @return {string}
 * @throws {error}
 */
function tranchesOfSku(skuId) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));

    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, skuId);
    _isExist(skuTkKey, error.SKU_NOT_EST);

    // Getting the tranches of sku.
    const trns = _getPagesItems(keys.skutrnspgs, keys.skutrnspg, skuId);

    return _toStr(trns);
}

/**
 * 查询某账户的所有 Tranche
 * @param {string} skuId [SKU编号]
 * @param {string} addr [区块链账户地址]
 * @return {string}
 * @throws {error}
 */
function tranchesOf(skuId, addr) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(_checkAddr(addr), _throwErr(error.ADDR_ERR));

    // Checking whether the sku exists.
    const skuTkKey = _makeKey(keys.sku, skuId);
    _isExist(skuTkKey, error.SKU_NOT_EST);

    // Getting the tranches.
    const trns = _getPagesItems(keys.bletrnspgs, keys.bletrnspg, skuId, addr);

    return _toStr(trns);
}

/**
 * 查询 SKU Token 信息
 * @param {string} skuId [SKU的id]
 * @throws {error}
 */
function tokenInfo(skuId) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));

    // Checking whether the sku exists.
    const skuTkVal = _isExist(_makeKey(keys.sku, skuId), error.SKU_NOT_EST);

    return skuTkVal;
}

/**
 * 查询指定 Tranche 的发行总量
 * @param {string} skuId [SKU的id]
 * @param {string} trnId [Tranche的id]
 */
function totalSupplyByTranche(skuId, trnId) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));

    // Checking whether the sku exists.
    _isExist(_makeKey(keys.sku, skuId), error.SKU_NOT_EST);

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId);

    return _loadVal(0, keys.skutrnspy, skuId, trnId);
}

/**
 * 查询账户 SKU Token 余额
 * @param {string} skuId [SKU的id]
 * @param {string} address [区块链账户地址]
 * @throws {error}
 */
function balanceOf(address, skuId) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(_checkAddr(address), _throwErr(error.ADDR_ERR));

    // Checking whether the sku exists.
    _isExist(_makeKey(keys.sku, skuId), error.SKU_NOT_EST);

    return _toStr(_loadVal(2, keys.ble, skuId, address));
}

/**
 * 查询账户指定 Tranche 的 SKU Token 余额
 * @param {string} skuId [SKU的id]
 * @param {string} trnId [Tranche的id]
 * @param {string} addr [区块链账户地址]
 * @throws {error}
 */
function balanceOfByTranche(addr, skuId, trnId) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(_checkAddr(addr), _throwErr(error.ADDR_ERR));

    // Checking whether the sku exists.
    _isExist(_makeKey(keys.sku, skuId), error.SKU_NOT_EST);

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId);

    let bleTrn = _loadVal(2, keys.bletrn, skuId, trnId, addr);
    bleTrn.lockups = _getPagesItems(keys.bletrnlockspgs, keys.bletrnlockspg, skuId, trnId, addr);

    return _toStr(bleTrn);
}

/**
 * 查询账户指定 Lockup 的 SKU Token 余额
 * @param {string} addr [区块链账户地址]
 * @param {string} skuId [SKU编号]
 * @param {string} trnId [Tranche编号]
 * @param {string} lockId [Lockup编号]
 */
function balanceOfByLockup(addr, skuId, trnId, lockId) {
    // Checking parameters.
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(_checkAddr(addr), _throwErr(error.ADDR_ERR));
    Utils.assert(_checkStr(lockId, 1, 64), _throwErr(error.LOCK_ID_ERR));

    // Checking whether the sku exists.
    _isExist(_makeKey(keys.sku, skuId), error.SKU_NOT_EST);

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId);

    // Checking whether the lockup exists.
    _isExist(_makeKey(keys.lock, lockId), error.LOCK_NOT_EST);

    // Checking whether the address has the lockup balance.
    let bleTrnLockVal = _load(_makeKey(keys.bletrnlock, skuId, trnId, lockId, addr));
    if (bleTrnLockVal === false) {
        bleTrnLockVal = '0';
    }
    return bleTrnLockVal;
}

/**
 * 销毁指定 Tranche 的 SKU Token
 * @param {string} address [销毁的账户地址]
 * @param {string} skuId [SKU的id]
 * @param {String} trnId [Tranche的id]
 * @param {String} val [销毁的数量]
 * @throws {error}
 */
function destroy(address, skuId, trnId, val) {
    // Checking parameters.
    Utils.assert(_checkAddr(address), _throwErr(error.ADDR_ERR));
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(Utils.stoI64Check(val) && Utils.int64Compare(val, 0) > 0, _throwErr(error.VAL_ERR));

    // Checking whether the address is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Checking whether the address is seller.
    Utils.assert(address === gTxSender, _throwErr(error.SEL_RDM_OTR));

    // Checking whether the sku exists.
    _isExist(_makeKey(keys.sku, skuId), error.SKU_NOT_EST);

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId);

    // Redeeming the tokens
    _destroy(address, skuId, trnId, val);

    // Committing event.
    Chain.tlog('destroy', _makeTlogSender(), address, skuId, trnId, val);
}

/**
 * 授权转移
 * @param {string} spr [被授权者的区块链账户地址]
 * @param {string} skuId [SKU的id]
 * @param {string} trnId [Tranche的id]
 * @param {string} val [授权的SKU数量]
 * @throws {error}
 */
function approve(spr, skuId, trnId, val) {
    // Approving the tokens.
    trnId = _approve(spr, skuId, trnId, val);

    // Committing event.
    Chain.tlog('approve', _makeTlogSender(), spr, skuId, trnId, val);
}

/**
 * 授权转移 SKU Tokens 数量
 * @param {string} owr [授权者的区块链账户地址]
 * @param {string} skuId [SKU的id]
 * @param {string} spr [被授权者的区块链账户地址]
 * @throws {error}
 */
function allowance(owr, skuId, trnId, spr) {
    // Checking parameters.
    Utils.assert(_checkAddr(owr), _throwErr(error.ONR_ERR));
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(_checkAddr(spr), _throwErr(error.SPR_ERR));

    // Checking whether the sku exists.
    _isExist(_makeKey(keys.sku, skuId), error.SKU_NOT_EST);

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId);

    // Checking whether the allowance exists.
    return _loadVal(0, keys.alw, owr, skuId, trnId, spr);
}

/**
 * 将 SKU Token 从指定地址转移
 * @param {string} frm [发Token区块链账户地址]
 * @param {string} skuId [SKU的id]
 * @param {string} trnId [Tranche的id]
 * @param {string} to [收Token的区块链账户地址]
 * @param {string} val [Token数量]
 * @throws {error}
 * */
function transferFrom(frm, skuId, trnId, to, val) {
    // Checking parameters.
    Utils.assert(_checkAddr(frm), _throwErr(error.FRM_ERR));

    // Transferring the tokens.
    trnId = _transfer(frm, skuId, trnId, to, val);

    // Committing event.
    Chain.tlog('transferFrom', _makeTlogSender(), frm, `${skuId}_${trnId}`, to, val);
}

/**
 * 将 SKU Token 转移
 * @param {string} skuId [SKU的id]
 * @param {string} trnId [Tranche的id]
 * @param {string} to [收Token的区块链账户地址]
 * @param {string} val [Token数量]
 * @throws {error}
 */
function transfer(skuId, trnId, to, val) {
    // Transferring the tokens.
    trnId = _transfer(gMsgSender, skuId, trnId, to, val);

    // Committing event.
    Chain.tlog('transfer', _makeTlogSender(), `${skuId}_${trnId}`, to, val);
}

/**
 * 商家设置承兑方信息
 * @param {string} id [承兑方的id]
 * @param {string} pub [承兑方公钥]
 * @param {strin} flNm [承兑方名称全称]
 * @param {string} stNm [承兑方名称简称]
 * @param {string} logo [承兑方logo]
 * @param {string} cat [承兑方联系方式]
 * @param {string} des [承兑方描述信息]
 * @param {string} addi [附加信息]
 * @throws {error}
 */
function setAcceptance(id, pub, flNm, stNm, logo, cat, des, addi) {
    // Checking parameters.
    Utils.assert(_checkStr(id, 1, 32), _throwErr(error.ACP_ID_ERR));
    const addr = Utils.toAddress(pub);
    Utils.assert(_checkAddr(addr), _throwErr(error.PUB_ERR));
    Utils.assert(_checkStr(flNm, 1,1024), _throwErr(error.FL_NM_ERR));
    Utils.assert(_checkStr(stNm, 1,64), _throwErr(error.ST_NM_ERR));
    Utils.assert(_checkStr(logo, 0, 10240), _throwErr(error.LOGO_ERR));
    Utils.assert(_checkStr(cat, 1, 64), _throwErr(error.CAT_ERR));
    Utils.assert(_checkStr(des, 0, 64000), _throwErr(error.DES_ERR));

    // Checking whether the sender is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Setting the acceptance.
    const acpKey = _makeKey(keys.acp, id);
    let acp = {};
    acp.publicKey = pub;
    acp.fullName = flNm;
    acp.shortName = stNm;
    acp.logo = logo;
    acp.contact = cat;
    acp.description = des;
    acp.addition = addi;
    _store(acpKey, _toStr(acp));
}

/**
 * 查询承兑方信息
 * @param {string} acpId [承兑方的id]
 * @return {string} [承兑方信息]
 * @throws {error}
 */
function acceptanceInfo(acpId) {
    // Checking parameters.
    Utils.assert(_checkStr(acpId, 1, 32), _throwErr(error.ACP_ID_ERR));

    // Checking whether the accetance exists.
    const acpKey = _makeKey(keys.acp, acpId);
    const acpVal = _isExist(acpKey, error.ACP_NOT_EST);

    return acpVal;
}

/**
 * 获取 Lockup 信息
 * @param {string} lockId [Lockup的id]
 * @return {string} [Lockup信息]
 * @throws {error}
 */
function lockupInfo(lockId) {
    // Checking parameters.
    Utils.assert(_checkStr(lockId, 1, 64), _throwErr(error.LOCK_ID_ERR));

    // Checking whether the lockup exists.
    const lockVal = _isExist(_makeKey(keys.lock, lockId), error.LOCK_NOT_EST);

    return lockVal;
}

/**
 * 申请兑付
 * @param {string} repnId [兑付编号]
 * @param {string} skuId [SKU的id]
 * @param {string} trnId [Tranche的id]
 * @param {string} val [兑付SKU的数量]
 * @param {string} des [描述信息]
 * @param {JSONObject} addi [附加信息]
 * @throws {error}
 */
function requestRedemption(repnId, skuId, trnId, val, des, addi) {
    // Checking parameters.
    Utils.assert(_checkStr(repnId, 1, 32), _throwErr(error.REPN_ID_ERR));
    Utils.assert(_checkStr(skuId, 1, 32), _throwErr(error.SKU_ID_ERR));
    Utils.assert(_checkStr(trnId, 0, 32), _throwErr(error.TRN_ID_ERR));
    Utils.assert(Utils.stoI64Check(val) && Utils.int64Compare(val, 0) > 0, _throwErr(error.VAL_ERR));
    Utils.assert(_checkStr(des, 0, 64000), _throwErr(error.DES_ERR));

    // Checking whether the sku exists.
    const skuTkVal = _isExist(_makeKey(keys.sku, skuId), error.SKU_NOT_EST);
    const skuTk = _toJsn(skuTkVal);

    // Checking whether the tranche exists.
    trnId = _checkTranche(trnId, skuTk.defaultTrancheId, false);

    // Checking whether the redemption does not exist.
    const repnKey = _makeKey(keys.repn, repnId, gMsgSender);
    _isNotExist(repnKey, error.REPN_EST);

    // Creating the lockup.
    const lockId = Utils.sha256(`${repnId}${gMsgSender}`, 1);
    const prd = _dayToTime(skuTk.redemptionPeriod);
    const endTime = Utils.int64Add(gBlockTime, prd);
    _createLockup(lockId, gBlockTime, endTime);

    // Lockup the tokens.
    _lockup(gMsgSender, skuId, trnId, lockId, val, false);

    // Recording the redemption.
    let repn = {};
    repn.skuId = skuId;
    repn.trancheId = trnId;
    repn.lockupId = lockId;
    repn.value = val;
    repn.status = 0;
    repn.description = des;
    repn.requestTime = Chain.block.timestamp;
    repn.addition = addi;
    _store(repnKey, _toStr(repn));

    // Committing event.
    Chain.tlog('requestRedemption', _makeTlogSender(), repnId, skuId, trnId, val);
}


/**
 * 承兑方兑付
 * @param {string} repnId [兑付的id]
 * @param {string} apt [申请人区块链账户地址]
 * @param {string} logistics [总值信息，如快递信息]
 */
function redeem(repnId, apt, logistics) {
    // Checking parameters.
    Utils.assert(_checkStr(repnId, 1, 32), _throwErr(error.REPN_ID_ERR));
    Utils.assert(_checkAddr(apt), _throwErr(error.APT_ERR));
    Utils.assert(_checkStr(logistics, 1, 10240), _throwErr(error.LG_INFO_ERR));

    // Checking whether the redemption exists and checking whether the redemption status is 0.
    const repnKey = _makeKey(keys.repn, repnId, apt);
    const repnVal = _isExist(repnKey, error.REPN_NOT_EST);
    let repn = _toJsn(repnVal);
    Utils.assert(repn.status === 0, _throwErr(error.REPN_REQ_ERR));

    // Checking whether the sender is acceptor.
    const skuTkVal = _load(_makeKey(keys.sku, repn.skuId));
    const skuTk = _toJsn(skuTkVal);
    const acpKey = _makeKey(keys.acp, skuTk.acceptanceId);
    const acpVal = _load(acpKey);
    const acp = _toJsn(acpVal);
    const acpAddr = Utils.toAddress(acp.publicKey);
    Utils.assert(gTxSender === acpAddr, _throwErr(error.NOT_APR));

    // Changing the lockup end time.
    const seller = _toJsn(_load(keys.sel));
    const prd = _dayToTime(seller.confirmPeriod);
    const endTime = Utils.int64Add(gBlockTime, prd);
    _setLockup(repn.lockupId, endTime);

    // Redemptioning
    repn.status = 1;
    repn.redeemTime = gBlockTime;
    repn.logistics = logistics;
    _store(repnKey, _toStr(repn));

    // Committing event.
    Chain.tlog('redeem', _makeTlogSender(), repnId, apt);
}

/**
 * 兑付申请方确认兑付
 * @param {string} repnId [兑付的id]
 * @param {string} apt [申请人区块链账户地址]
 * @param {string} logistics [申请人区块链账户地址]
 * @throws {error}
 */
function confirmRedemption(repnId, apt) {
    // Checking parameters.
    Utils.assert(_checkStr(repnId, 1, 32), _throwErr(error.REPN_ID_ERR));
    Utils.assert(_checkAddr(apt), _throwErr(error.APT_ERR));

    // Checking whether the redemption exists.
    const repnKey = _makeKey(keys.repn, repnId, apt);
    const repnVal = _isExist(repnKey, error.REPN_NOT_EST);
    let repn = _toJsn(repnVal);

    // Checking whether the redemption status is 1.
    Utils.assert(repn.status === 1, _throwErr(error.REPN_RED_ERR));

    // If the redemption is not expired, checking whether the sender is applicant
    const lockVal = _load(_makeKey(keys.lock, repn.lockupId));
    const lock = _toJsn(lockVal);
    if (Utils.int64Compare(lock.endTime, gBlockTime) >= 0 || lock.endTime === '-1') {
        Utils.assert(gMsgSender === apt, _throwErr(error.NOT_APT));
    }

    // Unlocking the balance and transfer to redemption address.
    const skuTkVal = _load(_makeKey(keys.sku, repn.skuId));
    const skuTk = _toJsn(skuTkVal);
    _unlock(apt, repn.skuId, repn.trancheId, repn.lockupId, repn.value);
    _transfer(gMsgSender, repn.skuId, repn.trancheId, skuTk.redemptionAddress, repn.value);

    // Changing the redemption status to 2, which represents success.
    repn.status = 2;
    repn.finishTime = gBlockTime;
    _store(repnKey, _toStr(repn));

    // Committing event.
    Chain.tlog('confirmRedemption', _makeTlogSender(), repnId, apt);
}

/**
 * 取消兑付
 * @param {string} repnId [兑付的id]
 * @param {string} apt [申请人区块链账户地址]
 * @param {string} rsn [取消兑付的原因]
 */
function cancelRedemption(repnId, apt, rsn) {
    // Checking parameters.
    Utils.assert(_checkStr(repnId, 1, 32), _throwErr(error.REPN_ID_ERR));
    Utils.assert(_checkAddr(apt), _throwErr(error.APT_ERR));
    Utils.assert(_checkStr(rsn, 1, 64000), _throwErr(error.RSN_ERR));

    // Checking whether the sender is redemption applicant or seller
    const isApt = (gMsgSender === apt);
    const isSeller = _isSeller(gMsgSender);
    Utils.assert(isApt || isSeller, _throwErr(error.NOT_APT_SEL));

    // Checking whether the redemption exists.
    const repnKey = _makeKey(keys.repn, repnId, apt);
    const repnVal = _isExist(repnKey, error.REPN_NOT_EST);
    let repn = _toJsn(repnVal);

    // If sender is applicant.
    if (isApt) {
        // Checking whether the redemption status is 0.
        Utils.assert(repn.status === 0, _throwErr(error.REPN_REQ_ERR));

        // Checking whether the redemption is expired, otherwise the redemption cannot be cancelled.
        const lockVal = _load(_makeKey(keys.lock, repn.lockupId));
        const lock = _toJsn(lockVal);
        Utils.assert(Utils.int64Compare(lock.endTime, gBlockTime) < 0 && lock.endTime !== '-1', _throwErr(error.REPN_NOT_EXP_ERR));
    }

    // If sender is seller.
    if (isSeller) {
        // Checking whether the redemption status is 1.
        Utils.assert(repn.status === 1, _throwErr(error.REPN_RED_ERR));
    }

    // Unlocking the balance.
    _unlock(apt, repn.skuId, repn.trancheId, repn.lockupId, repn.value);

    // Setting the redemption status.
    repn.status = -1;
    repn.cancelTime = gBlockTime;
    repn.cancelAddress = gMsgSender;
    repn.cancelReason = rsn;
    _store(repnKey, _toStr(repn));

    // Committing event.
    Chain.tlog('cancelRedemption', _makeTlogSender(), repnId, apt);
}

/**
 * 查询兑付信息
 * @param {string} repnId [兑付的id]
 * @param {string} apt [申请人区块链账户地址]
 * @throws {error}
 * @return {string}
 */
function redemptionInfo(repnId, apt) {
    // Checking parameters.
    Utils.assert(_checkStr(repnId, 1, 32), _throwErr(error.REPN_ID_ERR));
    Utils.assert(_checkAddr(apt), _throwErr(error.APT_ERR));

    // Checking whether the redemption exists.
    const repnKey = _makeKey(keys.repn, repnId, apt);
    const repnVal = _isExist(repnKey, error.REPN_NOT_EST);

    return repnVal;
}

/**
 * 设置商家信息
 * @param {string} cpyFlNm [公司名称全称]
 * @param {string} cpyStName [公司名称简称]
 * @param {string} cpyCat [公司联系方式]
 * @param {string} des [描述信息]
 * @param {Object} cpyCer [属性]
 * @throws {error}
 */
function setSeller(cpyFlNm, cpyStNm, cpyCat, des, cpyCer) {
    // Checking whether the sender is seller.
    Utils.assert(_isSeller(gTxSender), _throwErr(error.NOT_SEL));

    // Setting the seller.
    _setSeller(gTxSender, cpyFlNm, cpyStNm, cpyCat, des, cpyCer);

    // Committing event.
    Chain.tlog('setSeller', _makeTlogSender(), cpyFlNm, cpyStNm, cpyCat);
}

/**
 * 登记注册
 * @param {string} cmFlNm [Company Full Name]
 * @param {string} cmStNm [Company Short Name]
 * @param {string} cmCat [Company Contact]
 * @param {string} cmCer [Company Certification]
 * @throws {error}
 */
function register(cmFlNm, cmStNm, cmCat, des, cfmPrd, cmCer) {
    // Checking whether the contract is not registerred.
    _isNotExist(keys.crt, error.REG_ERR);

    // Setting the seller.
    Utils.assert(Utils.stoI64Check(cfmPrd) && Utils.int64Compare(cfmPrd, 0) > 0, _throwErr(error.CFM_PRD_ERR));
    _setSeller(gTxSender, cmFlNm, cmStNm, cmCat, des, cmCer, cfmPrd);

    // Setting the contract info.
    let crt = {};
    crt.name = "ATP60";
    crt.version = '1.1.1';
    crt.logicAddress = gThisAddress;
    crt.entryAddress = Chain.thisAddress;
    _store(keys.crt, _toStr(crt));

    // Creating default tranche with id 0.
    const dftTrn = {};
    _store(_makeKey(keys.trn, '0'), _toStr(dftTrn));

    // Committing event.
    Chain.tlog('register', _makeTlogSender(), cmFlNm, cmStNm, Chain.thisAddress, gThisAddress);
}

/**
 * 修改合约地址
 * @throws {error}
 */
function setLogicContract(newLgAddr) {
    // Checking parameter
    Utils.assert(Utils.addressCheck(newLgAddr) && Utils.int64Compare(Chain.getBalance(newLgAddr), 0) > 0, JSON.stringify(error.NEW_LOG_ADDR_ERR));

    // Checking whether the sender is seller.
    const isSel = _isSeller(gTxSender);
    Utils.assert(isSel, _throwErr(error.NOT_SEL));

    // Checking whether the contract is registerred.
    const crtVal = _isExist(keys.crt, error.NOT_CRT_ERR);
    let crt = JSON.parse(crtVal);

    // Checking whether the contract address is not setted.
    Utils.assert(crt.logicAddress !== newLgAddr, _throwErr(error.CRT_SET_ERR));

    // Setting the contract address.
    crt.logicAddress = newLgAddr;
    _store(keys.crt, _toStr(crt));

    // Committing event.
    Chain.tlog('setLogicContract', _makeTlogSender(), newLgAddr);
}

/**
 * 获取合约信息
 */
function contractInfo() {
    return _load(_makeKey(keys.crt));
}

function init(bar) {
    return;
}

function main(input) {
    const data = _toJsn(input);
    const method = data.method || '';
    const params = data.params || {};

    gThisAddress = params.logicAddress;

    if (method === 'register') {
        register(params.companyFullName, params.companyShortName, params.companyContact, params.description, params.confirmPeriod, params.companyCertification);
    } else {
        // Checking the entry contract.
        _checkEntryContract();

        switch (method) {
            case 'setLogicContract':
                setLogicContract(params.newLogicAddress);
                break;
            case 'setSeller':
                setSeller(params.companyFullName, params.companyShortName, params.companyContact, params.description, params.companyCertification);
                break;
            case 'setDocument':
                setDocument(params.id, params.name, params.url, params.hashType, params.hash);
                break;
            case 'createSpu':
                createSpu(params.id, params.name, params.type, params.description, params.attributes);
                break;
            case 'setSpu':
                setSpu(params.spuId, params.name, params.type, params.description, params.attributes);
                break;
            case 'issue':
                issue(params.skuId, params.trancheId, params.isDefaultTranche, params.spuId, params.name, params.symbol, params.faceValue, params.supply, params.decimals, params.mainIcon, params.viceIcons, params.labels, params.description, params.redemptionAddress, params.redemptionPeriod, params.acceptanceId, params.abstracts, params.attributes);
                break;
            case 'setSkusChoice':
                setSkusChoice(params.spuId, params.choice);
                break;
            case 'setSku':
                setSku(params.skuId, params.name, params.symbol, params.mainIcon, params.viceIcons, params.labels, params.description, params.redemptionAddress, params.acceptanceId, params.abstracts, params.attributes);
                break;
            case 'additionalIssuance':
                additionalIssuance(params.skuId, params.trancheId, params.supply);
                break;
            case 'assignToTranche':
                assignToTranche(params.skuId, params.toTrancheId, params.value);
                break;
            case 'setAuthorizers':
                setAuthorizers(params.skuId, params.authorizers);
                break;
            case 'authorizeSku':
                authorizeSku(params.skuId, params.trancheId);
                break;
            case 'createTranche':
                createTranche(params.id, params.description, params.limits);
                break;
            case 'destroy':
                destroy(params.address, params.skuId, params.trancheId, params.value);
                break;
            case 'transferFrom':
                transferFrom(params.from, params.skuId, params.trancheId, params.to, params.value);
                break;
            case 'transfer':
                transfer(params.skuId, params.trancheId, params.to, params.value);
                break;
            case 'approve':
                approve(params.spender, params.skuId, params.trancheId, params.value);
                break;
            case 'setAcceptance':
                setAcceptance(params.id, params.publicKey, params.fullName, params.shortName, params.logo, params.contact, params.description, params.addition);
                break;
            case 'requestRedemption':
                requestRedemption(params.redemptionId, params.skuId, params.trancheId, params.value, params.description, params.addition);
                break;
            case 'redeem':
                redeem(params.redemptionId, params.applicant, params.logistics);
                break;
            case 'cancelRedemption':
                cancelRedemption(params.redemptionId, params.applicant, params.reason);
                break;
            case 'confirmRedemption':
                confirmRedemption(params.redemptionId, params.applicant);
                break;
            default:
                throw _throwErr(error.MTD_ERR);
        }
    }
}

function query(input) {
    // Checking the entry contract.
    _checkEntryContract();

    const data = _toJsn(input);
    const method = data.method || '';
    const params = data.params || {};

    let val = null;
    switch (method) {
        case 'contractInfo':
            val = contractInfo();
            break;
        case 'sellerInfo':
            val = sellerInfo();
            break;
        case 'allDocuments':
            val = allDocuments();
            break;
        case 'skusOfSpu':
            val = skusOfSpu(params.spuId);
            break;
        case 'skusOfTranche':
            val = skusOfTranche(params.trancheId);
            break;
        case 'tranchesOfSku':
            val = tranchesOfSku(params.skuId);
            break;
        case 'tranchesOf':
            val = tranchesOf(params.skuId, params.address);
            break;
        case 'acceptanceInfo':
            val = acceptanceInfo(params.acceptanceId);
            break;
        case 'documentInfo':
            val = documentInfo(params.documentId);
            break;
        case 'spuInfo':
            val = spuInfo(params.spuId);
            break;
        case 'tokenInfo':
            val = tokenInfo(params.skuId);
            break;
        case 'totalSupplyByTranche':
            val = totalSupplyByTranche(params.skuId, params.trancheId);
            break;
        case 'trancheInfo':
            val = trancheInfo(params.trancheId);
            break;
        case 'authorizedSku':
            val = authorizedSku(params.skuId, params.trancheId, params.authorizer);
            break;
        case 'balanceOf':
            val = balanceOf(params.address, params.skuId);
            break;
        case 'balanceOfByTranche':
            val = balanceOfByTranche(params.address, params.skuId, params.trancheId);
            break;
        case 'balanceOfByLockup':
            val = balanceOfByLockup(params.address, params.skuId, params.trancheId, params.lockupId);
            break;
        case 'allowance':
            val = allowance(params.owner, params.skuId, params.trancheId, params.spender);
            break;
        case 'redemptionInfo':
            val = redemptionInfo(params.redemptionId, params.applicant);
            break;
        case 'lockupInfo':
            val = lockupInfo(params.lockupId);
            break;
        default:
            throw _throwErr(error.MTD_ERR);
    }
    return val;
}