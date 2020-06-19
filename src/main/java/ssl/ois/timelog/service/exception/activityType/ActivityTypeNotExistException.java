package ssl.ois.timelog.service.exception.activityType;

import ssl.ois.timelog.model.activity.type.ActivityType;

public class ActivityTypeNotExistException extends Exception {
    public ActivityTypeNotExistException(String activityTypeName) {
        super(activityTypeName + " does not exist");
    }
}