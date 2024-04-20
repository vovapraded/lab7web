package org.example.utility;

import org.common.managers.Collection;
import org.common.managers.DumpManager;
import org.common.utility.Console;
import org.common.utility.InvalidFormatException;
import org.example.connection.ResponsePublisher;
import org.example.connection.UdpServer;
import org.example.managers.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *Main class
 */

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            logger.debug("Сервер запускается");

            Collection collection = Collection.getInstance();
            Console console = new CurrentConsole();
            DumpManager.loadFromFile(collection);
            logger.debug("Коллекция загружена. Содержит " + collection.getHashMap().size() + " элементов");
            ValidatorOfCollection validator = new ValidatorOfCollection(console);
            validator.validateCollection();
            UdpServer udpServer = new UdpServer(new ExecutorOfCommands(console));
            ResponsePublisher.addListener(udpServer);
            while (true) {
                try {
                    udpServer.run();
                }catch (InvalidFormatException e){
                    console.addToSend(e.getMessage(),e.getAddress());
                    console.send(e.getAddress());
                }
                catch (RecieveDataException e) {
                    logger.error(e.getMessage());
                }
            }
        }catch (Exception e){
            logger.error("Ошибка: "+e.getClass()+" сообщение об ошибке: "+e.getMessage()+" причина ошибки: "+ e.getCause());
            e.printStackTrace();
        }

    }
}
