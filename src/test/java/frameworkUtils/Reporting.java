package frameworkUtils;

import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;


public class Reporting extends Assert{
	  public static void checkValues(int actual, int expected, ExtentTest node,String message) {
		    assertEquals(actual, expected, null);
		    node.pass(message + " Expected : " +expected + " Actual : " +actual);
		  }
	  
	  public static void checkValues(String actual, String expected, ExtentTest node, String message) {
		    assertEquals(actual, expected, null);
		    node.pass(message + " Expected : " +expected + " Actual : " +actual);
		  }
	  
	  public static void isTrue(boolean condition, ExtentTest node, String message) {
		    assertTrue(condition);
		    node.pass(message);		    
		  }

}
