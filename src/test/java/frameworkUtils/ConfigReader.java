package frameworkUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static String propertyFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\Config.properties"; 
	private static Properties properties;
	
	public static void loadProperties()
	{
		if(properties == null) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
				try {
				properties.load(reader);
				reader.close();
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
			}
		}
	}
	
	public static String getValue(String Key){
		try
		{
		if(properties.containsKey(Key)) {
			return properties.getProperty(Key);
		}
		else
		{
			throw new KeyNotFoundException("key : "+ Key +"is not defined in Config File");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
