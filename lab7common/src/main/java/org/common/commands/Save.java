package org.common.commands;

import org.common.managers.*;


import java.io.Serial;
import java.io.Serializable;

/**
 * The command saves the collection
 */
public class Save extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "Save".hashCode();



    @Override
    public void execute() {
            try {
                DumpManager.saveToFile(collection);
                console.addToSend("Коллекция успешно сохранена",getAddress());
            }
            catch (NullPointerException e){
                console.addToSend("Создайте файл для сохранения",getAddress());
            }
        console.send(getAddress());



    }

    @Override
    public void validate(String arg1) {

    }
}
