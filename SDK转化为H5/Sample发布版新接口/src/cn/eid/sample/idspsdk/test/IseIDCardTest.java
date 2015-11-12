package cn.eid.sample.idspsdk.test;

import cn.eid.sample.idspsdk.common.BaseApiTest;

import com.trimps.eid.sdk.defines.base.BoolResult;
import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;

/**
 * 
 * �ж��Ƿ�eID������Ҫ�ȳɹ�����open��
 * ������óɹ�������ͨ������BoolResult��boolean��Աdata��ֵ���ж��Ƿ�eID����
 */
public class IseIDCardTest extends BaseApiTest {

	public IseIDCardTest(DeviceReader reader) {
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
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMore("openDeviceʧ��", ret);
			result.isOK = false;
			result.more = buildMore();
			return result;
			
		}
		appendMore("openDevice�ɹ�");
		
		
		BoolResult code = new BoolResult();
		try {
			
			ret = deviceReader.iseIDCard(code);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ErrorCode.ERR_SUCCESS != ret) {
			
			appendMore("iseIDCard ʧ��", ret);
			closeDevice(deviceReader);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		
		appendMore("iseIDCard �ɹ���" + (code.result ? "��eID��" : "��eID��"));
		closeDevice(deviceReader);
		
		result.isOK = true;
		result.more = buildMore();
		
		return result;
		
	}

}
