package org.example.managers;

import org.example.commands.Command;
import org.example.managers.ResponseManager;
import org.example.network.Response;
import org.example.connection.ResponsePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CurrentResponseManager implements ResponseManager {
    private static final Logger logger = LoggerFactory.getLogger(CurrentResponseManager.class);

    private HashMap<Command,Response> responses = new HashMap<>();
    @Override
    public void initResponse(Command command, Response response){
        responses.put(command,response);
    }
    @Override
    public Response getResponse(Command command){
        return  responses.get(command);
    }
    @Override
    public void addToSend(String s, Command command){
        responses.get(command).getMessage().add(s);

    }
    @Override
    public void send(Command command){
        var response = responses.get(command);
        ResponsePublisher.sendResponse(response, response.getAddress());
        logger.debug("Респонс на комманду "+command.getClass().getName()+" отправлен по адрессу "+response.getAddress());

    }
}
