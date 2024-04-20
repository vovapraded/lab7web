package org.example.utility;

public class InfinityRecursionException extends RuntimeException {
    private String message;
    public InfinityRecursionException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
