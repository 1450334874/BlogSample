package cn.eid.sample.idspsdk.test;


import com.trimps.eid.sdk.defines.common.ErrorCode;

import cn.eid.sample.idspsdk.common.BaseApiTest;



public class ErrorCodeTest extends BaseApiTest {

	
	@Override
	public ResultData perform() {

		ResultData result = new ResultData();
		
		appendMore(ErrorCode.getErrorDescription(ErrorCode.ERR_BASE_IDSP | ErrorCode.ERR_HASH_ALG_NOT_SUPPORTED));
		appendMore(ErrorCode.getErrorDescription(ErrorCode.ERR_BASE_IDSP | ErrorCode.ERR_ASYM_ALG_NOT_SUPPORTED));
		appendMore(ErrorCode.getErrorDescription(ErrorCode.ERR_BASE_IDSP | ErrorCode.ERR_HASH_NOT_INIT));
		appendMore(ErrorCode.getErrorDescription(ErrorCode.ERR_BASE_IDSP | ErrorCode.ERR_HASH_NOT_UPDATE));
		
		String err = ErrorCode.getErrorDescription(0x23ad);
		appendMore("δ֪����루0x23ad��ʾ����" + err);
		
		err = ErrorCode.getErrorDescription(ErrorCode.ERR_BASE_USER | 0x4);
		appendMore("�û������0x4��ʾ����" + err);
		
		err = ErrorCode.getErrorDescription(ErrorCode.ERR_BASE_UAI | ErrorCode.ERR_INVALID_PARAM);
		appendMore("��������ʾ����" + err);
		
		result.isOK = true;
		result.more = buildMore();
		
		return result;
		
	}

}
