package apiModels;

public class PetErrorDetails {
		int Code;
	 	String Type;
		String Message;
		
		public int getCode() {
			return Code;
		}
		public PetErrorDetails setCode(int code) {
			Code = code;
			return this;
		}
		public String getType() {
			return Type;
		}
		public PetErrorDetails setType(String type) {
			Type = type;
			return this;
		}
		public String getMessage() {
			return Message;
		}
		public PetErrorDetails setMessage(String message) {
			Message = message;
			return this;
		}
	 
	 
}
