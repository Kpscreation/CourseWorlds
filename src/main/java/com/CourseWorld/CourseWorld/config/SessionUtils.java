package com.CourseWorld.CourseWorld.config;

import jakarta.servlet.http.HttpSession;

public class SessionUtils {
    public static String getUserRole(HttpSession session) {
        return (String) session.getAttribute("userRole");
    }
}
