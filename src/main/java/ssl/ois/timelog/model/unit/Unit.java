package ssl.ois.timelog.model.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;

public abstract class Unit {

    private UUID id;
    private List<ActivityType> activityTypeList;
    private ActivityType operatedActivityType;
    private String targetActivityName;

    public void addActivityType(ActivityType activityType) throws DuplicateActivityTypeException {
        if(this.isExist(activityType.getName())) {
            if(!this.isExistandDeleted(activityType.getName())) {
                throw new DuplicateActivityTypeException();
            }
            else {
                activityType.setDeleted(false);
                this.targetActivityName = activityType.getName();
                this.operatedActivityType = activityType;

                this.activityTypeList.removeIf(deletedActivityType -> deletedActivityType.getName().equals(this.targetActivityName));
                this.activityTypeList.add(this.operatedActivityType);
            }
        }
        else {
            this.operatedActivityType = activityType;
            this.activityTypeList.add(this.operatedActivityType);
        }
    }

    public void updateActivityType(String targetActivityTypeName, ActivityType activityTypeToCheck)
            throws DuplicateActivityTypeException, ActivityTypeNotExistException {
        if(!this.isExist(targetActivityTypeName)) {
            throw new ActivityTypeNotExistException(targetActivityTypeName);
        }
        if(this.isExist(activityTypeToCheck.getName()) && !activityTypeToCheck.getName().equals(targetActivityTypeName)) {
            throw new DuplicateActivityTypeException();
        }

        this.targetActivityName = targetActivityTypeName;
        this.operatedActivityType = activityTypeToCheck;

        this.activityTypeList.removeIf(activityType -> activityType.getName().equals(this.targetActivityName));
        this.activityTypeList.add(this.operatedActivityType);
    }

    public void deleteActivityType(String activityTypeName, ActivityType activityTypeToDelete) throws ActivityTypeNotExistException {
        if(!this.isExist(activityTypeName)) {
            throw new ActivityTypeNotExistException(activityTypeName);
        }
        this.targetActivityName = activityTypeName;
        this.operatedActivityType = activityTypeToDelete;

        this.activityTypeList.removeIf(activityType -> activityType.getName().equals(this.targetActivityName));
        this.activityTypeList.add(this.operatedActivityType);
    }

    private boolean isExist(String activityTypeName) {
        for(ActivityType activityType: this.activityTypeList) {
            if(activityType.getName().equals(activityTypeName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExistandDeleted(String activityTypeName) {
        for(ActivityType activityType: this.activityTypeList) {
            if(activityType.getName().equals(activityTypeName) && activityType.isDeleted()) {
                return true;
            }
        }
        return false;
    }
    

    public List<ActivityType> getActivityTypeList() {
        List<ActivityType> notDeleted = new ArrayList<>();
        for(ActivityType activityType: this.activityTypeList) {
            if(!activityType.isDeleted()) {
                notDeleted.add(activityType);
            }
        }
        return notDeleted;
    }

    public Unit(UUID id) {
        this.id = id;
        this.activityTypeList = new ArrayList<>();
    }

    public Unit(UUID id, List<ActivityType> activityTypeList) {
        this.id = id;
        this.activityTypeList = activityTypeList;
    }


    public UUID getID() {
        return id;
    }

    public ActivityType getOperatedActivityType() {
        return this.operatedActivityType;
    }

    public String getTargetActivityTypeName() {
        return this.targetActivityName;
    }
}