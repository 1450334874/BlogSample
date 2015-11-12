package cn.eid.sample.idspsdk.test;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import android.util.Log;

import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.defines.common.HashDataFrom;
import com.trimps.eid.sdk.defines.common.SignAlgInfo;
import com.trimps.eid.sdk.defines.common.SignResult;
import com.trimps.eid.sdk.defines.common.pin.PinResult;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;

import cn.eid.sample.idspsdk.common.BaseApiTest;
import cn.eid.sample.idspsdk.common.ConverterUtil;

/**
 * ˽Կǩ�� 
 */
public class PrivateKeySignTest extends BaseApiTest {

	private static final String TAG = PrivateKeySignTest.class.getName();
	
	private static final int PLAINTEXT_LENGTH = 117;
	private byte[] inData = new byte[PLAINTEXT_LENGTH];
	
	private byte[] inputData = new byte[1024];

	private String pin = "";

	public PrivateKeySignTest(DeviceReader reader, String pin) {
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
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("login", ret);
			
			closeDevice(deviceReader);
			result.more = buildMore();
			result.isOK = false;
			
			return result;
			
		}	
		appendMoreSuccess("login");
		
		
		
		
		
		
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
		
		
		
		//���ǩ��ԭ�ģ��Ѿ�base64���ܣ�����Բο����´�����н��ܺ���ǩ����
		String data2Sign = new String("MjAxNTEwMjcxMDMzMTk6MjAxNTEwMjcxMDMzMTk6ZGF0YQ==");
		byte[] data_to_sign_bs = null;
		try {
			
			data_to_sign_bs = Base64.decodeBase64(data2Sign.getBytes("utf-8"));
			System.out.println("data_to_sign����ԭ�ģ� " + new String(data_to_sign_bs, "utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			//replace the "inData" with data_to_sign_bs
			ret = deviceReader.signUpdate(data_to_sign_bs);
			
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
		
		result.isOK = true;
		
		appendMore("ʵ��ʹ�õ�sign�㷨��" + signResult.alg);
		appendMore("signFinal - " + from +  " �� " + ConverterUtil.getHexString(
				signResult.data, 
				signResult.data.length));

		
		//���ԡ����õ�ǩ�����ݣ�����base64����
		byte[] dataOutEncoded = Base64.encodeBase64(signResult.data);
		try {
			
			//��ת��String�������ύ����������֤
			String sign = new String(dataOutEncoded, "utf-8");
			Log.d("RSA or SM2", "sign�� " + sign);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
			ret = deviceReader.logout();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMore("logoutʧ��", ret);
			closeDevice(deviceReader);
			
			result.isOK = false;
			result.more = buildMore();
			return result;
			
		}
		appendMore("logout�ɹ�");
		result.more = buildMore();
		
		closeDevice(deviceReader);
		
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

		System.arraycopy(inputData, 0, inData, 0, PLAINTEXT_LENGTH);

	}

}
