package com.spring.billing_software.exception;


import org.springframework.core.io.Resource;

public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }

}
