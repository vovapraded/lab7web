package org.example.commands;


import org.example.utility.InvalidFormatException;
import org.example.utility.TypesOfArgs;
import org.example.utility.Validator;

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
            id = ValidateId.validateId(stringArg, false, collection);
        }catch (InvalidFormatException e){
            e.setCommand(this);
            throw e;
        }
        long sizeBefore = collection.getCountOfElements();
            collection.removeGreaterKey(id,getAuthorization().getLogin());
            long sizeAfter = collection.getCountOfElements();
            if (sizeAfter != sizeBefore) {
                responseManager.addToSend("Успешно удалено",this);
            }else {
                responseManager.addToSend("Нет таких элементов",this);
            }
        loggerHelper.debug("Команда "+this.getClass().getName()+"от адресса "+responseManager.getResponse(this).getAddress() +" выполнена");
        responseManager.send(this);



    }

    @Override
    public void validate(String arg1) {
        this.stringArg = arg1;
        if (!Validator.validate(stringArg, TypesOfArgs.Long,false) || Long.parseLong(stringArg)<=0){
            throw new InvalidFormatException("Id должен быть числом > 0",this);
        }

    }
}