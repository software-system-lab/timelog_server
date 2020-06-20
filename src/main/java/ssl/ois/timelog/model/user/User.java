package ssl.ois.timelog.model.user;

import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;

public class User {

    public static enum Operation {
        CREATE, UPDATE, DELETE
    };

    private UUID id;
    private List<ActivityType> activityTypeList;
    private ActivityType operatedActivityType;
    private Operation operation;

    public User(UUID id) {
        this.id = id;
    }

    public UUID getID() {
        return id;
    }

    public List<ActivityType> getActivityTypeList() {
        return this.activityTypeList;
    }

    public void addActivityType(ActivityType activityType) {
        this.operatedActivityType = activityType;
        this.operation = Operation.CREATE;
    }

    public void updateActivityType(ActivityType activityType) {
        this.operatedActivityType = activityType;
        this.operation = Operation.UPDATE;
    }

    public void deleteActivityType(String activityTypeName) {
        this.operatedActivityType = new ActivityType(activityTypeName, null, null);
        this.operation = Operation.DELETE;
    }

    public ActivityType getOperatedActivityType() {
        return this.operatedActivityType;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void store() {
        switch(this.operation) {
            case CREATE:
                this.activityTypeList.add(this.operatedActivityType);
                break;
            case UPDATE:
                this.activityTypeList.removeIf(activityType -> activityType.getName().equals(this.operatedActivityType.getName()));
                this.activityTypeList.add(this.operatedActivityType);
                break;
            case DELETE:
                this.activityTypeList.removeIf(activityType -> activityType.getName().equals(this.operatedActivityType.getName()));
                break;
        }
        this.operation = null;
    }
}
