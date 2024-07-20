package com.config_server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.config_server.Repo.EsewaPaymentRequestRepository;
import com.config_server.entities.EsewaPaymentRequest;
import com.config_server.service.EsewaPaymentService;

import java.util.HashMap;
import java.util.Map;

@Service
public class EsewaPaymentServiceImpl implements EsewaPaymentService {

    @Value("${esewa.endpoint}")
    private String esewaEndpoint;

    @Value("${esewa.verify.endpoint}")
    private String esewaVerifyEndpoint;

    @Value("${esewa.merchant_id}")
    private String merchantId;


    @Autowired
    private EsewaPaymentRequestRepository paymentRequestRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String initiatePayment(EsewaPaymentRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("amt", request.getAmt());
        params.put("psc", request.getPsc());
        params.put("pdc", request.getPdc());
        params.put("txAmt", request.getTxAmt());
        params.put("tAmt", request.getTAmt());
        params.put("pid", request.getPid());
        params.put("scd", merchantId);
        params.put("su", request.getSu());
        params.put("fu", request.getFu());

        // Logging for debugging
        System.out.println("Initiating payment with params: " + params);

        
        paymentRequestRepository.save(request);
        
        //return restTemplate.postForObject(esewaEndpoint, params, String.class);
   
        try {
            String response = restTemplate.postForObject(esewaEndpoint, params, String.class);
            System.out.println("eSewa response: " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return "Error initiating payment: " + e.getMessage();
        }
    
    }

//    @Override
//    public String verifyPayment(String referenceId, double amount) {
//        String url = esewaVerifyEndpoint + "?amt=" + amount + "&rid=" + referenceId + "&pid=" + PRODUCT_ID + "&scd=" + merchantId;
//        return restTemplate.getForObject(url, String.class);
//    }

	@Override
	public String verifyPayment(String referenceId, String pid, double amount) {
		String url = esewaVerifyEndpoint + "?amt=" + amount + "&rid=" + referenceId + "&pid=" + pid + "&scd=" + merchantId;
       
		 // Logging the verification URL
        System.out.println("Verification URL: " + url);
		
		//return restTemplate.getForObject(url, String.class);
        try {
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("Verification response: " + response);

            if (response.contains("<response_code>Success</response_code>")) {
                return "Payment Success";
            } else {
                return "Payment Verification Failed";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return "Error verifying payment: " + e.getMessage();
        }
    }
}