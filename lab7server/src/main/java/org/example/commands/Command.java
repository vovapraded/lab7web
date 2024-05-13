package org.example.commands;
import lombok.Getter;
import lombok.Setter;
import org.example.commands.inner.objects.Authorization;
import org.example.commands.inner.objects.LoggerHelper;
import org.example.entity.Ticket;
import org.example.managers.Collection;
import org.example.managers.CreateTicket;
import org.example.managers.ResponseManager;
import org.example.utility.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Interface for the command
 */
@Setter
@Getter
public abstract class Command implements Serializable {
    @Serial
    private static final long serialVersionUID = "Command".hashCode();
    protected LoggerHelper loggerHelper;
    protected Command() {

    }
//   public void   send()  {
//       responseManager.send(this);
//   }

    public abstract void execute();
    public abstract void validate(String arg1);
    public void prepareToSend(boolean ticketArgIsNeeded){
        if (ticketArgIsNeeded ) {
            Validator.validate(stringArg,TypesOfArgs.Long,false);
            CreateTicket creator = new CreateTicket(console);
            var ticket= creator.createTicket(null);
            this.setTicketArg(ticket);
        }

    }
    protected String stringArg=null;
    protected Ticket ticketArg=null;
    private Authorization authorization = null;
    protected static final Collection collection = Collection.getInstance();
    protected  transient    Console console ;
    protected  transient ResponseManager responseManager ;
    public void setConsole (Console console){
        this.console = console;
    }


}
