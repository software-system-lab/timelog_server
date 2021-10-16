package ssl.ois.timelog.exception;

public class DatabaseErrorException extends Exception{
    public DatabaseErrorException() {
        super("Error occurred during communicating with database");
    }
}