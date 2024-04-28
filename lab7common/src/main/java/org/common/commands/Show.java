package org.common.commands;

import java.io.Serial;
import java.io.Serializable;

/**
 * The command outputs a collection
 */
public class Show extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "Show".hashCode();




    @Override
    public void execute() {
        if (collection.getHashMap().isEmpty()) {
            responseManager.addToSend("Коллекция пуста",this);
        } else {
            collection.getHashMap().values().stream()
                    .sorted()
                    .forEach(ticket -> responseManager.addToSend(ticket.toString(),this) );
        }
        responseManager.send(this);
    }

    @Override
    public void validate(String arg1) {

    }
}
