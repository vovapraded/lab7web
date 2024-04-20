package org.example.utility;

import lombok.Getter;
import org.common.utility.Console;
import org.example.connection.ResponsePublisher;
import org.example.connection.UdpServer;

import java.io.File;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.common.utility.InvalidFormatException;
public class CurrentConsole extends Console {

    private HashMap<SocketAddress, ArrayList<String>> responses = new HashMap<>();


    @Override
    public String getInputFromCommand(int minCountOfArgs,int maxCountOfArgs){
      return null;
    }


    @Override
    public void selectFileScanner(Scanner scanner) {
    }

    @Override
    public void addToSend( String s,SocketAddress address){
        ArrayList<String> existingList = responses.get(address);
        if (existingList == null) {
            existingList = new ArrayList<>();
            responses.put(address,existingList);
        }
        existingList.add(s);
    }
    @Override
    public void send(SocketAddress address){
        var buffer = responses.get(address);
        String[] array = buffer.toArray(new String[0]);
        String result = String.join("\n", array);
        ResponsePublisher.generateResponse(result.getBytes(),address);
        buffer.clear();
    }
}
