package org.example;

import org.example.authorization.AuthorizationManager;
import org.example.dao.UserDao;
import org.example.managers.*;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;


/**
 *Main class
 */
@SpringBootApplication
@ConfigurationPropertiesScan


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static ApplicationContext context;

    public static ApplicationContext getContext(){
        return context;
    }
    private static void run(){
        context=SpringApplication.run(Main.class);
    }
    public static void main(String[] args)  {
        run();
            try {


                Flyway flyway = Flyway.configure().loadDefaultConfigurationFiles().load();
                System.out.println(flyway.info());
                flyway.migrate();
                logger.debug("Сервер запускается");
                CurrentResponseManager responseManager = new CurrentResponseManager();
                var userDao=context.getBean(UserDao.class);
                AuthorizationManager.setUserDao(userDao);
//                System.out.println(
//                );



//                UdpServer udpServer = new UdpServer(responseManager);

//                ResponsePublisher.addListener(udpServer);
            }catch (Exception e){
                logger.error("Ошибка: "+e.getClass()+" сообщение об ошибке: "+e.getMessage()+" причина ошибки: "+ e.getCause());
                e.printStackTrace();
            }


    }
}
