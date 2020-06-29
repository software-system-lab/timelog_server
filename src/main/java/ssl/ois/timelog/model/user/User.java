package ssl.ois.timelog.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;

public class User {

    public enum Operation {
        CREATE, UPDATE, DELETE, NONE
    }

    private UUID id;
    private List<ActivityType> activityTypeList;
    private ActivityType operatedActivityType;
    private Operation operation;
    private String targetActivityName;

    public User(UUID id) {
        this.id = id;
        this.operation = Operation.NONE;
        this.activityTypeList = new ArrayList<>();
    }

    public User(UUID id, List<ActivityType> activityTypeList) {
        this.id = id;
        this.activityTypeList = activityTypeList;
    }

    public UUID getID() {
        return id;
    }

    public List<ActivityType> getActivityTypeList() {
        return this.activityTypeList;
    }

    public ActivityType getOperatedActivityType() {
        return this.operatedActivityType;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public String getTargetActivityTypeName() {
        return this.targetActivityName;
    }

    public void addActivityType(ActivityType activityType) throws DuplicateActivityTypeException {
        if(this.isExist(activityType.getName())) {
            throw new DuplicateActivityTypeException();
        }
        this.operatedActivityType = activityType;
        this.operation = Operation.CREATE;
    }

    public void updateActivityType(String targetActivityTypeName, ActivityType activityType)
            throws DuplicateActivityTypeException, ActivityTypeNotExistException {
        if(!this.isExist(targetActivityTypeName)) {
            throw new ActivityTypeNotExistException(targetActivityTypeName);
        }
        if(this.isExist(activityType.getName()) && !activityType.getName().equals(targetActivityTypeName)) {
            throw new DuplicateActivityTypeException();
        }
        this.targetActivityName = targetActivityTypeName;
        this.operatedActivityType = activityType;
        this.operation = Operation.UPDATE;
    }

    public void deleteActivityType(String activityTypeName) throws ActivityTypeNotExistException {
        if(!this.isExist(activityTypeName)) {
            throw new ActivityTypeNotExistException(activityTypeName);
        }
        this.targetActivityName = activityTypeName;
        this.operation = Operation.DELETE;
    }

    public void store() {
        switch(this.operation) {
            case CREATE:
                this.activityTypeList.add(this.operatedActivityType);
                break;
            case UPDATE:
                this.activityTypeList.removeIf(activityType -> activityType.getName().equals(this.targetActivityName));
                this.activityTypeList.add(this.operatedActivityType);
                break;
            case DELETE:
                this.activityTypeList.removeIf(activityType -> activityType.getName().equals(this.targetActivityName));
                break;
            case NONE:
                break;
        }
        this.targetActivityName = null;
        this.operation = Operation.NONE;
    }

    private boolean isExist(String activityTypeName) {
        for(ActivityType activityType: this.activityTypeList) {
            if(activityType.getName().equals(activityTypeName)) {
                return true;
            }
        }
        return false;
    }
}
