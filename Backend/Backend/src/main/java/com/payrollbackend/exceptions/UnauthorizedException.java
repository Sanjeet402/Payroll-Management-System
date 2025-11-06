package com.payrollbackend.exceptions;

/**
 * Exception thrown when a user is not authorized to perform an action.
 * This includes authentication failures and insufficient permissions.
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
