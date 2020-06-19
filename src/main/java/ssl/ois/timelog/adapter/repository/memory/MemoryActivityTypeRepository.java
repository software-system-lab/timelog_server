package ssl.ois.timelog.adapter.repository.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeRepository;

public class MemoryActivityTypeRepository implements ActivityTypeRepository {
    private Map<String, List<ActivityType>> activityMap; 

    public MemoryActivityTypeRepository() {
        this.activityMap = new HashMap<>();
    }

    @Override
    public void addActivityType(String userID, ActivityType activityType) throws DuplicateActivityTypeException {
        this.checkActivityListForUser(userID);

        for(ActivityType type: this.activityMap.get(userID)) {
            if (type.getName().equals(activityType.getName())) {
                throw new DuplicateActivityTypeException();
            }
        }
        this.activityMap.get(userID).add(activityType);
    }

    @Override
    public List<ActivityType> getActivityTypeList(String userID) {
        this.checkActivityListForUser(userID);
        return this.activityMap.get(userID);
    }

    @Override
    public void updateActivityType(String userID, String activityTypeName, ActivityType newActivityType) throws ActivityTypeNotExistException {
        this.checkActivityListForUser(userID);

        if (this.activityMap.get(userID).removeIf(activityType -> activityType.getName().equals(activityTypeName))) {
            this.activityMap.get(userID).add(newActivityType);
        } else {
            throw new ActivityTypeNotExistException(activityTypeName);
        }
    }

    @Override
    public void removeActivityType(String userID, String activityTypeName) throws ActivityTypeNotExistException {
        this.checkActivityListForUser(userID);

        if (!this.activityMap.get(userID).removeIf(activityType -> activityType.getName().equals(activityTypeName))) {
            throw new ActivityTypeNotExistException(activityTypeName);
        }
    }

    private void checkActivityListForUser(String userID) {
        if(this.activityMap.get(userID) == null) {
            this.activityMap.put(userID, new ArrayList<>());
        }
    }
}