package com.config_server.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import com.config_server.service.EsewaPaymentService;

@Controller
@RequestMapping("/esewa")
public class EsewaPaymentController {

	@Autowired
	EsewaPaymentService esewaPaymentService;
	
    @Value("${esewa.endpoint}")
    private String esewaEndpoint;

    @Value("${esewa.merchant_id}")
    private String merchantId;

    
    @GetMapping("/payment")
    public String showPaymentPage() {
    	
        return "payment";
    }
    
    @PostMapping("/pay")
    public RedirectView initiatePayment(@RequestParam String amt, @RequestParam String psc, 
                                        @RequestParam String pdc, @RequestParam String txAmt, 
                                        @RequestParam String tAmt, @RequestParam String pid, 
                                        @RequestParam String su, @RequestParam String fu, Model model) {
      
    	// Logging parameters received from the form
        System.out.println("Payment Request Params:");
        System.out.println("amt: " + amt);
        System.out.println("psc: " + psc);
        System.out.println("pdc: " + pdc);
        System.out.println("txAmt: " + txAmt);
        System.out.println("tAmt: " + tAmt);
        System.out.println("pid: " + pid);
        System.out.println("su: " + su);
        System.out.println("fu: " + fu);

    	
    	
    	String paymentUrl = esewaEndpoint + "?amt=" + amt + "&psc=" + psc + "&pdc=" + pdc +
                            "&txAmt=" + txAmt + "&tAmt=" + tAmt + "&pid=" + pid + "&scd=" + merchantId +
                            "&su=" + su + "&fu=" + fu;
        return new RedirectView(paymentUrl);
    }

    @GetMapping("/success")
    @ResponseBody
    public String paymentSuccess(@RequestParam String oid, @RequestParam String amt, 
                                 @RequestParam String refId) {
    	
    	
    	 // Logging parameters received from eSewa
        System.out.println("Payment Success Params:");
        System.out.println("oid: " + oid);
        System.out.println("amt: " + amt);
        System.out.println("refId: " + refId);
        
        
        String verificationResponse = esewaPaymentService.verifyPayment(refId, oid, Double.parseDouble(amt));

        if (verificationResponse.contains("<response_code>Success</response_code>")) {
            return "Payment Success. Reference ID: " + refId;
        } else {
            return "Payment Verification Failed";
        }
    }

    @GetMapping("/failure")
    @ResponseBody
    public String paymentFailure() {
        return "Payment Failed";
    }
}