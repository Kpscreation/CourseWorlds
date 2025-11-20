package com.CourseWorld.CourseWorld.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.CourseWorld.CourseWorld.Component.PasswordResetToken;
import com.CourseWorld.CourseWorld.Component.User;
import com.CourseWorld.CourseWorld.Component.VerificationToken;
import com.CourseWorld.CourseWorld.Repository.PasswordResetTokenRepository;
import com.CourseWorld.CourseWorld.Repository.UserRepository;
import com.CourseWorld.CourseWorld.Repository.VerificationTokenRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;

    public void registerUser(User user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated_at(LocalDateTime.now());
        user.setEnabled(false);
        if (user.getRole() == User.Role.Institute) {
            user.setAdminVerified(false); // Institutes need admin verification
        } else {
            user.setAdminVerified(true); // Users are auto-verified
        }

        // Save the user to the database
        userRepository.save(user);

        // Generate and save email verification token
             
    }

    public User authenticate(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if email is verified
        if (!user.isEnabled()) {
            throw new RuntimeException("Please verify your email first.");
        }

        // Check if the account is approved by Admin (for both Users and Institutes)
        if (!user.isAdminVerified()) {
            throw new RuntimeException("Your account needs admin approval before login.");
        }

        // Check if password is correct
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private void sendVerificationEmail(User user, String token) {
        String verificationUrl = "http://localhost:9090/verify?token=" + token;
        String subject = "Verify Your Email";
        String body = "Click the link to verify your account: " + verificationUrl;
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    public void sendPasswordResetEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete any existing password reset tokens for the user
        passwordResetTokenRepository.deleteByUser(user);

        // Generate new token
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken resetToken = new PasswordResetToken(token, user, expiryDate);
        passwordResetTokenRepository.save(resetToken);

        // Send reset link via email
        String resetLink = "http://localhost:9090/password-reset?token=" + token;
        emailService.sendEmail(user.getEmail(), "Reset Your Password",
                "Click the link to reset your password: " + resetLink);
    }

    public boolean verifyUser(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElse(null);

        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false; // Token is invalid or expired
        }

        // Enable user and delete token
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
        return true;
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired. Please request a new one.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete the token after successful password reset
        passwordResetTokenRepository.delete(resetToken);
    }

    public List<User> getAllInstitutes() {
        return userRepository.findByRole(User.Role.Institute);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findByRole(User.Role.User); // Fetch users with role "User"
    }
    
    public String verifyInstitute(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (user.isAdminVerified()) {
                return "Institute is already verified!";
            }
            
            user.setAdminVerified(true);
            userRepository.save(user);
            return "Institute verified successfully!";
        }
        
        return "Institute not found!";
    }
}
