package com.naher_farhsa.Exception;

public class AppException extends RuntimeException {

    private final ErrorType errorType;

    public AppException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    // Enum to hold different error messages and types
    public enum ErrorType {
        EMAIL_ALREADY_EXISTS("Email already exists"),
        PASSWORD_CANNOT_BE_NULL("Password cannot be null or empty"),
        WALLET_NOT_FOUND("Wallet not found"),
        INVALID_PIN("Invalid PIN."),
        INVALID_PASSWORD("Invalid Password."),
        INVALID_UPDATE_TYPE("Invalid update type."),
        PIN_CANNOT_BE_NULL("PIN cannot be null"),
        INSUFFICIENT_BALANCE("Insufficient wallet balance"),
        TRANSACTION_NOT_FOUND("Transaction not found"),
        USER_NOT_FOUND("User not found"),
        INVALID_OLD_PIN("Invalid old PIN."),
        INVALID_OLD_PASSWORD("Invalid old Password.");


        private final String message;

        ErrorType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

