package org.common.commands;

import java.io.Serial;
import java.io.Serializable;

/**
 * The clear collection command
 */
public class Clear extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "Clear".hashCode();


    @Override
    public void execute() {
        collection.clearCollection();
        responseManager.addToSend("Коллекция очищена",this);
        responseManager.send(this);
    }

    @Override
    public void validate(String arg1) {
    }
}
