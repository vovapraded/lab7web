package org.common.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static final InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream("app.properties");
    private static final   Properties properties = new Properties();
    static {
        try (InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getAddress(){
        return properties.getProperty("server.address");
    }
    public static int getPort(){
        System.out.println(properties.getProperty("server.port"));
        return Integer.parseInt(properties.getProperty("server.port"));
    }

}

