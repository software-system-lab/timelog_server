package ssl.ois.timelog.service.exception;

public class AMSErrorException extends Exception{
    public AMSErrorException() {
        super("Error occurred during communicating with AMS Server");
    }
}