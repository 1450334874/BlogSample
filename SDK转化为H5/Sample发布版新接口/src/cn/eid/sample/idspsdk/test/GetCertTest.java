package cn.eid.sample.idspsdk.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.security.cert.X509Certificate;

import com.trimps.eid.sdk.defines.base.ByteResult;
import com.trimps.eid.sdk.defines.common.CertInfo;
import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;
import android.text.format.DateFormat;
import cn.eid.sample.idspsdk.common.BaseApiTest;
import cn.eid.sample.idspsdk.common.ConverterUtil;

/**
 * 
 * ��ȡ֤��
 * <br/>
 * <br/>��ȡ֤��ǰ����֪���������� ��ȡ֤��󣬿ɶ�֤����н��������Ի��������Ϣ
 * <br/>�������н�����Կ��ǩ����Կ��������Կ���ڼӽ��ܣ�ǩ����Կ����ǩ����ǩ����ǰ������ֻ��ǩ����Կ��
 * <br/>Constants.TEID_KEY_TYPE������ֵ����ֵΪTEID_SIGN��ʹ��ǩ����Կ��ֵΪTEID_KEYEX��ʹ�ý�����Կ
 */
public class GetCertTest extends BaseApiTest {

	public GetCertTest(DeviceReader reader) {
		super(reader);
		
		
	}
	
	@SuppressWarnings("rawtypes")
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
		

		ByteResult cert = new ByteResult();
		try {
			
			ret = deviceReader.getCert(cert);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMore("getCert ʧ��", ret);
			
			result.isOK = false;
			result.more = buildMore();
			
			closeDevice(deviceReader);
			
			return result;

		} else {

			result.isOK = true;
			
			HashMap<String, String> certData = readCertificateInformation(cert.data);
			if (null != certData && certData.size() > 0) {
				
				appendMore("getCert �ɹ���֤����Ϣ���£�");
				
				Iterator iter = certData.entrySet().iterator();
				while (iter.hasNext()) {
					
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					appendMore("\"" + key + "\"��	" + val);
					
				}

			} else {

				appendMore("getCert �ɹ���֤�����ʧ��!");
				result.isOK = false;

			}

		}
		
		CertInfo certInfo = new CertInfo();
		try {
			
			ret = deviceReader.getCert(certInfo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ret == ErrorCode.ERR_SUCCESS) {

			appendMore("getCert �ɹ�");
			result.isOK = true;
			
			printCertInfo(certInfo);

		} else {
	
			appendMore("getCert ʧ��", ret);
			result.isOK = false;
			
		}
		
			
		closeDevice(deviceReader);
		result.more = buildMore();
		
		return result;
		
	}

	private HashMap<String, String> readCertificateInformation(byte[] buf) {

		HashMap<String, String> hashMap = new HashMap<String, String>();

		try {

			InputStream input = new ByteArrayInputStream(buf);
			Certificate certificate = CertificateFactory.getInstance("X.509", "BC").generateCertificate(input);

			X509Certificate X509certificates = X509Certificate .getInstance(certificate.getEncoded());
			
			String version = convertCertVersion(X509certificates.getVersion());
			
			String issuerDN = X509certificates.getIssuerDN().toString();

			String endDate = DateFormat.format("yyyy-MM-dd HH:mm:ss E", X509certificates.getNotAfter()).toString();
			String beginDate = DateFormat.format("yyyy-MM-dd HH:mm:ss E", X509certificates.getNotBefore()).toString();

			String serialNumber = X509certificates.getSerialNumber().toString(16);
			String sigAlgName = X509certificates.getSigAlgName();
			String sigAlgOID = X509certificates.getSigAlgOID();
			byte[] sigAlgParams = X509certificates.getSigAlgParams();
			String subjectDN = X509certificates.getSubjectDN().getName();
			
			hashMap.put("version", version); // ֤��İ汾��
			hashMap.put("issuerDN", issuerDN);// ����ı��
			hashMap.put("beginDate", beginDate);// ����֤��������Ч��
			hashMap.put("endDate", endDate);// ����֤��Ŀ�ʼ����
			hashMap.put("serialNumber", serialNumber);// ����֤������к�
			hashMap.put("sigAlgName", sigAlgName);// ����֤���ǩ��
			hashMap.put("sigAlgOID", sigAlgOID);// ����OIDǩ���㷨��֤��
			
			if(sigAlgParams != null) {
			
				hashMap.put("sigAlgParams", ConverterUtil.getHexString(
					sigAlgParams, sigAlgParams.length));
			}else{
				hashMap.put("sigAlgParams", null);
			}
			
			hashMap.put("subjectDN", subjectDN);

			return hashMap;

		} catch (Exception e) {

			// recordLog(CustomUtil.LogMode.ERROR, "readCertificateInformation",
			// e.getMessage(), true);
			// new RuntimeException("֤���쳣�����Ժ�����");

			if (e != null)
				e.printStackTrace();

		}

		return null;
	}
	
	
	String convertCertVersion(int index) {
		
		String ret = null;
		switch (index) {
		
		case 0:
			ret = "v1";
			break;
			
		case 1:
			ret = "v2";
			break;
		
		case 2:
			ret = "v3";
			break;
			
		default:
			break;
			
		}
		
		if (null == ret) {
			
			return String.valueOf(index);
			
		}
		
		return ret;
		
	}
	
	
	void printCertInfo(CertInfo info) {
		
		appendMore("֤��䷢�ߣ�issuer����	" + info.getIssuer());
		appendMore("֤��䷢�����кţ�issuerSN����	" + info.getIssuerSN());
		appendMore("֤�����壨subject����	" + info.getSubject());
		appendMore("֤�����кţ�sn����	" + info.getSN());
		appendMore("֤��汾�ţ�version����	" + info.getVersion());
		appendMore("֤����Ч�ڣ�period����	" + info.getBeginDate() + "~" + info.getEndDate());
		appendMore("֤��ǩ���㷨��sigAlgName����	" + info.getSigAlgName());
		appendMore("֤��ǩ���㷨OID��sigAlgOID����	" + info.getSigAlgOID());
		appendMore("֤��ǩ���㷨������sigAlgParams����	" + info.getSigAlgParams());
		
	}

}
