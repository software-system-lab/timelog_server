package ssl.ois.timelog.service.repository.activityType;

import java.util.ArrayList;
import java.util.List;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.activity.type.add.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.activityType.ActivityTypeNotExistException;

public interface ActivityTypeRepository {
    void addActivityType(String userID, ActivityType activityType) throws DuplicateActivityTypeException;
    List<ActivityType> getActivityTypeList(String userID);
    void updateActivityType(String userID, String targetActivityTypeName, ActivityType newActivityType) throws ActivityTypeNotExistException;
    void removeActivityType(String userID, String targetActivityTypeName) throws ActivityTypeNotExistException;
}
