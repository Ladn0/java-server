package org.webserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager(){

    }

    public static ConfigurationManager getInstance(){
        if (myConfigurationManager == null){
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    public void loadConfigurationFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        try{
            while ((i = fileReader.read()) != -1) {
                stringBuffer.append((char) i);
            }
        }catch(IOException e){
            throw new HttpConfigurationException(e);
        }
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(stringBuffer.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing configuration", e);
        }
        try {
            myCurrentConfiguration = objectMapper.treeToValue(jsonNode, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error creating POJO from parsed json node",e);
        }
    }

    public Configuration getCurrentConfiguration(){
        if(myCurrentConfiguration == null){
            throw new HttpConfigurationException("There is no current configuration set!");
        }
        return myCurrentConfiguration;
    }

}
