package com.CourseWorld.CourseWorld.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CourseWorld.CourseWorld.Component.PayDetails;

public interface PaymentRepository extends JpaRepository<PayDetails, Long>{

    PayDetails findByRazorpayOrderId(String razorpayOrderId);

    PayDetails findByRazorpayPaymentId(String razorpayPaymentId);
}
