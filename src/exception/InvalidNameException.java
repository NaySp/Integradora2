package exception;

public class InvalidNameException extends Exception {

    private String msg;

    public InvalidNameException(String msg){
        super(msg);
        this.msg = "Invalid product name";
    }

    public String getMsg() {

        return msg;
    }

}
