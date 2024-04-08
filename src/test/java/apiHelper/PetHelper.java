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
import frameworkUtils.ConfigReader;
import frameworkUtils.Utils;
import io.restassured.response.Response;

public class PetHelper{
	
	public static PetJsonModel InputData() {
		PetJsonModel petJsonModel = new PetJsonModel();
		
		PetDetails details = new PetDetails();
		details.setId(Utils.randomNumberGenerator(PetConstants.NEW_PET_MIN,PetConstants.NEW_PET_MAX))
			   .setName(Utils.randomNameGenerator(PetConstants.PET_DETAILS));

		List<String> urls = new ArrayList<String>();
		urls.add(Utils.randomNameGenerator(PetConstants.PET_DETAILS));
		
		List<PetDetails> tags = new ArrayList<PetDetails>();
		tags.add(details);

		petJsonModel.setId(Utils.randomNumberGenerator(PetConstants.NEW_PET_MIN,PetConstants.NEW_PET_MAX))
					.setName(Utils.randomNameGenerator(PetConstants.PET_NAME))
					.setcategory(details)
					.setPhotoUrls(urls)
					.setTags(tags)
					.setStatus(PetStatus.available.text);
		
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
