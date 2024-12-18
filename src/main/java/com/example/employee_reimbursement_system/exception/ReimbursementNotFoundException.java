package com.example.employee_reimbursement_system.exception;

public class ReimbursementNotFoundException extends RuntimeException {
    public ReimbursementNotFoundException(String message) {
        super(message);
    }
}
