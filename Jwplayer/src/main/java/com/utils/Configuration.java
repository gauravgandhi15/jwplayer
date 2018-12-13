package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class Configuration {
   
    public static String readApplicationFile(String key) throws Exception {
        String value;
        try {
            Properties prop = new Properties();
            File f = new File(getPath() + "//Config.properties");
            if (f.exists()) {
                prop.load(new FileInputStream(f));
                value = prop.getProperty(key);
            } else {
                throw new Exception("File not found");
            }
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Failed to read from application.properties file.");
            throw ex;
        }
        if (value == null) {
            throw new Exception("Key not found in properties file");
        }
        return value;
    }

    /**
	   * Get absolute path
	   */
	  public static String getPath() {
	    String path = "";
	    File file = new File("");
	    String absolutePathOfFirstFile = file.getAbsolutePath();
	    path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
	    return path;
	  }
}
