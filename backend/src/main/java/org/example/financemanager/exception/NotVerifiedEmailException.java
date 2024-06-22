package org.example.financemanager.exception;

public class NotVerifiedEmailException extends RuntimeException{
    public NotVerifiedEmailException(String email) {
        super("Your email (" + email +") is not confirmed.");
    }
}
