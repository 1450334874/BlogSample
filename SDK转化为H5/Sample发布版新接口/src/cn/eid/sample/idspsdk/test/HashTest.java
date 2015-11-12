package cn.eid.sample.idspsdk.test;

import java.util.Random;

import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.defines.common.HashDataFrom;
import com.trimps.eid.sdk.defines.common.HashResult;
import com.trimps.eid.sdk.defines.common.SignAlgInfo;
import com.trimps.eid.sdk.defines.common.alg.HashAlg;
import com.trimps.eid.sdk.defines.common.alg.SignAlg;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;

import cn.eid.sample.idspsdk.common.BaseApiTest;
import cn.eid.sample.idspsdk.common.ConverterUtil;


/**
 *  Hash�㷨
 *  ��֧��SHA1��SM3
 */
public class HashTest extends BaseApiTest {

	public HashTest(DeviceReader reader) {
		super(reader);
		
		
	}
	
	private byte[] hashData_129 = new byte[129];
	
	@Override
	public ResultData perform() {
	
		ResultData result = new ResultData();
		
		setInputData();
		
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
		
		//�ɸ���ǩ���㷨��ָ��hash�㷨��Ҳ����ֱ��ָ��autoģʽ
		
		HashAlg hashAlg = null;
		if (info.alg == SignAlg.TEID_ALG_SHA1_WITH_RSA) {
			
			hashAlg = HashAlg.TEID_ALG_SHA1;
			
		} else if (info.alg == SignAlg.TEID_ALG_SM3_WITH_SM2) {
			
			hashAlg = HashAlg.TEID_ALG_SM3;
			
		}
		
		//����autoģʽ
		hashAlg = HashAlg.TEID_ALG_AUTO;
		appendMore("ǩ���㷨ָ��Ϊ��HashAlg.TEID_ALG_AUTO");

		HashDataFrom from = HashDataFrom.DEFAULT;
		from = HashDataFrom.EXTERNAL_AND_CARD;
		for (int i = 0; i < 3; ++i) {
		
			if (i == 0) {
				
				from = HashDataFrom.DEFAULT;
				
			} else if (i == 1) {
				
				from = HashDataFrom.EXTERNAL_AND_CARD;
				
			} else if (i == 2) {
				
				from = HashDataFrom.EXTERNAL;
				
			}
			
			try {
				
				ret = deviceReader.hashInit(hashAlg, from);
				if (ErrorCode.ERR_SUCCESS != ret) {
					
					appendMoreFailed("hashInit", ret);
					result.isOK = false;
					result.more = buildMore();
					
					closeDevice(deviceReader);
					
					return result;
					
				}
				
				ret = deviceReader.hashUpdate(hashData_129);
				if (ErrorCode.ERR_SUCCESS != ret) {
					
					appendMoreFailed("hashUpdate", ret);
					result.isOK = false;
					result.more = buildMore();
					
					closeDevice(deviceReader);
					
					return result;
					
				}
				
				HashResult hashResult = new HashResult();
				ret = deviceReader.hashFinal(hashResult);
				if (ErrorCode.ERR_SUCCESS != ret) {
					
					appendMoreFailed("hashFinal", ret);
					result.isOK = false;
					result.more = buildMore();
					
					closeDevice(deviceReader);
					
					return result;
					
				}
				appendMoreSuccess("hashFinal");
				
				appendMore("ʵ��ʹ�õ�Hash�㷨��" + hashResult.alg);
				appendMore("hashResult - " + from + " �� " + ConverterUtil.getHexString(
						hashResult.data, 
						hashResult.data.length));
				
	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		closeDevice(deviceReader);
		result.more = buildMore();
		result.isOK = true;
		
		return result;
		
	}
	
	/**
	 * ׼������
	 */
	private void setInputData() {

		Random r = new Random();
		int value;
		for (int i = 0; i < hashData_129.length; i++) {
			
			value = r.nextInt(16);
			hashData_129[i] = (byte) value;
			
		}

	}
	
}
