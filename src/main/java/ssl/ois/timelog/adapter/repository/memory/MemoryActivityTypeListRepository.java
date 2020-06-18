package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.repository.activity.ActivityTypeListRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryActivityTypeListRepository implements ActivityTypeListRepository {
    private Map<String, ActivityTypeList> activityTypeListMap;
    public MemoryActivityTypeListRepository(){
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
