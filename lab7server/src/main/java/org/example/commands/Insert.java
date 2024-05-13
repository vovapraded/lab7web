package org.example.commands;


import org.example.utility.InvalidFormatException;
import org.example.utility.TypesOfArgs;
import org.example.utility.Validator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Add item command
 */
public class Insert extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "Insert".hashCode();


    @Override
    public void execute() {
        var idStr = stringArg;
        Long id = null;
        try {
            id = ValidateId.validateId(idStr, true, collection);
        }catch (InvalidFormatException e){
            e.setCommand(this);
            throw e;
        }

        ticketArg.setId(id);
        ticketArg.setCreatedBy(getAuthorization().getLogin());
        ticketArg.setCreationDate(new Date());
        collection.insertElement(ticketArg);
        responseManager.addToSend("Билет успешно введён",this);
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