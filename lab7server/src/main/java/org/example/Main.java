package org.example;

import org.example.authorization.AuthorizationManager;
import org.example.dao.TicketDao;
import org.example.managers.Collection;
import org.example.dao.UserDao;
import org.example.connection.ResponsePublisher;
import org.example.connection.UdpServer;
import org.example.managers.*;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


/**
 *Main class
 */
@SpringBootApplication
@ConfigurationPropertiesScan


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args)  {
        var context = SpringApplication.run(Main.class, args);
            try {


                Flyway flyway = Flyway.configure().loadDefaultConfigurationFiles().load();
                System.out.println(flyway.info());
                flyway.migrate();
                logger.debug("Сервер запускается");
                CurrentResponseManager responseManager = new CurrentResponseManager();
                HibernateManager hibernateManager = new HibernateManager();
                TicketDao ticketDao = new TicketDao(hibernateManager);
                UserDao userDao = new UserDao(hibernateManager);
                AuthorizationManager.setUserDao(userDao);
//                System.out.println(
//                        context.getBean(TicketDao.class)
//                );

                Collection collection = Collection.getInstance();
                collection.setTicketDao(ticketDao);
                collection.addHashMap(ticketDao.loadCollection());
                logger.debug("Коллекция загружена. Содержит " + collection.getHashMap().size() + " элементов");


                UdpServer udpServer = new UdpServer(responseManager);

                ResponsePublisher.addListener(udpServer);
            }catch (Exception e){
                logger.error("Ошибка: "+e.getClass()+" сообщение об ошибке: "+e.getMessage()+" причина ошибки: "+ e.getCause());
                e.printStackTrace();
            }


    }
}
