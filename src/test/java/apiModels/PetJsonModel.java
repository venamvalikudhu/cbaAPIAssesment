package apiModels;
import java.util.List;

public class PetJsonModel {
	
	int Id;
	PetDetails category;
	String Name;
	List<String> PhotoUrls;
	List<PetDetails> Tags;
	String Status;
		 
	public int getId() {
		return Id;
	}
	public PetJsonModel setId(int id) {
		this.Id = id;
		return this;
	}
	public PetDetails getcategory() {
		return category;
	}
	public PetJsonModel setcategory(PetDetails petDetails) {
		this.category = petDetails;
		return this;
	}
	public String getName() {
		return Name;
	}
	public PetJsonModel setName(String name) {
		Name = name;
		return this;
	}
	public List<String> getPhotoUrls() {
		return PhotoUrls;
	}
	public PetJsonModel setPhotoUrls(List<String> photoUrls) {
		PhotoUrls = photoUrls;
		return this;
	}
	public List<PetDetails> getTags() {
		return Tags;
	}
	public PetJsonModel setTags(List<PetDetails> tags) {
		Tags = tags;
		return this;
	}
	public String getStatus() {
		return Status;
	}
	public PetJsonModel setStatus(String status) {
		Status = status;
		return this;
	}

}
