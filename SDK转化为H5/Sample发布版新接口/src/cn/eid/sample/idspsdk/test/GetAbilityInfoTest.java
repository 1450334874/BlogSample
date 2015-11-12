package cn.eid.sample.idspsdk.test;

import cn.eid.sample.idspsdk.common.BaseApiTest;
import cn.eid.sample.idspsdk.common.ConverterUtil;

import com.trimps.eid.sdk.defines.common.AbilityInfo;
import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;

/**
 * 
 * ��ȡ�����ļ���Ϣ
 *
 * <br/>�ɻ�ȡ�İ������ǶԳ��㷨������ʶ��Hash�㷨������ʶ�������Զ����ʶ������������롢
 * <br/> оƬ�汾�� ��COS�汾�š���汾�š��ļ�ϵͳ�汾�š�JAVA�汾�š�������֤��ʶ���Գ��㷨������ʶ��������ʶ��Ϣ
 */
public class GetAbilityInfoTest extends BaseApiTest {

	public GetAbilityInfoTest(DeviceReader reader) {
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
			
			appendMore("openDevice ʧ��", ret);
			result.isOK = false;
			result.more = buildMore();
			return result;
			
		}
		
		appendMore("openDevice �ɹ�");
		
		AbilityInfo info = new AbilityInfo();
		try {
			
			ret = deviceReader.getAbilityInfo(info);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {				
			
			appendMore("getAbilityInfo ʧ��", ret);
			
			closeDevice(deviceReader);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		appendMore("getAbilityInfo �ɹ�");
		
		{
			appendMore("�������ļ���ϢΪ����");
			appendMore(" �ǶԳ��㷨������ʶ: 0x" + Integer.toHexString(info.asymmetricItems));
			appendMore(" Hash�㷨������ʶ: 0x" + Integer.toHexString(info.hashItems));
			appendMore(" оƬ�ͺ�: 0x" + Integer.toHexString(info.shChipVer));
			appendMore(" COS���̴���: 0x" + Integer.toHexString(info.shCosVer));
			appendMore(" ��汾��: 0x" + Integer.toHexString(info.shDllVer));
			appendMore(" �ļ�ϵͳ�汾��: 0x" + Integer.toHexString(info.shFileSystemVer));
			appendMore(" Java�汾��: 0x" + Integer.toHexString(info.shJavaVer));
			appendMore(" ������֤��ʶ: 0x" + Integer.toHexString(info.shOfflineFlag));
			appendMore(" �Գ��㷨������ʶ: 0x" + Integer.toHexString(info.symmetricItems));
			appendMore(" �����Զ����ʶ: 0x" + ConverterUtil.getHexString(info.idCarrier, info.idCarrier.length));
			appendMore(" �����������: 0x" + ConverterUtil.getHexString(info.issuerOrg, info.issuerOrg.length ));
			appendMore(" �û���˽Կ���㷨��ʶ: 0x" + Integer.toHexString(info.shUserAsymAlgType));
			
		}
		
		closeDevice(deviceReader);
		
		result.isOK = true;
		result.more = buildMore();
		return result;
		
	}

}
