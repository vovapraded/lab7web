package org.common.commands;

import java.io.Serial;
import java.io.Serializable;


/**
 * The command to display the average price
 */
//sa
public class AverageOfPrice extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "AverageOfPrice".hashCode();


    @Override
    public void execute() {
        var average = collection.getAveragePrice();
        if (average.isEmpty()) {
            console.addToSend("Коллекция пуста", getAddress());
        } else {
            console.addToSend("Средняя цена " + average.getAsDouble(),getAddress());
        }
        console.send(getAddress());
    }

    @Override
    public void validate(String arg1) {
    }
}