package cn.eid.sample.idspsdk.test;

import cn.eid.sample.idspsdk.common.BaseApiTest;
import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.defines.common.pin.PinResult;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;

/**
 * 
 * �޸�eIDǩ������
 * <br/>�޸��������������ȷ�����룬���������������Ƭ��������
 */
public class ChangePinTest extends BaseApiTest {

	private String oldPin = "";
	private String newPin = "";
	
	public ChangePinTest(DeviceReader reader, String oldPin, String newPin) {
		super(reader);
		
		this.oldPin = oldPin;
		this.newPin = newPin;
		
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
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMore("openDeviceʧ��", ret);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
		}
		appendMore("openDevice�ɹ�");
		
		
		PinResult pinResult = new PinResult();
		try {
			
			ret = deviceReader.changePin(oldPin, newPin, pinResult);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			result.isOK = false;
			if (pinResult.isLock) {
				
				appendMore("changePin ʧ�ܣ���������", ret);
				
			} else {
				
				appendMore("changePin ʧ�ܣ���δ����", ret);
				
			}
			
			closeDevice(deviceReader);
			result.isOK = false;
			result.more = buildMore();
			return result;

		} 
		
		appendMore("changePin �ɹ�");
		
		closeDevice(deviceReader);
		
		result.isOK = true;
		result.more = buildMore();
		return result;
		
	}

}
