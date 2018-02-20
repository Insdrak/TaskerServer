package com.motivtelecom.conf;

import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertiesMediator {

    private final static Logger logger = Logger.getLogger(PropertiesMediator.class);

    private static Properties getSettings (){
        //System.out.printf(com.motivtelecom.conf.PropertiesMediator.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        try (InputStream stream = new FileInputStream(PropertiesMediator.class.getProtectionDomain().getCodeSource().getLocation().getPath()+ "/config.properties")) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        }
        catch (IOException e) {
            logger.error("Error loading config file",e);
            return null;
        }
    }

    static String getCronSchedule (){
        //noinspection ConstantConditions
        return getSettings().getProperty("PollingTimer");
    }
}
