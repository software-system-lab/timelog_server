package ssl.ois.timelog.exception.activity;

public class SaveActivityTypeErrorException extends Exception{
    public SaveActivityTypeErrorException(String activityName) {
        super("Error occurred during saving " + activityName);
    }
}