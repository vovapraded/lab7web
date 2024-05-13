package org.example.managers;

import org.example.utility.ConnectToDatabaseException;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class HibernateManager {
    private final  Configuration configuration ;


     {
        configuration = new Configuration();
        configuration.configure();
    }
//    private static final Collection collection = Collection.getInstance();
    public  Configuration getConfiguration() throws ConnectToDatabaseException{
        return configuration;
    }
}

