package frameworkUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
	
	public static int randomNumberGenerator(int min, int max) {
		return (int)(Math.random()*(max-min+1)+min);  
	}
	
	public static String randomNameGenerator(String name) {
		return name + randomNumberGenerator(Integer.parseInt(ConfigReader.getValue("framework_min_Value")),
				Integer.parseInt(ConfigReader.getValue("framework_max_Value")));
	}
	
	public static <T extends Enum<T>> String getValuesAsList(Class<T> aEnum) {
		return Stream.of(aEnum.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(","));
	}
	
}
