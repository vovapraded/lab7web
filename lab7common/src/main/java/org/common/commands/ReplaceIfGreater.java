package org.common.commands;

import org.common.dto.Ticket;
import org.common.utility.*;

import java.io.Serial;
import java.io.Serializable;


/**
 * The command to replace the item price if it is higher
 */
public class ReplaceIfGreater extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "ReplaceIfGreater".hashCode();


    public void execute(){
        var idStr = stringArg;
        try {
            ValidateId.validateId(idStr, false, collection);
        }catch (InvalidFormatException e){
            e.setAddress(getAddress());
            throw e;
        }
        Long id = Long.parseLong(idStr);
        Ticket oldTicket = collection.getElement(id);
        System.out.println(oldTicket);
        Ticket newTicket = ticketArg ;
        System.out.println(newTicket);

        if (newTicket.compareTo(oldTicket)>0){
            console.addToSend("Операция прошла успешно. Замена произошла",getAddress());
            collection.removeElement(id);
            newTicket.setId(id);
            collection.insertElement(newTicket);
        }
        else {
            console.addToSend("Операция прошла успешно. Замена не произошла",getAddress());
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
