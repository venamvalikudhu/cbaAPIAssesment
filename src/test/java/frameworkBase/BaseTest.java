package frameworkBase;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import frameworkUtils.ConfigReader;
import frameworkUtils.ExtentReportConfig;

public class BaseTest extends Base {
	
		protected ExtentReports report; 
		protected ExtentTest test;
		
		@BeforeTest(alwaysRun=true)
		protected void setUpTest() {
			report = ExtentReportConfig.SetExtentReport(this.getClass().getName());
		}

		@AfterTest(alwaysRun=true)
		protected void cleanUpTest() {
			report.flush();
		}
	 
		@BeforeSuite(alwaysRun=true)
		protected void setUp() {
			ConfigReader.loadProperties();
		}
		
	protected Response Post(String EndPoint, Object body, ContentType type) {
		return  given(getRequestSpec(type)).
                body(body).
                when().post(EndPoint).
                then().spec(getResponseSpec()).
                extract().
                response();
	}

	protected <T> T Post(String EndPoint, Object body, Class<T> s,ContentType type) {
		return  given(getRequestSpec(type)).
                body(body).
                when().post(EndPoint).
                then().spec(getResponseSpec()).
                extract().
                response().
                as(s);
	}
	
	protected  Response Post(String EndPoint, Map<String, String> formParams,ContentType type) {
		return  given(getRequestSpec(type)).
                formParams(formParams).
                when().post(EndPoint).
                then().spec(getResponseSpec()).
                extract().
                response();
	}
	
	protected  Response Post(String EndPoint,Map<String, String> formParams, String filePath,ContentType type) {
		return  given(getRequestSpec(type)).
				params(formParams).
                multiPart("file", new File(filePath)).
                when().post(EndPoint).
                then().spec(getResponseSpec()).
                extract().
                response();
	}
	
	protected  Response Get(String EndPoint,ContentType type) {
	    return  given(getRequestSpec(type)).
	    		when().get(EndPoint).
	    		then().spec(getResponseSpec()).
	            extract().
	            response();	
	}
	
	protected  Response Get(String EndPoint, Map<String, Object> queryParams,ContentType type) {
	    return  given(getRequestSpec(type)).
	    		queryParams(queryParams).
	    		when().get(EndPoint).
	    		then().spec(getResponseSpec()).
	            extract().
	            response();	
	}
	
	protected  Response Delete(String EndPoint,ContentType type) {
	    return  given(getRequestSpec(type)).
	    		when().delete(EndPoint).
	    		then().spec(getResponseSpec()).
	            extract().
	            response();	
	}
	
	protected  Response Put(String EndPoint, Object body,ContentType type) {
	    return  given(getRequestSpec(type)).
	    		body(body).
	    		when().put(EndPoint).
	    		then().spec(getResponseSpec()).
	            extract().
	            response();		
	}
}
