package org.example.utility;

import org.example.connection.PacketReceiver;
import org.example.dao.TicketDao;
import org.common.managers.Collection;
import org.example.dao.UserDao;
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
    public static void main(String[] args)  {

            try {
                UserDao userDao = new UserDao();
                userDao.getPasswordAndSaltByLogin("d");
                logger.debug("Сервер запускается");
                CurrentResponseManager responseManager = new CurrentResponseManager();
                TicketDao dao = new TicketDao();
                Collection collection = Collection.getInstance();
                collection.setTicketDao(dao);
                collection.addHashMap(dao.loadCollection());
                logger.debug("Коллекция загружена. Содержит " + collection.getHashMap().size() + " элементов");



                UdpServer udpServer = new UdpServer(responseManager);

                ResponsePublisher.addListener(udpServer);
            }catch (Exception e){
                logger.error("Ошибка: "+e.getClass()+" сообщение об ошибке: "+e.getMessage()+" причина ошибки: "+ e.getCause());
                e.printStackTrace();
            }


    }
}
