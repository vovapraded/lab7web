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
            console.addToSend("Коллекция пуста",getAddress());
        } else {
            collection.getHashMap().values().stream()
                    .sorted()
                    .forEach(ticket -> console.addToSend(ticket.toString(),getAddress()) );
        }
        console.send(getAddress());
    }

    @Override
    public void validate(String arg1) {

    }
}
