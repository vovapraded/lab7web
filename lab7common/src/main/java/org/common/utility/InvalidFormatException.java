package org.common.utility;

import lombok.Getter;
import lombok.Setter;

import java.net.SocketAddress;

public class InvalidFormatException extends RuntimeException {
    @Getter @Setter
private  SocketAddress address;
    public InvalidFormatException(String message, SocketAddress address) {
        super(message);
        this.address = address;
    }
    public InvalidFormatException(String message) {
        super(message);
    }

}
