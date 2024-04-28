package org.common.utility;

import lombok.Getter;
import lombok.Setter;
import org.common.commands.Command;

import java.net.SocketAddress;

public class InvalidFormatException extends RuntimeException {
    @Getter @Setter
private  Command command;
    public InvalidFormatException(String message, Command command) {
        super(message);
        this.command = command;
    }
    public InvalidFormatException(String message) {
        super(message);
    }

}
