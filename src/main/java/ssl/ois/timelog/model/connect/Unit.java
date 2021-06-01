package ssl.ois.timelog.model.connect;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;

public interface Unit {
    void addActivityType(ActivityType activityType) throws DuplicateActivityTypeException;
    void editActivityType(String targetActivityTypeName, ActivityType activityTypeToCheck) throws DuplicateActivityTypeException, ActivityTypeNotExistException;
    void removeActivityType(String activityTypeName) throws ActivityTypeNotExistException;
    List<ActivityType> getActivityTypeList();
    UUID getID();
    ActivityType getOperatedActivityType();
    String getTargetActivityTypeName();
    Map<UUID, Role> getMemberRoleMap() throws UnsupportedOperationException;
}