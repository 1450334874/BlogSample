package cn.eid.sample.idspsdk.test;

import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.defines.common.pin.PinResult;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;

import cn.eid.sample.idspsdk.common.BaseApiTest;

/**
 * ��¼��Ƭ
 * ÿ�ſ�Ƭ��������PIN�룬��Щ��������֤PIN������ʹ�ã���˽Կ��ǩ��
 * ���������������Ƭ��������
 * 
 * 
 * ����豸��ȫ״̬
 * ������Login.execute��¼�ɹ��󣬿�Ƭ�ᱣ�ֵ�ǰ�İ�ȫ״̬��
 * Ϊ�˷�ֹ����������ʿ�Ƭ�����¿��ܵİ�ȫ���⣬��������Ҫ���ָ�״̬ʱ�������Logout.execute������豸��ȫ״̬��
 */
public class LoginLogoutTest extends BaseApiTest {

	private String pin = "";
	
	public LoginLogoutTest(DeviceReader reader, String pin) {
		super(reader);
		
		this.pin = pin;
		
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
			
			ret = deviceReader.login(pin, pinResult);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			if (pinResult.isLock) {
				
				appendMore("login ʧ�ܣ���������", ret);
				
			} else{
				
				appendMore("login ʧ�ܣ���δ����", ret);
				
			}
			
			closeDevice(deviceReader);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		appendMore("login �ɹ�");
		
		
		try {
			
			ret = deviceReader.logout();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMore("logoutʧ��", ret);
			result.isOK = false;
			result.more = buildMore();
			closeDevice(deviceReader);
			
			return result;
			
		}
		appendMore("logout�ɹ�");
		
		closeDevice(deviceReader);
		result.isOK = true;
		result.more = buildMore();
		
		return result;
		
	}

}
