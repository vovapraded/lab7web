package org.common.commands;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

/**
 * The command to display the collection in reverse order
 */
public class PrintDescending extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "PrintDescending".hashCode();


    @Override
    public void execute() {
        if (collection.getHashMap().isEmpty()){
            console.addToSend("Коллекция пуста",getAddress());
        }
        else{
            collection.getHashMap().values().stream()
                    .sorted(Comparator.reverseOrder())
                    .forEach(ticket -> console.addToSend(ticket.toString() ,getAddress()));
        }
        console.send(getAddress());

    }

    @Override
    public void validate(String arg1) {

    }
}