package org.example.utility;

import java.awt.event.FocusEvent;

public class ConnectToDatabaseException extends RuntimeException {
    public ConnectToDatabaseException(String message, Throwable cause) {
        super(message,cause);
    }


}
