package org.common.commands;

import org.common.utility.InvalidFormatException;
import org.common.utility.TypesOfArgs;
import org.common.utility.Validator;

import java.io.Serial;
import java.io.Serializable;
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
        ticketArg.setCreationDate(new Date());
        collection.updateTicket(ticketArg);
        responseManager.addToSend("Билет успешно введён",this);
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
