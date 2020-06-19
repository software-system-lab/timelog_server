package ssl.ois.timelog.service.repository.activity;

import java.util.ArrayList;
import java.util.List;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;

public interface ActivityTypeRepository {
    void addActivityType(String userID, ActivityType activityType) throws DuplicateActivityTypeException, DatabaseErrorException;
    List<ActivityType> getActivityTypeList(String userID) throws DatabaseErrorException;
    void updateActivityType(String userID, String targetActivityTypeName, ActivityType newActivityType) throws ActivityTypeNotExistException, DatabaseErrorException;
    void removeActivityType(String userID, String targetActivityTypeName) throws ActivityTypeNotExistException, DatabaseErrorException;
}
