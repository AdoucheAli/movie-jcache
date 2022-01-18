package fr.adouche.movie;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class SystemPropertiesLoader {

    @PostConstruct
    void initialise() {

        Properties mySystemProperties = new Properties();
        try {
            mySystemProperties.load(SystemPropertiesLoader.class.getClassLoader().getResourceAsStream("hazelcastXXE.properties"));
            System.getProperties().putAll(mySystemProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
