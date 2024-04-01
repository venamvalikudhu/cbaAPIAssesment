package apiTests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import apiConstants.PetConstants;
import apiEndPoints.PetEndPoints;
import apiEnums.PetStatus;
import apiEnums.StatusCode;
import apiHelper.PetHelper;
import apiModels.PetErrorDetails;
import apiModels.PetJsonModel;
import frameworkBase.BaseTest;
import frameworkUtils.ExtentReportConfig;
import frameworkUtils.Reporting;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetTests extends BaseTest{

	private ExtentTest node;
	private List<Integer> cleanUp =  new ArrayList<Integer>();
	Map<String, String> fp = new HashMap<String, String>();
	Map<String, Object> qp = new HashMap<String, Object>();
	
	// SetUp
	@BeforeMethod(alwaysRun=true)
	private void setUp(ITestContext testContext, Method m) {
		test = report.createTest(m.getName());
		}

	// Clean Up
	@AfterMethod(alwaysRun=true)
	private void cleanUp(ITestResult result) {
		if(cleanUp.size() > 0) {
			for(Integer id : cleanUp) {
				Delete(PetEndPoints.delete(id), ContentType.JSON);
			}
		}
	}
	
	@Test(groups = { "Regression", "Smoke" })
	private void Pet_Post_Image() throws FileNotFoundException{
		fp.put(PetConstants.ADDITIONAL_META_DATA, "UploadImage");
		String dir = PetHelper.imageDir();
		
		node = test.createNode("Data creation for Test method");
		int newpetDataID = createData(PetHelper.InputData(),node).getId();
		cleanUp.add(newpetDataID);

		node = test.createNode("POST call");
		Response response = Post(PetEndPoints.postImage(newpetDataID), fp, dir, ContentType.MULTIPART);
		apiCallValidation(response,node);
		
		node = test.createNode("POST call Validation");
		PetErrorDetails imageUpload = response.as(PetErrorDetails.class);
		Reporting.checkValues(StatusCode.CODE_200.code, imageUpload.getCode(),node,"Status Code Validation");
		Reporting.isTrue(imageUpload.getMessage().contains(PetConstants.IMAGE_NAME),node, "Image name validation in response. Value is " +imageUpload.getMessage());
	}

	@Test(groups = { "Regression", "Smoke" })
	private void Pet_Get() {
		node = test.createNode("Data creation for Test method");
		PetJsonModel newpetData = createData(PetHelper.InputData(),node);
		cleanUp.add(newpetData.getId());

		node = test.createNode("GET call");
		Response response = Get(PetEndPoints.get(newpetData.getId()), ContentType.JSON);
		apiCallValidation(response,node);

		node = test.createNode("GET call Validation");
		PetJsonModel getResponse = response.as(PetJsonModel.class);
		Reporting.checkValues(getResponse.getId(),newpetData.getId(),node, "ID Validation");
		Reporting.checkValues(getResponse.getName(),newpetData.getName(),node,"Name Validation");
		Reporting.checkValues(getResponse.getStatus(),newpetData.getStatus(),node, "Status Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Get_Error() {
		node = test.createNode("GET call with Invalid Input");
		Response response = Get(PetEndPoints.get(0), ContentType.JSON);
		
		PetHelper.toTable(response,node);
		PetErrorDetails getResponse = response.as(PetErrorDetails.class);
		
		node = test.createNode("GET call Validation");		
		Reporting.checkValues(getResponse.getCode(),PetConstants.GET_ERRORCODE,node,"Error Code Validation");
		Reporting.checkValues(getResponse.getType(),PetConstants.GET_ERRORTYPE,node,"Error type Validation");
		Reporting.checkValues(getResponse.getMessage(),PetConstants.GET_ERROR_MESSAGE,node,"Error message Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Get_Status() {
		Response response;
		node = test.createNode("GET call by Status");
		for (PetStatus status : PetStatus.values()) {
			node = test.createNode("GET call by Status : " +status);
			qp.put(PetConstants.GET_QUERY_PARAM_KEY, status.text);
			response = Get(PetEndPoints.get, qp, ContentType.JSON);
			Reporting.checkValues(response.statusCode(), StatusCode.CODE_200.code,node,"Status Code Validation");
			Reporting.checkValues(response.contentType(),PetConstants.JSON_CONTENT_TYPE,node,"Content Code Validation");	
			
		}
		qp.clear();
		qp.put(PetConstants.GET_QUERY_PARAM_KEY,PetHelper.getStatus());
		
		node = test.createNode("GET call by Status as Query param");
		response = Get(PetEndPoints.get,qp, ContentType.JSON);
		Reporting.checkValues(response.statusCode(), StatusCode.CODE_200.code,node,"Status Code Validation");
		Reporting.checkValues(response.contentType(),PetConstants.JSON_CONTENT_TYPE,node,"Content Type Validation");			
	}

	@Test(groups = { "Regression"})
	private void Pet_Delete() {
		node = test.createNode("New Data created for Test Method");
		int newpetDataID = createData(PetHelper.InputData(),node).getId();
		
		node = test.createNode("DELETE Call");
		Response response = Delete(PetEndPoints.delete(newpetDataID),ContentType.JSON);
		apiCallValidation(response,node);
		
		node = test.createNode("DELETE Call validation");
		PetErrorDetails getResponse = response.as(PetErrorDetails.class);		
		Reporting.checkValues(getResponse.getCode(),StatusCode.CODE_200.code,node,"Code Validation");
		Reporting.checkValues(getResponse.getMessage(), Integer.toString(newpetDataID),node,"Message Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Delete_Error() {
		node = test.createNode("DELETE call");
		Response response = Delete(PetEndPoints.delete(0),ContentType.JSON);
		PetHelper.toTable(response,node);
		
		node = test.createNode("DELETE call Validation");
		Reporting.checkValues(response.statusCode(),StatusCode.CODE_404.code,node,"Status Code Validation");
	}

	@Test(groups = { "Regression", "Smoke"})
	private void Pet_Post() {
		node = test.createNode("New user creation using POST");
		PetJsonModel inputData = PetHelper.InputData();
		Response response = Post(PetEndPoints.post, inputData,ContentType.JSON);
		apiCallValidation(response,node);

		PetJsonModel getResponse = response.as(PetJsonModel.class);
		cleanUp.add(getResponse.getId());
		
		node = test.createNode("POST call validation");
		Reporting.checkValues(getResponse.getId(),inputData.getId(),node,"ID Validation");
		Reporting.checkValues(getResponse.getName(),inputData.getName(),node,"Name Validation");
		Reporting.checkValues(getResponse.getStatus(),inputData.getStatus(),node,"Status Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Post_Error() {
		node = test.createNode("New user creation using POST");
		Response response = Post(PetEndPoints.post, "", ContentType.JSON);
		PetHelper.toTable(response,node);
		
		node = test.createNode("POST call validation");
		PetErrorDetails getResponse = response.as(PetErrorDetails.class);		
		Reporting.checkValues(getResponse.getCode(),StatusCode.CODE_405.code,node,"Status Validation");
		Reporting.checkValues(getResponse.getMessage(),PetConstants.POST_ERROR_MESSAGE,node,"Message Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Post_Update() {
		node = test.createNode("New user creation using POST");
		PetJsonModel inputData = PetHelper.InputData();
		int newpetDataID = createData(inputData, node).getId();

		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put(PetConstants.POST_FORM_PARAM_KEY[0], "UpdatedName");
		formParams.put(PetConstants.POST_FORM_PARAM_KEY[1], "UpdatedStatus");

		node = test.createNode("POST call to Update Existing");
		node.info("Updating the user with values- UpdatedName & UpdatedStatus");
		Response response = Post(PetEndPoints.post(newpetDataID), formParams, ContentType.URLENC);
		PetHelper.toTable(response,node);
		
		PetErrorDetails getResponse = response.as(PetErrorDetails.class);
		Reporting.checkValues(getResponse.getCode(),StatusCode.CODE_200.code,node,"Code Validation");
		Reporting.checkValues(getResponse.getMessage(), Integer.toString(newpetDataID),node,"Message Validation");
		
		node = test.createNode("GET call to validate the changes");
		response = Get(PetEndPoints.get(newpetDataID), ContentType.JSON);
		apiCallValidation(response,node);

		PetJsonModel getValidationResponse = response.as(PetJsonModel.class);
		Reporting.checkValues(getValidationResponse.getId(),newpetDataID,node,"ID Validation");
		Reporting.checkValues(getValidationResponse.getName(),"UpdatedName",node,"Name Validation");
		Reporting.checkValues(getValidationResponse.getStatus(),"UpdatedStatus",node,"Status Validation");		
	}

	@Test(groups = { "Regression", "Smoke" })
	private void Pet_Put() {
		node = test.createNode("New user creation using POST");
		PetJsonModel inputData = PetHelper.InputData();
		PetJsonModel newpetData = createData(inputData,node);
		inputData.setName("ChangedName");
		
		node = test.createNode("PUT call with the data changes");
		node.info("Updating the body with Name as ChangedName");
		Response response = Put(PetEndPoints.putPet, inputData, ContentType.JSON);
		apiCallValidation(response,node);
		
		node = test.createNode("PUT call response Validation");
		PetJsonModel getValidationResponse = response.as(PetJsonModel.class);
		Reporting.checkValues(getValidationResponse.getId(),newpetData.getId(),node,"ID Validation");
		Reporting.checkValues(getValidationResponse.getName(),"ChangedName",node,"Name Validation");
		Reporting.checkValues(getValidationResponse.getStatus(),newpetData.getStatus(),node,"Status Validation");		
	}

	// Method for creating New Test Data
	private PetJsonModel createData(PetJsonModel petJsonModel, ExtentTest node) {
		Response response = Post(PetEndPoints.post, 
				petJsonModel, ContentType.JSON);
		apiCallValidation(response, node);
		return response.as(PetJsonModel.class); 
	}

	// Common Response assertion validation method
	private void apiCallValidation(Response response, ExtentTest node) {
		Reporting.checkValues(response.statusCode(), StatusCode.CODE_200.code,node, "Status Code Validation");
		Reporting.checkValues(response.contentType(),PetConstants.JSON_CONTENT_TYPE, node, "Response Code Validation");
		PetHelper.toTable(response,node);
	}


}
