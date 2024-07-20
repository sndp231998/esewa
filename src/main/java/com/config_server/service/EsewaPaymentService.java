package com.config_server.service;

import com.config_server.entities.EsewaPaymentRequest;

public interface EsewaPaymentService {
	 String initiatePayment(EsewaPaymentRequest request);
	    String verifyPayment(String referenceId, String pid, double amount);
}