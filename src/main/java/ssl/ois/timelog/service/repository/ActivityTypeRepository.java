package ssl.ois.timelog.service.repository;

import ssl.ois.timelog.model.user.ActivityTypeList;

public interface ActivityTypeRepository {
    public void save(ActivityTypeList activityTypeList);
    public ActivityTypeList findByUserID(String userID);
    public void update(ActivityTypeList activityTypeList);
}
