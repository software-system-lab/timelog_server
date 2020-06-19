package ssl.ois.timelog.service.exception.activity;

import ssl.ois.timelog.model.activity.type.ActivityType;

public class ActivityTypeNotExistException extends Exception {
    public ActivityTypeNotExistException(String activityTypeName) {
        super(activityTypeName + " does not exist");
    }
}