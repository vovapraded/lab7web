package org.example.utility;

import java.io.IOException;

import org.example.connection.UdpClient;
import org.example.managers.*;
import org.common.utility.*;
/**
 *Main class
 */

public class Main {

    public static void main(String[] args) throws IOException {
        UdpClient udpClient = new UdpClient();
        CurrentConsole currentConsole = CurrentConsole.getInstance();
        CreatorOfCommands creator = new CreatorOfCommands();
        ParseInput parseInput = new ParseInput();
        while (true) {
            String s = currentConsole.getInput();
            if (s.equals("\\")) {
                try {
                    currentConsole.print(udpClient.getResponse(true).trim());
                }catch (NoResponseException e){
                    currentConsole.print(e.getMessage());
                }
                continue;
            }
            if (!s.isEmpty()) {
                try {
                    parseInput.parseInput(s);
                    String cmd = parseInput.getArg1();
                    String arg2 = parseInput.getArg2();
                    if (parseInput.getArg3Exist() == 1) {
                        throw new InvalidFormatException("Слишком много аргументов");
                    }
                   var command = creator.createCommand(cmd, arg2);
                   if (command==null){
                       continue;
                   }
                    try {
                        udpClient.sendCommand(command);
                        currentConsole.print(udpClient.getResponse(false).trim());
                    }catch (NoResponseException e){
                        currentConsole.print(e.getMessage());
                    }

                } catch (InvalidFormatException e) {
                    currentConsole.print(e.getMessage());
                }
            }

        }
    }
}