package com.bgtools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class PropertiesManager {
    private Properties properties = new Properties();

    public PropertiesManager() {

        InputStream is = PropertiesManager.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public static void main(String[] args) {
        PropertiesManager propertiesManager = new PropertiesManager();
        System.out.println("bgPayment = " + propertiesManager.getProperty("bgPayment"));
        System.out.println("bgAutopay = " + propertiesManager.getProperty("bgAutopay"));

    }
}