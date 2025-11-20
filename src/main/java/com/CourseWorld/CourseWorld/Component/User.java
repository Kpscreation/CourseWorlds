package com.CourseWorld.CourseWorld.Component;
	
import java.time.*;
	
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
	
	@Entity
	@Table(name="user")
	public class User {
		public enum Role{
			User,
			Institute,
			Admin
		}
		
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "user_id", nullable = false)
	    private long user_id;
	
	    @Column(name = "name", nullable = false)
	    private String name;
	
	    @Column(name = "email", nullable = false,unique = true)
	    private String email;
	
	    @Column(name = "password", nullable = false)
	    private String password;
	
	    @Column(name = "phone", nullable = false)
	    private long phone;
	
	    @Column(name = "address")
	    private String address;
	
	    @Column(name = "img")
	    private String img;
	    
	    @Column(name = "role", nullable = false)
	    private Role role;
	    
	    @Column(name="enabled")
	    private boolean enabled;
	    
	    @Column(name = "admin_verified", nullable = false)
	    private boolean adminVerified = false;  // Use Boolean instead of boolea
	  
		@Column(name = "created_at")
	    private LocalDateTime created_at;
	
	    @Column(name = "updated_at")
	    private LocalDateTime updated_at;
	
	    @Column(name = "deleted_at")
	    private LocalDateTime deleted_at;
		
		public User() {
			
		}
	
		public User(long user_id, String name, String email, String password, long phone, String address, String img ,Role role,
				boolean enabled, boolean adminVerified, LocalDateTime created_at, LocalDateTime updated_at,
				LocalDateTime deleted_at) {
			this.user_id = user_id;
			this.name = name;
			this.email = email;
			this.img = img;
			this.password = password;
			this.phone = phone;
			this.address = address;
			this.role = role;
			this.enabled = enabled;
			this.adminVerified = adminVerified;
			this.created_at = created_at;
			this.updated_at = updated_at;
			this.deleted_at = deleted_at;
		}
	
		public long getUser_id() {
			return user_id;
		}
	
		public void setUser_id(long user_id) {
			this.user_id = user_id;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public String getImg() {
			return img;
		}
	
		public void setImg(String img) {
			this.img = img;
		}
	
		
		public String getEmail() {
			return email;
		}
	
		public void setEmail(String email) {
			this.email = email;
		}
	
		public String getPassword() {
			return password;
		}
	
		public void setPassword(String password) {
			this.password = password;
		}
	
		public long getPhone() {
			return phone;
		}
	
		public void setPhone(long phone) {
			this.phone = phone;
		}
	
		public String getAddress() {
			return address;
		}
	
		public void setAddress(String address) {
			this.address = address;
		}
	
		public Role getRole() {
			return role;
		}
	
		public void setRole(Role role) {
			this.role = role;
		}
	
		public boolean isEnabled() {
			return enabled;
		}
	
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}		
		
		public boolean isAdminVerified() {
			return adminVerified;
		}
	
		public void setAdminVerified(boolean adminVerified) {
			this.adminVerified = adminVerified;
		}
	
		public boolean getAdminVerified() {
			return adminVerified;
		}
	
		public LocalDateTime getCreated_at() {
			return created_at;
		}
	
		public void setCreated_at(LocalDateTime created_at) {
			this.created_at = created_at;
		}
	
		public LocalDateTime getUpdated_at() {
			return updated_at;
		}
	
		public void setUpdated_at(LocalDateTime updated_at) {
			this.updated_at = updated_at;
		}
	
		public LocalDateTime getDeleted_at() {
			return deleted_at;
		}
	
		public void setDeleted_at(LocalDateTime deleted_at) {
			this.deleted_at = deleted_at;
		}

		@Override
		public String toString() {
			return "User [user_id=" + user_id + ", name=" + name + ", img=" + img + ", email=" + email + ", password=" + password
					+ ", phone=" + phone + ", address=" + address + ", role=" + role + ", enabled=" + enabled
					+ ", adminVerified=" + adminVerified + ", created_at=" + created_at + ", updated_at=" + updated_at
					+ ", deleted_at=" + deleted_at + "]";
		}
		
		
	}
