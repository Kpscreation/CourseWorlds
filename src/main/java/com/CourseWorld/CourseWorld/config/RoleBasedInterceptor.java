package com.CourseWorld.CourseWorld.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class RoleBasedInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");

        String requestURI = request.getRequestURI();

        // Redirect to access-denied page if role is incorrect
        if ((requestURI.startsWith("/Admin") && !"Admin".equals(userRole)) ||
            (requestURI.startsWith("/User") && !"User".equals(userRole)) ||
            (requestURI.startsWith("/Institute") && !"Institute".equals(userRole))) {
            
            response.sendRedirect("/access-denied");
            return false; // Prevent further request processing
        }
        return true; // Allow request to continue
    }
}
