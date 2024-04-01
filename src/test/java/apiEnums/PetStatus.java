package apiEnums;

public enum PetStatus {
    available("available"),
    pending("pending"),
    sold("sold");

    public final String text;

    PetStatus(String text) {
        this.text = text;
    }

}
