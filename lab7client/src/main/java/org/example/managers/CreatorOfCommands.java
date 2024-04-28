package org.example.managers;

import org.common.commands.*;
import org.common.commands.authorization.AuthorizationCommand;
import org.common.commands.authorization.Login;
import org.common.commands.authorization.Register;
import org.common.utility.*;
import org.common.managers.Collection;
import org.example.authorization.AuthorizationManager;
import org.example.authorization.NoAuthorizationException;
import org.example.utility.CurrentConsole;
import org.common.utility.InvalidFormatException;


import java.util.HashMap;

/**
 * A class for executing commands
 */
public class CreatorOfCommands {
    private final Collection collection = Collection.getInstance();
    private final CurrentConsole currentConsole = CurrentConsole.getInstance();
    private  HashMap<String, Command> commands = new HashMap<String, Command>();

    public CreatorOfCommands(){
        commands.put("insert",new Insert());
        commands.put("help",new Help());
        commands.put("remove_key",new RemoveKey());
        commands.put("update",new Update());
        commands.put("show",new Show());
        commands.put("exit",new Exit());
        commands.put("clear",new Clear());
        commands.put("info",new Info());
        commands.put("login",new Login());
        commands.put("register",new Register());

        commands.put("remove_greater",new RemoveGreater());
        commands.put("remove_greater_key", new RemoveGreaterKey());
        commands.put("print_descending", new PrintDescending());
        commands.put("average_of_price",new AverageOfPrice());
        commands.put("execute_script",new ExecuteScript());
        commands.put("replace_if_greater",new ReplaceIfGreater());
        commands.put("filter_less_than_venue",new FilterLessThanVenue());
    }
    public Command createCommand(String cmd, String arg1) throws NoAuthorizationException {
        if (commands.containsKey(cmd)){
            int a = arg1.isEmpty() ? 0 : 1;
            try {
            if (a!= Commands.valueOf(cmd).getCountArgs()){
                throw new InvalidFormatException("Неверное число аргументов");
            }
            }catch (IllegalArgumentException e){
                throw new RuntimeException("Нет такой команды");
            }
            var command=commands.get(cmd);
            command.setConsole(currentConsole);
            AuthorizationManager.prepareCommand(command);
            command.validate(arg1);
            command.prepareToSend(Commands.valueOf(cmd).isTicketArgIsNeeded());


            if (cmd.equals("execute_script") || cmd.equals("exit") ){
                command.execute();
                return null;
            }
            if (!(command instanceof AuthorizationCommand) && !AuthorizationManager.checkAuth()){
                throw new NoAuthorizationException("Вы не авторизованы. Введите login или register");
            }
            return command;
        }
        else {
            throw new InvalidFormatException("Нет такой команды");
        }
    }


//
//
//



//








}






