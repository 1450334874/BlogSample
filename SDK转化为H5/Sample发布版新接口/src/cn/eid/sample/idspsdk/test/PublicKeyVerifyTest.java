package cn.eid.sample.idspsdk.test;

import java.util.Random;

import cn.eid.sample.idspsdk.common.BaseApiTest;
import cn.eid.sample.idspsdk.common.ConverterUtil;

import com.trimps.eid.sdk.defines.base.BoolResult;
import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.defines.common.HashDataFrom;
import com.trimps.eid.sdk.defines.common.SignAlgInfo;
import com.trimps.eid.sdk.defines.common.SignResult;
import com.trimps.eid.sdk.defines.common.pin.PinResult;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;



/**
 * ��Կ��ǩ 
 */
public class PublicKeyVerifyTest extends BaseApiTest {
	
	private byte[] inputData = new byte[1024];
	
	private static final int PLAINTEXT_LENGTH = 117;
	private byte[] inData = new byte[PLAINTEXT_LENGTH];
	
	private String pin = "";
	
	public PublicKeyVerifyTest(DeviceReader reader, String pin) {
		super(reader);
		
		this.pin = pin;
		
	}
	
	@Override
	public ResultData perform() {

		ResultData result = new ResultData();
		
		setInputData();

		
		
		
		//���豸
		long ret = ErrorCode.ERR_SUCCESS;
		try {
			
			ret = deviceReader.openDevice();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("openDevice", ret);
			result.isOK = false;
			result.more = buildMore();
			return result;
			
			
		}
		appendMoreSuccess("openDevice");
		
		
		
		
		
		
		PinResult objPinResult = new PinResult();
		try {
			
			ret = deviceReader.login(pin, objPinResult);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("login", ret);
			
			closeDevice(deviceReader);
			result.more = buildMore();
			result.isOK = false;
			
			return result;
			
		}	
		appendMoreSuccess("login");
		

		
		
		
		//sign + verify
		SignAlgInfo info = new SignAlgInfo();
		try {
			
			ret = deviceReader.getSignAlg(info);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("getSignAlg", ret);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		appendMoreSuccess("getSignAlg");
		appendMore("��ǰ��Ƭ֧�ֵ�ǩ���㷨��\r\n\t" + info.alg + "[Index = " + info.alg.getIndex() + "]");
		
		
		
		
		
		HashDataFrom from = HashDataFrom.EXTERNAL;
		
		//�ɸ���ǩ���㷨��ָ��hash�㷨��Ҳ����ֱ��ָ��autoģʽ
		try {
			
			ret = deviceReader.signInit(info.alg, from);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("signInit", ret);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		appendMoreSuccess("signInit");
		
		
		try {
			
			ret = deviceReader.signUpdate(inData);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("signUpdate", ret);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		appendMoreSuccess("signUpdate");
		
		
		
		
		SignResult signResult = new SignResult();
		try {
			
			ret = deviceReader.signFinal(signResult);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("signFinal", ret);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		appendMoreSuccess("signFinal");
		
		appendMore("ʵ��ʹ�õ�sign�㷨��" + signResult.alg);
		appendMore("signFinal - " + from +  " �� " + ConverterUtil.getHexString(
				signResult.data, 
				signResult.data.length));
		
		
		
		try {
			
			ret = deviceReader.verifyInit(signResult, from);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("verifyInit", ret);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		appendMoreSuccess("verifyInit");
		
		
		
		try {
			
			ret = deviceReader.verifyUpdate(inData);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("verifyUpdate", ret);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		appendMoreSuccess("verifyUpdate");
		
		
		BoolResult isVerifySucceed = new BoolResult();
		try {
			
			ret = deviceReader.verifyFinal(isVerifySucceed);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("verifyFinal", ret);
			result.isOK = false;
			result.more = buildMore();
			
			return result;
			
		}
		
		result.isOK = isVerifySucceed.result;
		appendMore("verifyFinal ��" + signResult.alg + " ����ǩ" + (isVerifySucceed.result ? "�ɹ�" : "ʧ��") + "��");
		
		
		
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
	
	/**
	 * ׼������
	 */
	private void setInputData() {

		Random r = new Random();
		int value;
		for (int i = 0; i < inputData.length; i++) {
			value = r.nextInt(16);
			inputData[i] = (byte) value;
		}

		System.arraycopy(inputData, 0, inData, 0,
				PLAINTEXT_LENGTH);

	}

}
