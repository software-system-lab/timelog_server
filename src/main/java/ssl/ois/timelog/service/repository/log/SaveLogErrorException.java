package ssl.ois.timelog.service.repository.log;

public class SaveLogErrorException extends Exception{
    public SaveLogErrorException(String logName) {
        super("Error occurred during saving " + logName);
    }
}