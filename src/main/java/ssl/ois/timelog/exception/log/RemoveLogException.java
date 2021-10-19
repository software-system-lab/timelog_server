package ssl.ois.timelog.exception.log;

public class RemoveLogException extends Exception{
    public RemoveLogException(String logId) {
        super("Error occurred during removing log " + logId);
    }
}