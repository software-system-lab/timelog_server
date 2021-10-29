package ssl.ois.timelog.adapter.repository.activity_type;

import ssl.ois.timelog.exception.DatabaseErrorException;
import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;

import java.util.List;

public interface ActivityTypeRepository {
    void addActivityType(ActivityType activityType) throws SaveActivityTypeErrorException;
    void updateActivityType(ActivityType activityType) throws SaveActivityTypeErrorException;
    ActivityType findByNameAndUnitId(String activityName, String unitId) throws GetActivityTypeErrorException;
    ActivityType findById(String id) throws GetActivityTypeErrorException;
    List<ActivityType> findByUnitId(String unitId) throws DatabaseErrorException;
}
