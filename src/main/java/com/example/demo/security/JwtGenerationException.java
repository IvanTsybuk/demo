package com.example.demo.security;

public class JwtGenerationException extends RuntimeException {
    public JwtGenerationException() {
        super();
    }

    public JwtGenerationException(String message) {
        super(message);
    }

    public JwtGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
