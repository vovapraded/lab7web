package org.example.managers;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.common.commands.Command;
import org.common.managers.Collection;
import org.common.utility.Console;
import org.common.utility.InvalidFormatException;
import org.example.utility.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

/**
 * A class for executing commands
 */
public class ExecutorOfCommands {



    private final Collection collection = Collection.getInstance();
    private  final Console console;
    private static final Logger logger = LoggerFactory.getLogger(ExecutorOfCommands.class);


    public ExecutorOfCommands( Console console){
        this.console = console;
    }
    public void executeCommand(ImmutablePair pair) throws InvalidFormatException {
        Command command =(Command) pair.getLeft();
        SocketAddress address = (SocketAddress) pair.getRight();
            command.setConsole(console);
            command.setAddress(address);
            command.execute();
            logger.debug("Команда "+command.getClass().getName()+" выполнена успешно");
    }


//
//
//



//








}






