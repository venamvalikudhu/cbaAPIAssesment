package apiTests;

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
		int newpetID = createData(node).getId();
		cleanUp.add(newpetID);

		node = test.createNode("POST call");
		Response response = Post(PetEndPoints.postImage(newpetID), fp, dir, ContentType.MULTIPART);
		apiCallValidation(response,node);
		
		node = test.createNode("POST call Validation");
		PetErrorDetails imageUpload = response.as(PetErrorDetails.class);
		Reporting.compare(StatusCode.CODE_200.code, imageUpload.getCode(),node,"Status Code Validation");
		Reporting.isTrue(imageUpload.getMessage().contains(PetConstants.IMAGE_NAME),node, "Image name validation in response. Value is " +imageUpload.getMessage());
	}

	@Test(groups = { "Regression", "Smoke" })
	private void Pet_Get() {
		node = test.createNode("Data creation for Test method");
		PetJsonModel newpet = createData(node);
		cleanUp.add(newpet.getId());

		node = test.createNode("GET call");
		Response response = Get(PetEndPoints.get(newpet.getId()), ContentType.JSON);
		apiCallValidation(response,node);

		node = test.createNode("GET call Validation");
		PetJsonModel getResponse = response.as(PetJsonModel.class);
		Reporting.compare(getResponse.getId(),newpet.getId(),node, "ID Validation");
		Reporting.compare(getResponse.getName(),newpet.getName(),node,"Name Validation");
		Reporting.compare(getResponse.getStatus(),newpet.getStatus(),node, "Status Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Get_Error() {
		node = test.createNode("GET call with Invalid Input");
		
		Response response = Get(PetEndPoints.get(0), ContentType.JSON);
		PetHelper.toTable(response,node);
		PetErrorDetails getResponse = response.as(PetErrorDetails.class);
		
		node = test.createNode("GET call Validation");		
		Reporting.compare(getResponse.getCode(),PetConstants.GET_ERRORCODE,node,"Error Code Validation");
		Reporting.compare(getResponse.getType(),PetConstants.GET_ERRORTYPE,node,"Error type Validation");
		Reporting.compare(getResponse.getMessage(),PetConstants.GET_ERROR_MESSAGE,node,"Error message Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Get_Status() {
		Response response;
		node = test.createNode("GET call by Status");
		for (PetStatus status : PetStatus.values()) {
			node = test.createNode("GET call by Status : " +status);
			qp.put(PetConstants.GET_QUERY_PARAM_KEY, status.text);
			response = Get(PetEndPoints.get, qp, ContentType.JSON);
			apiCallValidation(response, node);	
		}
		qp.clear();
		qp.put(PetConstants.GET_QUERY_PARAM_KEY,PetHelper.getStatus());
		
		node = test.createNode("GET call by Status as Query param");
		response = Get(PetEndPoints.get,qp, ContentType.JSON);
		apiCallValidation(response, node);	
	}

	@Test(groups = { "Regression"})
	private void Pet_Delete() {
		node = test.createNode("New Data created for Test Method");
		int newpetID = createData(node).getId();
		
		node = test.createNode("DELETE Call");
		Response response = Delete(PetEndPoints.delete(newpetID),ContentType.JSON);
		apiCallValidation(response,node);
		
		node = test.createNode("DELETE Call validation");
		PetErrorDetails getResponse = response.as(PetErrorDetails.class);		
		Reporting.compare(getResponse.getCode(),StatusCode.CODE_200.code,node,"Code Validation");
		Reporting.compare(getResponse.getMessage(), Integer.toString(newpetID),node,"Message Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Delete_Error() {
		node = test.createNode("DELETE call");
		Response response = Delete(PetEndPoints.delete(0),ContentType.JSON);
		PetHelper.toTable(response,node);
		
		node = test.createNode("DELETE call Validation");
		Reporting.compare(response.statusCode(),StatusCode.CODE_404.code,node,"Status Code Validation");
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
		Reporting.compare(getResponse.getId(),inputData.getId(),node,"ID Validation");
		Reporting.compare(getResponse.getName(),inputData.getName(),node,"Name Validation");
		Reporting.compare(getResponse.getStatus(),inputData.getStatus(),node,"Status Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Post_Error() {
		node = test.createNode("New user creation using POST");
		Response response = Post(PetEndPoints.post, "", ContentType.JSON);
		PetHelper.toTable(response,node);
		
		node = test.createNode("POST call validation");
		PetErrorDetails getResponse = response.as(PetErrorDetails.class);		
		Reporting.compare(getResponse.getCode(),StatusCode.CODE_405.code,node,"Status Validation");
		Reporting.compare(getResponse.getMessage(),PetConstants.POST_ERROR_MESSAGE,node,"Message Validation");
	}

	@Test(groups = { "Regression"})
	private void Pet_Post_Update() {
		node = test.createNode("New user creation using POST");
		int newpetDataID = createData(node).getId();

		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put(PetConstants.POST_FORM_PARAM_KEY[0], "UpdatedName");
		formParams.put(PetConstants.POST_FORM_PARAM_KEY[1], "UpdatedStatus");

		node = test.createNode("POST call to Update Existing");
		node.info("Updating the user with values- UpdatedName & UpdatedStatus");
		Response response = Post(PetEndPoints.post(newpetDataID), formParams, ContentType.URLENC);
		PetHelper.toTable(response,node);
		
		PetErrorDetails getResponse = response.as(PetErrorDetails.class);
		Reporting.compare(getResponse.getCode(),StatusCode.CODE_200.code,node,"Code Validation");
		Reporting.compare(getResponse.getMessage(), Integer.toString(newpetDataID),node,"Message Validation");
		
		node = test.createNode("GET call to validate the changes");
		response = Get(PetEndPoints.get(newpetDataID), ContentType.JSON);
		apiCallValidation(response,node);

		PetJsonModel getValidationResponse = response.as(PetJsonModel.class);
		Reporting.compare(getValidationResponse.getId(),newpetDataID,node,"ID Validation");
		Reporting.compare(getValidationResponse.getName(),"UpdatedName",node,"Name Validation");
		Reporting.compare(getValidationResponse.getStatus(),"UpdatedStatus",node,"Status Validation");		
	}

	@Test(groups = { "Regression", "Smoke" })
	private void Pet_Put() {
		node = test.createNode("New user creation using POST");
		PetJsonModel newpetData = createData(node);
		newpetData.setName("ChangedName");
		
		node = test.createNode("PUT call with the data changes");
		node.info("Updating the body with Name as ChangedName");
		Response response = Put(PetEndPoints.putPet, newpetData, ContentType.JSON);
		apiCallValidation(response,node);
		
		node = test.createNode("PUT call response Validation");
		PetJsonModel getValidationResponse = response.as(PetJsonModel.class);
		Reporting.compare(getValidationResponse.getId(),newpetData.getId(),node,"ID Validation");
		Reporting.compare(getValidationResponse.getName(),"ChangedName",node,"Name Validation");
		Reporting.compare(getValidationResponse.getStatus(),newpetData.getStatus(),node,"Status Validation");		
	}

	// Method for creating New Test Data
	private PetJsonModel createData(ExtentTest node) {
		Response response = Post(PetEndPoints.post, 
				PetHelper.InputData(), ContentType.JSON);
		apiCallValidation(response, node);
		return response.as(PetJsonModel.class); 
	}

	// Common Response assertion validation method
	private void apiCallValidation(Response response, ExtentTest node) {
		Reporting.compare(response.statusCode(), StatusCode.CODE_200.code,node, "Status Code Validation");
		Reporting.compare(response.contentType(),PetConstants.JSON_CONTENT_TYPE, node, "Response Code Validation");
		PetHelper.toTable(response,node);
	}


}
