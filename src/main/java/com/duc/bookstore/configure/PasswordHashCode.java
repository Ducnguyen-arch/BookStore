package com.duc.bookstore.configure;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashCode {
    public static void main(String[] args) {
        String rawPassword = "admin"; // Mật khẩu bạn muốn đặt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("BCrypt password: " + hashedPassword);
    }
}
