package com.notificationlab.soochak;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.notificationlab.soochak.dto.Message;

import lombok.Data;

@Configuration
public class Test implements ApplicationRunner{

	@Data
	private static class Employee{
		
		Employee(){};
		
//		Employee(String a, String b){
//			this.a = a;
//			this.b = b;
//			
//		}
		
		private boolean a;
		
		//@org.codehaus.jackson.annotate.JsonIgnore
		private String b;
		
		private EmployeeType t;
		
		
		
	}
	
	private static enum EmployeeType{
		HELLO
	}
	
	public void run(ApplicationArguments args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> name = new HashMap<String, Object>();
		name.put("fname", "himanshu");
		name.put("lname", "shekahr");
		map.put("name", name);
		Message msg = new Message(1000000000l, map, null);
		String content = mapper.writeValueAsString(msg);
		System.out.println("=====================================");
		System.out.println(content);
		System.out.println("=====================================");
//		TypeReference<Message<Map<String, Object>>> typeRef 
//        = new TypeReference<Message<Map<String, Object>>>() {};
//		TypeReference<Map<String, Object>> typeRef 
//        = new TypeReference<Map<String, Object>>() {};
        Message msg1 = mapper.readValue(content, Message.class);
		System.out.println(msg1);
	}

	public static void main(String[] a) throws IOException {
		//org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(1));
		System.out.println(new Gson().toJson(1));
		//Employee emp = new Employee("Himanshu", "Shekhar");
		Employee emp = new Employee();
		emp.setA(true);
		emp.setB("Shekhar");
		System.out.println(emp);
		System.out.println("*****************************");
		String json = mapper.writeValueAsString(emp);
		System.out.println(json);
		System.out.println("*****************************");
		//String json2 = "{\"a\":\"Himanshu\",\"b\":\"Shekhar\"}";
		String json2 = "{\"b\":\"Shekhar\"}";
		String json3 = "{\"a\":\"true\"}";
		Employee emp1 = mapper.readValue(json2, Employee.class);
		System.out.println(emp1);
		System.out.println("*****************************");
		
		
		
		BigDecimal bg1 = new BigDecimal("9999.99");
		BigDecimal hundred = new BigDecimal("100");
		
		System.out.println(bg1.multiply(hundred).setScale(0));
		
		BigDecimal limitAmountInPaisa = new BigDecimal("10009");
		limitAmountInPaisa = limitAmountInPaisa.setScale(2);
		limitAmountInPaisa = limitAmountInPaisa.divide(hundred);
		System.out.println(limitAmountInPaisa);
		
		System.out.println("NEW CODE TEST");
		
		
		String jsonStr = "{\\\"head\\\" : {\\\"clientId\\\":\\\"PAYTM-EDC\\\",\\\"version\\\":\\\"v1\\\", \\\"responseTimestamp\\\":\\\"1507123312139\\\",\\\"signature\\\":\\\"<CHECKSUMHASH>\\\"},\\\"body\\\": {\\\"resultInfo\\\": {\\\"resultStatus\\\": \\\"SUCCESS\\\",\\\"resultCode\\\": \\\"01\\\",\\\"resultMsg\\\": \\\"Txn Success\\\"},\\\"mid\\\" : \\\"5456765154\\\",\\\"payer_mid\\\" : \\\"9823765762\\\",\\\"txn_amt\\\" : \\\"982.85\\\",\\\"order_id\\\" : \\\"ICICI1999678\\\",\\\"ref_text\\\" : \\\"qyasdas123glk12aspqx\\\",\\\"comment\\\" : \\\"This is sample request\\\"}}";
		String jsonStr1 = "{\"head\" : {\"clientId\":\"PAYTM-EDC\",\"version\":\"v1\", \"responseTimestamp\":\"1507123312139\",\"signature\":\"<CHECKSUMHASH>\"},\"body\": {\"resultInfo\": {\"resultStatus\": \"SUCCESS\",\"resultCode\": \"01\",\"resultMsg\": \"Txn Success\"},\"mid\" : \"5456765154\",\"payer_mid\" : \"9823765762\",\"txn_amt\" : \"982.85\",\"order_id\" : \"ICICI1999678\",\"ref_text\" : \"qyasdas123glk12aspqx\",\"comment\" : \"This is sample request\"}}";
		JsonNode actualObj = mapper.readTree(jsonStr1);
		 
		Map<String,Object> map = new HashMap<String, Object>();
	    // When
	    JsonNode jsonNode1 = actualObj.get("head");
	    System.out.println(jsonNode1.get("clientId").textValue());
//	    assertThat(jsonNode1.textValue(), equalTo("v1"));
	    ExtendedInfoRequestBean e = new ExtendedInfoRequestBean();
	    Map<String,String> mp = new LinkedHashMap<String, String>();
	    
	    e.setLooperExtendedInfoData(new LooperExtendedInfoData("rmi://12391/alsd", "oiosda"));	    
	    mp = new ObjectMapper().convertValue(e, new TypeReference<Map<String,Object>>() {
	    	
	    	
        });
	    for(Entry<?, ?> entry : mp.entrySet()) {
	    	if(!(entry.getValue() instanceof String)) {
	    		mp.put((String)entry.getKey(), mapper.writeValueAsString(entry.getValue()));
	    	}
	    }
	    boolean b = mp.get("topupAndPay") instanceof String;
	    System.out.println(b);
	    Map<String,String> mp1 = (Map)mp;
		System.out.println(mapper.writeValueAsString(mp1));
		System.out.println(mp1);
		//mp
		Map<String,String> m = new LinkedHashMap<String, String>();
		m.put("t", "false");
		System.out.println(m);
		List<String> ls = new ArrayList<String>();
		List<Object> l1 = new ArrayList<Object>();
		l1.add(Boolean.TRUE);
		ls = (List)l1;
		System.out.println(ls.get(0)  instanceof String);			
		System.out.println(ls);
		
		Map<String, Object> mpj = getMerchantMigrationDetails("");
		System.out.println(mpj);
		for(Entry<String, Object> entry : mpj.entrySet()) {
			if(Objects.nonNull(entry.getValue())) {
				System.out.println(entry.getKey()+" :: "+entry.getValue().getClass().getName());
			}else {
				System.out.println(entry.getKey());
			}
			if(entry.getKey().equals("MERCHANT-PREFERENCE-INFO")) {
				Map<String, Object> m1 = (Map<String, Object>)entry.getValue();
				System.out.println("merchantPreferenceInfos :: " + m1.get("merchantPreferenceInfos").getClass().getName());
			}
		}
		
	}
	
	
	public static Map<String, Object> getMerchantMigrationDetails(String mid) throws JsonParseException, JsonMappingException, IOException{
        String respStr = getMerchantMigrationDetailsJson(mid);
		ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(respStr, new TypeReference<Map<String, Object>>() {
			});
	}
	
	private static String getMerchantMigrationDetailsJson(String mid) {
		return "\n" + 
				"{\"MERCHANT-EMI-INFO\":{\"resultInfo\":{\"resultStatus\":\"F\",\"resultCodeId\":\"00000020\",\"resultCode\":\"TARGET_NOT_FOUND\",\"resultMsg\":\"TARGET_NOT_FOUND\"},\"emiConfigInfos\":null},\"CONTRACT-DETAIL-2019041651051010016800094754554\":{\"resultInfo\":{\"resultStatus\":\"S\",\"resultCodeId\":\"00000000\",\"resultCode\":\"SUCCESS\",\"resultMsg\":\"SUCCESS\"},\"contractBasic\":{\"contractId\":\"2019041651051010016800094754554\",\"merchantId\":\"216820000001484792550\",\"productCode\":\"51051000100000000001\",\"productName\":\"StandardDirectPayAcquiringProd\",\"productVersion\":\"1.0067\",\"signedTime\":1555336962000,\"effectTime\":1555336962000,\"expiryTime\":4070889000000,\"lastModifier\":\"-1\",\"contractStatus\":\"EFFECTIVE\",\"effectType\":\"IMMEDIATE\",\"expiryType\":\"DUE\",\"memo\":\"Standard acquiring product\",\"externalContractId\":null,\"createdTime\":null,\"modifiedTime\":null,\"transId\":null},\"contractTemplate\":null,\"productCondition\":{\"maxAmount\":{\"currency\":\"INR\",\"value\":\"1000000\",\"amount\":\"1000000\"},\"payIntegrationTypes\":[\"CASHIER\",\"API\"],\"orderTimeout\":4320,\"needLogin\":\"LOGIN_OPTIONAL\",\"supportMergeOrder\":false,\"payMethods\":[\"CREDIT_CARD\",\"BALANCE\",\"HYBRID_PAYMENT\"],\"settleStrategy\":\"REALTIME\",\"settleCycle\":null,\"settleCycleStartTime\":null,\"settleAccountType\":\"BALANCE_ACCOUNT\",\"settleMethod\":\"ONLINE_SETTLEMENT\",\"bankAccountType\":null,\"cardToken\":null,\"settleBalanceAccount\":\"20070000000802842557\",\"cardIndexNo\":null,\"settleAfterClearingResult\":false,\"chargebackPayoutAccountSource\":\"SETTLEMENT_BALANCE_ACCOUNT_PAYOUT\",\"chargebackDefaultPayoutAccount\":null,\"supportRefund\":true,\"supportMultiRefund\":true,\"refundExpiryTime\":\"18\",\"pendingOrderRefundSource\":\"SETTLEMENT_BALANCE_ACCOUNT_PAYOUT\",\"defaultPendingPayoutAccount\":null,\"refundPayoutAccounts\":null,\"completedOrderRefundSource\":\"SETTLEMENT_BALANCE_ACCOUNT_PAYOUT\",\"defaultCompletedRefundAccount\":null,\"extendInfo\":\"{\\\"isSupportAddPay\\\":\\\"Y\\\",\\\"isSupportHybridPayment\\\":\\\"Y\\\"}\",\"feeItems\":[{\"feeCalcBasis\":\"TRADING_AMOUNT\",\"chargeTarget\":\"RECEIVER\",\"feeCalcMethod\":\"FULL_CALCULATION\",\"feeSettleMode\":\"REALTIME\",\"feeRefundRule\":\"UNSUPPORTED\",\"payMethodFeeInfos\":[{\"payMethod\":\"HYBRID_PAYMENT\",\"feeRanges\":[{\"feeRate\":0.0,\"fixedFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"minFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"maxFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"lowerFeeValue\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"includeLowerValue\":false,\"upperFeeValue\":{\"currency\":\"INR\",\"value\":\"-1\",\"amount\":\"-1\"},\"includeUpperValue\":true}],\"feeRateFactors\":null,\"invoiceType\":null},{\"payMethod\":\"CREDIT_CARD\",\"feeRanges\":[{\"feeRate\":0.0,\"fixedFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"minFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"maxFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"lowerFeeValue\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"includeLowerValue\":false,\"upperFeeValue\":{\"currency\":\"INR\",\"value\":\"-1\",\"amount\":\"-1\"},\"includeUpperValue\":true}],\"feeRateFactors\":null,\"invoiceType\":null},{\"payMethod\":\"BALANCE\",\"feeRanges\":[{\"feeRate\":0.0,\"fixedFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"minFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"maxFeeAmount\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"lowerFeeValue\":{\"currency\":\"INR\",\"value\":\"0\",\"amount\":\"0\"},\"includeLowerValue\":false,\"upperFeeValue\":{\"currency\":\"INR\",\"value\":\"-1\",\"amount\":\"-1\"},\"includeUpperValue\":true}],\"feeRateFactors\":null,\"invoiceType\":null}],\"chargeMode\":\"INNER_DEDUCT\",\"taxRate\":0.18,\"taxRateInfos\":null,\"chargeCurrency\":\"INR\",\"roundingRule\":\"ROUND_HALF_UP\",\"chargeItemCode\":\"ACQUIRING_SERVICE_FEE\",\"accountRemark\":\"ACQUIRING_SERVICE_FEE\",\"taxCategory\":\"SERVICE_TAX\",\"taxPriceRelationship\":\"TAX_EXCLUDED_IN_PRICE\",\"taxRateCode\":null,\"pricingMethod\":\"EXCLUDE_TAX_AND_PRICE\",\"chargeTargetRules\":null}],\"supportPreCreateOrder\":true,\"orderingMode\":\"API\",\"currency\":\"INR\",\"stagePaymentType\":\"PAYMENT_IN_FULL\",\"acquiringMode\":\"DIRECTPAY\",\"settleCurrency\":\"INR\",\"refundCurrency\":\"INR\",\"timeoutForInactiveOrder\":\"10\",\"refundOptions\":[\"TO_SOURCE\"],\"supportMultipleConfirm\":null,\"confirmTimeout\":null,\"supportExcessConfirm\":null,\"excessProportion\":null,\"allowPrnValidation\":null,\"maxPrnValidRetryAllowCount\":null,\"maxPrnValidRetryAllowTime\":null,\"prnExpiryTime\":null,\"isCancelAllowed\":false,\"maxCancelAllowTime\":1440,\"maxAgreementContractNumber\":null,\"merchantNotifyUrl\":null,\"smsNotifyUserAfterTxnFailure\":null,\"smsNotifyUserAfterTxnSuccess\":null,\"unlinkNeedMerchantAgree\":null,\"supportMerchantInitOTP\":null,\"agreementConfirmTimeoutRule\":null,\"supportAgreementPay\":null},\"contractRelations\":[],\"merchantConfiguration\":null},\"MERCHANT-EXTENDED-INFO\":{\"resultInfo\":{\"resultCode\":\"00000\",\"resultStatus\":\"S\",\"messaage\":\"Success\"},\"merchantId\":\"PayerM48792964723487\",\"extendedInfo\":{\"entityId\":\"9741391\",\"status\":\"ACTIVE\",\"numberOfRetry\":\"0\",\"keySize\":\"16\",\"walletEnabled\":\"false\",\"walletRechargeRnabled\":\"NO_RECHARGE\",\"callbackUrlEnabled\":\"false\",\"sap\":\"\",\"comment\":\"NA\",\"s2sCallbackEnabled\":\"false\",\"businessName\":\"PayerMerchant\",\"signedTime\":\"2019-04-15T00:00:00+05:30\",\"entityKey\":\"19v8Hw5T11YQrK4CKG74G4uYobYNhB7pwc64xKDl8z8=\",\"isDownloaded\":\"F\",\"contactFname\":\"PayerMerchant\",\"contactMname\":\"\",\"contactLname\":\"PayerMerchant\",\"contactMobile\":\"9773730880\",\"eciStatus\":\"ACTIVE\",\"primaryEmail\":\"abhishekvarshney@gmail.com\",\"secondaryEmail\":\"\",\"merchCommPref\":\"0\",\"secondaryFirstname\":\"\",\"secondaryLastname\":\"\",\"secondaryMobileno\":\"\",\"secondaryPhoneno\":\"\",\"invoiceEmail\":\"\",\"panNoBusiness\":\"QALLV0901G\",\"bankAccNo\":\"0123456789\",\"kycStatus\":\"9376503\",\"ifscCode\":\"HDFC0000003\",\"panNoPersonal\":\"\",\"addProofnoPersonal\":\"\",\"idProofnoPersonal\":\"\",\"signatoryName\":\"PayerMerchant\",\"isPeonEnable\":false,\"merchantName\":\"PayerMerchant\",\"custCommPref\":0,\"productCode\":\"51051000100000000001\",\"merchRefCommPref\":0,\"custRefCommPref\":0,\"isOtpThemeEnabled\":0,\"isApiRefundAllowed\":0,\"maxAmountForComplexRefund\":0,\"peonRequestType\":\"DEFAULT\",\"peonServiceName\":\"PeonSentServiceImpl\",\"merchantWebForcedTheme\":\"\",\"merchantWapForcedTheme\":\"\",\"secureStatusEnabled\":0,\"urbanAirshipHash\":\"\",\"urbanAirshipEnabled\":true,\"platformType\":null,\"minPartialRenewalPercentage\":null,\"aggregatorMid\":null,\"blocked\":false,\"additionalEmails\":\"\",\"alipayMid\":null,\"isMerchant\":null,\"merchantLimit\":null,\"userId\":null,\"gstin\":null,\"paymentInvoiceMobile\":null,\"paymentInvoiceEmail\":null,\"ONPAYTM\":false}},\"CONTRACT-AVALABLE\":1,\"MERCHANT-MAPPING-INFO\":{\"paytmId\":\"PayerM48792964723487\",\"alipayId\":\"216820000001484792550\",\"guid\":null,\"ssoId\":null,\"officialName\":\"PayerMerchant\",\"paytmWalletId\":null,\"alipayWalletId\":null,\"merchantType\":null,\"industryTypeId\":345678920,\"enityId\":null},\"MAIL-SENT-TO\":null,\"MERCHANT-PREFERENCE-INFO\":{\"resultInfo\":{\"resultCode\":\"00000\",\"resultStatus\":\"S\",\"messaage\":\"Success\"},\"merchantId\":\"PayerM48792964723487\",\"merchantPreferenceInfos\":[{\"prefType\":\"STORE CARD DETAILS\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"NO\"},{\"prefType\":\"S2S_NOTIFY\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"ADD_MONEY_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"Y\"},{\"prefType\":\"HYBRID_ALLOWED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"Y\"},{\"prefType\":\"AUTO_CREATE_USER\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"UNQREF_REQUEST\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"ENCRYPT_REQUEST_RESPONSE\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"CHECK_ENABLED_LATER\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"CHECKSUM_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"OCP_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"Y\"},{\"prefType\":\"MID_GENERATION\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"CUSTOM\"},{\"prefType\":\"FSS_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"NO\"},{\"prefType\":\"AUTO_REFUND_TO_BANK\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"Y\"},{\"prefType\":\"ConvenienceFee\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"BIN_IN_RESPONSE\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"COMMISSION\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"DISABLED\"},{\"prefType\":\"WalletOnlyMerchant\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"offlineMerchant\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"WithoutConvenienceFee\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"PENDING_TO_SUCCESS_REFUND\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"AUTOMATE_ADD_MONEY_BC_WALLET\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"AUTOMATE_ADD_MONEY_GTFN\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"TOPUP_SUBWALLET\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"REFUND_DISABLE\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"DYNAMIC_QR_CASHIER\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"DYNAMIC_QR_2FA\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"ENHANCED_CASHIER_PAGE_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"IS_MOCK_MERCHANT\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"AUTO_DEBIT\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"ONLINE_SETTLEMENT\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"CANCEL_ALLOWED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"REFUND_SUCCESS_PEON_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"BW_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"Y\"},{\"prefType\":\"PARTIAL_RENEWAL_ALLOWED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"SEND_SETTLEMENT_SMS\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"PG_AUTOLOGIN_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"S2S_CHECKSUM_ENABLED\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"},{\"prefType\":\"prnEnabled\",\"prefStatus\":\"ACTIVE\",\"prefValue\":\"N\"}]},\"MERCHANT-ACQUIRING-INFO\":{\"resultInfo\":{\"resultStatus\":\"S\",\"resultCodeId\":\"00000000\",\"resultCode\":\"SUCCESS\",\"resultMsg\":\"success\"},\"acquiringConfigInfos\":[{\"recordId\":\"120100000002375356551\",\"mcc\":\"Retail\",\"payMethod\":\"CREDIT_CARD\",\"serviceInstId\":\"HDFC\",\"serviceInstName\":\"HDFC Bank\",\"enableStatus\":true,\"merchantId\":\"216820000001484792550\",\"supportRegions\":[\"DOMESTIC\"],\"preference\":null,\"subServiceInstIds\":null,\"fromAoaMerchant\":null}]}}";
	}
}
