package exception;

public class EqualProductException extends Exception{
    private String msg;

    public EqualProductException(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
