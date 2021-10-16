package ssl.ois.timelog.adapter.repository.activity_type;

import ssl.ois.timelog.model.activity_type.ActivityType;
import ssl.ois.timelog.exception.log.SaveLogErrorException;

public interface ActivityTypeRepository {
    void addActivityType(ActivityType activityType) throws SaveLogErrorException;
}
