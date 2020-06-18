package ssl.ois.timelog.service.repository.activityType;

public class ActivityTypeNotExistException extends Exception {
    public ActivityTypeNotExistException(String activityTypeName) {
        super("The activity type " + activityTypeName + " does not exist");
    }
}