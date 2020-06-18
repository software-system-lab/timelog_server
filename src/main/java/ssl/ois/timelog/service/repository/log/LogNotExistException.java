package ssl.ois.timelog.service.repository.log;

public class LogNotExistException extends Exception {
    public LogNotExistException(String logName) {
        super("The log " + logName + " does not exist");
    }
}