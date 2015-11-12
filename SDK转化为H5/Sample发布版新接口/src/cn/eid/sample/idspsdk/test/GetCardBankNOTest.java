package cn.eid.sample.idspsdk.test;

import com.trimps.eid.sdk.defines.base.StringResult;
import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;

import cn.eid.sample.idspsdk.common.BaseApiTest;

/**
 * ��ȡ���п�����
 */
public class GetCardBankNOTest extends BaseApiTest {

	public GetCardBankNOTest(DeviceReader reader) {
		super(reader);
		
		
	}
	
	@Override
	public ResultData perform() {
		
		ResultData result = new ResultData();

		long ret = ErrorCode.ERR_SUCCESS;
		try {
			
			ret = deviceReader.openDevice();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(ErrorCode.ERR_SUCCESS != ret){
			
			appendMore("openDevice ʧ��", ret);
			result.isOK = false;
			result.more = buildMore();
			return result;
			
		}
		
		appendMore("openDevice �ɹ�");
		
		StringResult cardBankNO = new StringResult();
		try {
			
			ret = deviceReader.getCardBankNO(cardBankNO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(ErrorCode.ERR_SUCCESS != ret){
			
			result.isOK = false;							
			appendMore("getCardBankNO ʧ��", ret);
			
		}else {
			
			result.isOK = true;
			appendMore("getCardBankNO �ɹ������п���Ϊ��\"" + cardBankNO.data + "\"");
			
			
		}
		
		closeDevice(deviceReader);
		result.more = buildMore();
		
		return result;
		
	}

}
