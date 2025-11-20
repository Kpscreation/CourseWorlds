package com.CourseWorld.CourseWorld.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.CourseWorld.CourseWorld.Component.Enrollment;
import com.CourseWorld.CourseWorld.Component.PayDetails;
import com.CourseWorld.CourseWorld.Repository.EnrollmentRepository;
import com.CourseWorld.CourseWorld.Repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class RazorpayService {
	
	@Value("${rzp_key_id}")
	private String apiKey;
	
	@Value("${rzp_key_secret}")
	private String apiSecret;
	
	@Value("${rzp_currency}")
	private String currency;
	
	@Autowired	
    private PaymentRepository payDetailsRepository;
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	

	public Order createOrder(PayDetails paydetails) throws RazorpayException {
	    if (paydetails.getAmount() == null || paydetails.getAmount() <= 0) {
	        throw new IllegalArgumentException("Invalid amount");
	    }

	    RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
	    JSONObject orderRequest = new JSONObject();
	    orderRequest.put("amount", paydetails.getAmount() * 100);
	    orderRequest.put("currency", currency);
	    orderRequest.put("receipt", "receipt_" + System.currentTimeMillis());

	    Order order = razorpayClient.Orders.create(orderRequest);

	    return order;
	}
	
	public PayDetails savePaymentDetails(PayDetails payDetails,Enrollment enrollment) {
		enrollmentRepository.save(enrollment);
        return payDetailsRepository.save(payDetails);
	}

	public String fetchPaymentMethod(String paymentId) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

        // Fetch payment details from Razorpay API
        Payment payment = razorpayClient.Payments.fetch(paymentId);

        // Extract payment method
        return payment.get("method").toString();
    }
}
