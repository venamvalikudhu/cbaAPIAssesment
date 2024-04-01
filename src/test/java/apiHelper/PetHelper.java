package apiHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import apiConstants.PetConstants;
import apiEnums.PetStatus;
import apiModels.PetDetails;
import apiModels.PetJsonModel;
import frameworkBase.BaseTest;
import frameworkUtils.ConfigReader;
import frameworkUtils.Utils;
import io.restassured.response.Response;

public class PetHelper extends BaseTest{
	
	public static PetJsonModel InputData() {
		PetJsonModel petJsonModel = new PetJsonModel();
		petJsonModel.setId(Utils.randomNumberGenerator(PetConstants.NEW_PET_MIN,PetConstants.NEW_PET_MAX));
		petJsonModel.setName(Utils.randomNameGenerator(PetConstants.PET_NAME));

		PetDetails details = new PetDetails();
		details.setId(Utils.randomNumberGenerator(PetConstants.NEW_PET_MIN,PetConstants.NEW_PET_MAX));
		details.setName(Utils.randomNameGenerator(PetConstants.PET_DETAILS));
		petJsonModel.setcategory(details);
		
		List<String> urls = new ArrayList<String>();
		urls.add(Utils.randomNameGenerator(PetConstants.PET_DETAILS));
		petJsonModel.setPhotoUrls(urls);

		List<PetDetails> Tags = new ArrayList<PetDetails>();
		Tags.add(details);
		petJsonModel.setTags(Tags);
		
		petJsonModel.setStatus(PetStatus.available.text);
		
		return petJsonModel;
	}
	
	public static String imageDir() throws FileNotFoundException{
		String dir = System.getProperty("user.dir") 
				+ File.separator + ConfigReader.getValue("framework_images_folder") 
				+ File.separator + PetConstants.IMAGE_NAME;
		File f = new File(dir);
		if(!f.exists()) {
			throw new FileNotFoundException("File does not exist in " +dir);
		}
		return dir;
	}
	
	public static String getStatus() {
		return Stream.of(PetStatus.values())
                .map(Enum::name)
                .collect(Collectors.joining(","));
	}
	
	
	public static void toTable(Response response, ExtentTest node) {
		String[][] data = {
			    { "Response Body", "Content Type", "Response Line", "Status code"},
			    { response.getBody().prettyPrint() , response.getContentType().toString()
			    	,response.getStatusLine().toString(), Integer.toString(response.getStatusCode()) },
			};
			Markup m = MarkupHelper.createTable(data);
			node.pass(m);
	}
}
