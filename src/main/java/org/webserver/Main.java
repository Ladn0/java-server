package org.webserver;


import org.webserver.config.Configuration;
import org.webserver.config.ConfigurationManager;
import org.webserver.httpcore.HttpMainThread;

import java.io.IOException;


public class Main {
    public static void main(String[] args){
        System.out.println("Server is starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        try {
            HttpMainThread httpMainThread = new HttpMainThread(conf.getPort(), conf.getWebroot());
            httpMainThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

