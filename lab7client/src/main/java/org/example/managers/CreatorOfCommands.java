package org.example.managers;

import org.common.commands.*;
import org.common.commands.authorization.AuthorizationCommand;
import org.common.commands.authorization.Login;
import org.common.commands.authorization.Register;
import org.common.utility.*;
import org.common.managers.Collection;
import org.example.authorization.AuthorizationManager;
import org.example.authorization.NoAuthorizationException;
import org.example.commands.*;
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
        commands.put("\\",new Empty());
        commands.put("whoami",new WhoAmI());
    }
    public Command createCommand(String cmd, String arg1) throws NoAuthorizationException {
        if (commands.containsKey(cmd)){
            int countOfGivenArgs = arg1.isEmpty() ? 0 : 1;
            int countArgs;
            try {
                 countArgs = Commands.valueOf(cmd).getCountArgs();
            }catch (IllegalArgumentException e){
                 countArgs = 0;
            }
            if (countOfGivenArgs != countArgs){
                throw new InvalidFormatException("Неверное число аргументов");
            }

            var command=commands.get(cmd);
            command.setConsole(currentConsole);
            if (command instanceof ClientCommand){
                command.execute();
                return null;
            }
            AuthorizationManager.prepareCommand(command);
            command.validate(arg1);
            command.prepareToSend(Commands.valueOf(cmd).isTicketArgIsNeeded());


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






