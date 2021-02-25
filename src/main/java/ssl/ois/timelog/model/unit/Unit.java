package ssl.ois.timelog.model.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.timebox.Timebox;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.team.DuplicateMemberException;
import ssl.ois.timelog.service.exception.team.MemberNotInGroupException;

public abstract class Unit implements UnitInterface {

    private UUID id;
    private List<ActivityType> activityTypeList;
    private ActivityType operatedActivityType;
    private String targetActivityName;

    protected Unit(UUID id) {
        this.id = id;
        this.activityTypeList = new ArrayList<>();
    }

    protected Unit(UUID id, List<ActivityType> activityTypeList) {
        this.id = id;
        this.activityTypeList = activityTypeList;
    }
    
    public void addActivityType(ActivityType activityType) throws DuplicateActivityTypeException{
        if(this.isExist(activityType.getName())) {
            throw new DuplicateActivityTypeException();
        }
        else {
            this.operatedActivityType = activityType;
            this.activityTypeList.add(this.operatedActivityType);
        }
    }

    public void editActivityType(String targetActivityTypeName, ActivityType activityTypeToCheck)
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

    public void removeActivityType(String activityTypeName) throws ActivityTypeNotExistException {
        if(!this.isExist(activityTypeName)) {
            throw new ActivityTypeNotExistException(activityTypeName);
        }
        this.targetActivityName = activityTypeName;

        this.activityTypeList.removeIf(activityType -> activityType.getName().equals(this.targetActivityName));
    }

    private boolean isExist(String activityTypeName) {
        for(ActivityType activityType: this.activityTypeList) {
            if(activityType.getName().equals(activityTypeName)) {
                return true;
            }
        }
        return false;
    }
    
    public List<ActivityType> getActivityTypeList() {
        return this.activityTypeList;
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

    public void addTimebox(Timebox timebox) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public List<Timebox> getTimeboxList() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public void addMemberToTeam(UUID targetMember) throws UnsupportedOperationException, DuplicateMemberException {
        throw new UnsupportedOperationException();
    }

    public void deleteMemberFromTeam(UUID targetMember) throws UnsupportedOperationException, MemberNotInGroupException {
        throw new UnsupportedOperationException();
    }
}