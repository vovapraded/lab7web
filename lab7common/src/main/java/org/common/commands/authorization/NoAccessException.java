package org.common.commands.authorization;


import lombok.Getter;
import lombok.Setter;
import org.common.commands.Command;

public class NoAccessException extends RuntimeException{

    public NoAccessException(String message) {
        super(message);
    }

}
