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
	public void setId(int id) {
		this.Id = id;
	}
	public PetDetails getcategory() {
		return category;
	}
	public void setcategory(PetDetails petDetails) {
		this.category = petDetails;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<String> getPhotoUrls() {
		return PhotoUrls;
	}
	public void setPhotoUrls(List<String> photoUrls) {
		PhotoUrls = photoUrls;
	}
	public List<PetDetails> getTags() {
		return Tags;
	}
	public void setTags(List<PetDetails> tags) {
		Tags = tags;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

}
