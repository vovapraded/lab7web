package org.example.dao;

public class FailedTransactionException extends RuntimeException{
    public FailedTransactionException(String message) {
        super(message);
    }

}
