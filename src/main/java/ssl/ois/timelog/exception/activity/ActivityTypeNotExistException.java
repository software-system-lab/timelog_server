package ssl.ois.timelog.exception.activity;

public class ActivityTypeNotExistException extends Exception {
    public ActivityTypeNotExistException(String activityTypeName) {
        super(activityTypeName + " does not exist");
    }
}