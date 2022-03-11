package fr.adouche.movie;

import org.slf4j.Logger;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
@Startup
public class SystemPropertiesLoader {

    private static final Logger LOGGER = getLogger(SystemPropertiesLoader.class);
    @PostConstruct
    void initialise() {

        Properties mySystemProperties = new Properties();
        try {
            mySystemProperties.load(SystemPropertiesLoader.class.getClassLoader().getResourceAsStream("hazelcastXXE.properties"));
            System.getProperties().putAll(mySystemProperties);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
