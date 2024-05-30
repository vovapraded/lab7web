package org.example.dao;

public class FailedTransactionException extends RuntimeException{

    public FailedTransactionException(String message,Throwable cause) {

        super(message,cause);
    }

}
