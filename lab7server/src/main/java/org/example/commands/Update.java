package org.example.commands;

import org.example.utility.InvalidFormatException;
import org.example.utility.TypesOfArgs;
import org.example.utility.Validator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * The command to update the ticket
 */
public class Update extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "Update".hashCode();



    public void execute(){
        var idStr = stringArg;
        Long id = null;
        try {
            id = ValidateId.validateId(idStr, false, collection);
        }catch (InvalidFormatException e){
            e.setCommand(this);
            throw e;
        }
        ticketArg.setId(id);
        ticketArg.setCreatedBy(getAuthorization().getLogin());
        ticketArg.setCreationDate(LocalDate.now());
        collection.updateTicket(ticketArg, getAuthorization().getLogin());
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
