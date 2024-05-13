package org.example.commands;

import org.example.commands.Command;

import java.io.Serial;
import java.io.Serializable;

/**
 * The shutdown command
 */
public class Exit extends Command implements Serializable,ClientCommand {
    @Serial
    private static final long serialVersionUID = "Exit".hashCode();


    @Override
    public void execute() {
        console.print("Завершение работы");
        System.exit(0);
    }

    @Override
    public void validate(String arg1) {
    }
}
