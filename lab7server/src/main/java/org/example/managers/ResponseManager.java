package org.example.managers;

import org.example.commands.Command;
import org.example.network.Response;

public interface ResponseManager {

    void initResponse(Command command, Response response);

    Response getResponse(Command command);

    void addToSend(String s, Command command);

    void send(Command command);
}
