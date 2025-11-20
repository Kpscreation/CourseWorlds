package com.CourseWorld.CourseWorld.Controller;


import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.CourseWorld.CourseWorld.Component.Enrollment;
import com.CourseWorld.CourseWorld.Component.PayDetails;
import com.CourseWorld.CourseWorld.Service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;

@RestController
public class PaymentController {
	
	@Autowired
	private RazorpayService razorpayService;

	
	@PostMapping("/create-order")
	@ResponseBody
	public ResponseEntity<?> createOrder(@RequestBody PayDetails payDetails,HttpSession session) throws RazorpayException {		
	    System.out.println("Received amount: " + payDetails.getAmount());	    
	    Order order = razorpayService.createOrder(payDetails);	    		   
	    return ResponseEntity.ok(order.toJson().toString()); // Return JSON		
	}
	
    @GetMapping("/payment-success")
    public String paymentSuccess(@RequestParam("payment_id") String paymentId, Model model) {
        model.addAttribute("paymentId", paymentId);
        return "<h1> <b style='color:green;'> success payment</b></h1>"; // This will return `success.html`
    }

  
    @PostMapping("/save")
    public PayDetails savePayment(@RequestBody Map<String, Object> paymentData,@RequestParam int id,HttpSession session) throws RazorpayException {
        // Extract details from request body
        String orderId = (String) paymentData.get("razorpay_order_id");
        String paymentId = (String) paymentData.get("razorpay_payment_id");
        String currency = (String) paymentData.get("currency");
        String status = (String) paymentData.get("status");
        String paymentMethod = razorpayService.fetchPaymentMethod(paymentId);
        Double amount = ((Number) paymentData.get("amount")).doubleValue() / 100; // Convert paise to INR

       
        Object userIdObj = session.getAttribute("userId");       
        int userId = Integer.parseInt(userIdObj.toString()); // Convert to int safely            
                
        
        // Create PayDetails object
        PayDetails payDetails = new PayDetails();
        payDetails.setRazorpayOrderId(orderId);
        payDetails.setRazorpayPaymentId(paymentId);
        payDetails.setAmount(amount);
        payDetails.setCurrency(currency);
        payDetails.setStatus(status);       
        payDetails.setPaymentMethod(paymentMethod);                 
        payDetails.setUser_id(userId);
        payDetails.setCourseId(id);
        System.out.println(payDetails);
        // Save to MySQL
        
        Enrollment enrollment=new Enrollment();
        enrollment.setCourseId(id);
        enrollment.setUser_id(userId);        
        System.out.println(enrollment);
        return razorpayService.savePaymentDetails(payDetails,enrollment);
    }
}
