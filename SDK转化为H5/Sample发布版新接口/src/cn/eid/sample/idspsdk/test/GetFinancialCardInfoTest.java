package cn.eid.sample.idspsdk.test;

import com.trimps.eid.sdk.defines.common.ErrorCode;
import com.trimps.eid.sdk.defines.common.FinancialCardInfo;
import com.trimps.eid.sdk.defines.common.FinancialCardInfo.CardType;
import com.trimps.eid.sdk.idspapi.core.DeviceReader;

import cn.eid.sample.idspsdk.common.BaseApiTest;

/**
 * �ж��Ƿ����IC�� 
 */
public class GetFinancialCardInfoTest extends BaseApiTest {

	public GetFinancialCardInfoTest(DeviceReader reader) {
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
			
		
		
		FinancialCardInfo cardInfo = new FinancialCardInfo();
		try {
			
			ret = deviceReader.getFinancialCardInfo(cardInfo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (ErrorCode.ERR_SUCCESS != ret) {
			
			appendMoreFailed("getFinancialCardInfo", ret);
			result.isOK = false;
			result.more = buildMore();
			
			closeDevice(deviceReader);
			
			return result;
			
		}
		
		if (cardInfo.isOK()) {
			
			String cardType = convertCardType(cardInfo.getCardType());
			appendMore("getFinancialCardInfo �ɹ����ǽ��ڿ������ͣ�" + cardType + "\"");
			
		} else {
			
			appendMore("getFinancialCardInfo �ɹ����ǽ��ڿ���");
			
		}

		closeDevice(deviceReader);
		result.isOK = true;
		result.more = buildMore();
		
		return result;
		
	}
	
	
	String convertCardType(int type) {
		
		String typeStr = "δ֪����";
		switch (type) {
		
		case CardType.SEMI_CREDIT_CARD:
			typeStr = "����׼����";
			break;

		case CardType.DEBIT_CARD:
			typeStr = "�������";
			break;
		
		case CardType.CREDIT_CARD:
			typeStr = "��������";
			break;
			
		case CardType.CASH_CARD:
			typeStr = "���������ֽ�";
			break;

		default:
			break;
		}
		
		return typeStr;
		
	}

}
