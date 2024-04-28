package org.example.managers;

import org.common.utility.ConnectToDatabaseException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateManager {
    private final static Configuration configuration ;


    static {
        configuration = new Configuration();
        configuration.configure();
    }
//    private static final Collection collection = Collection.getInstance();
    public static Configuration getConfiguration() throws ConnectToDatabaseException{
        return configuration;
    }
}

