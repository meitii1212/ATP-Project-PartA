package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {
    private static Configurations Instance =null;
    private Properties properties;
    OutputStream output;
    InputStream input;
    private Configurations(){
        try{

            output = new FileOutputStream("resources/config.properties");
            input = new FileInputStream("resources/config.properties");
            properties = new Properties();

            // set key and value
            properties.setProperty("threadPoolSize", "3");
            properties.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
            properties.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");

            // save properties to project root folder
            properties.store(output, "");

        }
        catch (IOException io) {
            io.printStackTrace();
        }


        //properties.load();
    }
    public static Configurations getInstance(){
        if(Instance ==null){
            Instance = new Configurations();
        }

        return Instance;
    }

    public static synchronized String getProperty(String key) throws IOException {
        // load a properties file
        Configurations con = Configurations.getInstance();
        con.properties.load(con.input);
        return con.properties.getProperty(key);
    }
    public static synchronized void setProperty(String key, String new_val) throws IOException {
        // store a properties file
        Configurations con = Configurations.getInstance();
        con.properties.setProperty(key, new_val);
        con.properties.store(con.output, null);
    }
}