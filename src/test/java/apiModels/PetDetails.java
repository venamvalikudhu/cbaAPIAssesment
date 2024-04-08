package apiModels;

public class PetDetails {
	 	int Id;
	 	String Name;
	 
	 	public int getId() {
			return Id;
		}
		public PetDetails setId(int id) {
			Id = id;
			return this;
		}
		
		public String getName() {
			return Name;
		}
		public PetDetails setName(String name) {
			Name = name;
			return this;
		}
}
