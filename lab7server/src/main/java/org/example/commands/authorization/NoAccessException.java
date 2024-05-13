package org.example.commands.authorization;


import lombok.Getter;
import lombok.Setter;
import org.example.commands.Command;

public class NoAccessException extends RuntimeException{

    public NoAccessException(String message) {
        super(message);
    }

}
