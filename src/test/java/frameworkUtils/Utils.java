package frameworkUtils;

public class Utils {
	
	public static int randomNumberGenerator(int min, int max) {
		return (int)(Math.random()*(max-min+1)+min);  
	}
	
	public static String randomNameGenerator(String name) {
		return name + randomNumberGenerator(Integer.parseInt(ConfigReader.getValue("framework_min_Value")),
				Integer.parseInt(ConfigReader.getValue("framework_min_Value")));
	}
	
}
