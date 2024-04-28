package org.common.managers;

import org.common.commands.Command;
import org.common.network.Response;

import java.net.SocketAddress;

public interface ResponseManager {

    void initResponse(Command command, Response response);

    Response getResponse(Command command);

    void addToSend(String s, Command command);

    void send(Command command);
}
