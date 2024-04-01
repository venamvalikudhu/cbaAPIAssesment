package apiEndPoints;

import frameworkUtils.ConfigReader;

public class PetEndPoints{
	private static String petEndPoint = ConfigReader.getValue("Pet_EndPoint");	
	public static String get(int petID ) {return petEndPoint + "/" + petID; }
	public static String get = petEndPoint + "/findByStatus"; 
	public static String post = petEndPoint ;
	public static String post(int petID ) {return petEndPoint + "/" + petID; }
	public static String postImage(int petID ) {return petEndPoint + "/" + petID + "/uploadImage"; }
	public static String delete(int petID ) {return petEndPoint + "/" + petID; }
	public static String putPet = petEndPoint;
}
