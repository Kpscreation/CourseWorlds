package com.CourseWorld.CourseWorld.Component;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class PayDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	private int user_id;
	 
	
	private int courseId;

    @Column(name = "razorpay_order_id", nullable = false)
    private String razorpayOrderId;

    @Column(name = "razorpay_payment_id", nullable = false)
    private String razorpayPaymentId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "payment_method")
    private String paymentMethod;

    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = null;

    // Getters and Setters

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

	public PayDetails() {
	}

	public PayDetails(Long id, int user_id, int courseId, String razorpayOrderId, String razorpayPaymentId,
			Double amount, String currency, String status, String paymentMethod, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.user_id = user_id;
		this.courseId = courseId;
		this.razorpayOrderId = razorpayOrderId;
		this.razorpayPaymentId = razorpayPaymentId;
		this.amount = amount;
		this.currency = currency;
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "PayDetails [id=" + id + ", user_id=" + user_id + ", courseId=" + courseId + ", razorpayOrderId="
				+ razorpayOrderId + ", razorpayPaymentId=" + razorpayPaymentId + ", amount=" + amount + ", currency="
				+ currency + ", status=" + status + ", paymentMethod=" + paymentMethod + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
}
