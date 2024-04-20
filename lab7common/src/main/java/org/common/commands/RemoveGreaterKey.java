package org.common.commands;


import org.common.utility.InvalidFormatException;
import org.common.utility.TypesOfArgs;
import org.common.utility.Validator;

import java.io.Serial;
import java.io.Serializable;

/**
 * The command to delete items with an id greater than the specified number
 */
public class RemoveGreaterKey extends Command implements  Serializable {
    @Serial
    private static final long serialVersionUID = "RemoveGreaterKey".hashCode();



    @Override
    public void execute() {
        Long id = null;
        try {
            ValidateId.validateId(stringArg, false, collection);
        }catch (InvalidFormatException e){
            e.setAddress(getAddress());
            throw e;
        }
        int sizeBefore = collection.getCountOfElements();
            collection.removeGreaterKey(id);
            int sizeAfter = collection.getCountOfElements();
            if (sizeAfter != sizeBefore) {
                console.addToSend("Успешно удалено",getAddress());
            }else {
                console.addToSend("Нет таких элементов",getAddress());
            }
        console.send(getAddress());



    }

    @Override
    public void validate(String arg1) {
        this.stringArg = arg1;
        if (!Validator.validate(stringArg, TypesOfArgs.Long,false) || Long.parseLong(stringArg)<=0){
            throw new InvalidFormatException("Id должен быть числом > 0",getAddress());
        }

    }
}