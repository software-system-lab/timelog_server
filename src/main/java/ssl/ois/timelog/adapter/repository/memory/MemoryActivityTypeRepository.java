package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.model.user.ActivityType;
import ssl.ois.timelog.model.user.ActivityTypeList;
import ssl.ois.timelog.service.repository.ActivityTypeRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryActivityTypeRepository implements ActivityTypeRepository {
    private Map<String, ActivityTypeList> activityTypeListMap;
    public MemoryActivityTypeRepository(){
        this.activityTypeListMap = new HashMap<>();
    }
    @Override
    public void save(ActivityTypeList activityTypeList) {
        this.activityTypeListMap.put(activityTypeList.getUserID(), activityTypeList);
    }

    @Override
    public ActivityTypeList findByUserID(String userID) {
        return this.activityTypeListMap.get(userID);
    }

    @Override
    public void update(ActivityTypeList activityTypeList) {
        this.save(activityTypeList);
    }


}
