package apiEnums;

public enum StatusCode {
    CODE_200(200, "OK"),
    CODE_201(201, "Created"),
    CODE_404(404, "Not Found"),
    CODE_405(405, "Method Not Allowed"),
    CODE_415(415, "Unsupported Media Type");

    public final int code;
    public final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
