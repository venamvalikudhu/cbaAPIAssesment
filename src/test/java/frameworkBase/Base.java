package frameworkBase;

import frameworkUtils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Base {
	protected static RequestSpecification getRequestSpec(ContentType type){
		
			RestAssuredConfig config = RestAssuredConfig.config().		
				httpClient(HttpClientConfig.httpClientConfig()
	                            .setParam("http.socket.timeout",Integer.parseInt(ConfigReader.getValue("framework_socket_timeout")))
	                            .setParam("http.connection.timeout",Integer.parseInt(ConfigReader.getValue("framework_connection_timeout"))));
			
			RequestSpecBuilder requestSpec = new RequestSpecBuilder().
	                setBaseUri(ConfigReader.getValue("baseURI").toString()).
	                setBasePath(ConfigReader.getValue("basepath").toString()); 
			
			RequestSpecification requestSpecbuilder;
			
		switch(type) {
			case JSON:
				requestSpecbuilder =  requestSpec.
		                setContentType(ContentType.JSON).
		                log(LogDetail.ALL).
		                setConfig(config).
		                build();
				break;
			case URLENC:
				requestSpecbuilder =  requestSpec.
		                setContentType(ContentType.URLENC).
		                log(LogDetail.ALL).
		                setConfig(config).
		                build();
				break;
			case MULTIPART:
				requestSpecbuilder = requestSpec.
		                setContentType(ContentType.MULTIPART).
		                log(LogDetail.ALL).
		                setConfig(config).
		                build();
				break;
			default:
				requestSpecbuilder = requestSpec.
		                setContentType(ContentType.JSON).
		                log(LogDetail.ALL).
		                setConfig(config).
		                build();
		}
		
		return requestSpecbuilder;
    }

	protected static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).
                build();
    }

}
