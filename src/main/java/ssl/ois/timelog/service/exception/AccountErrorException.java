package ssl.ois.timelog.service.exception;

public class AccountErrorException extends Exception{
    public AccountErrorException() {
        super("Error occurred during communicating with AMS Server");
    }
}