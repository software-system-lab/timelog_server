package ssl.ois.timelog.adapter.repository.activity_type;

import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;

public interface ActivityTypeRepository {
    void addActivityType(ActivityType activityType) throws SaveActivityTypeErrorException;
    void editActivityType(ActivityType activityType) throws SaveActivityTypeErrorException;
    ActivityType findByNameAndUnitId(String activityName, String unitId) throws GetActivityTypeErrorException;
}
