package ssl.ois.timelog.model.team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.team.DuplicateMemberException;
import ssl.ois.timelog.service.exception.team.MemberNotInGroupException;


public class Team {

    private UUID id;
    private List<ActivityType> activityTypeList;
    private ActivityType operatedActivityType;
    private String targetActivityName;
    private List<UUID> memberIdList;
    private UUID leaderId;


	public UUID getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(UUID leaderId) {
		this.leaderId = leaderId;
	}

    public Team(UUID id) {
        this.id = id;
        this.activityTypeList = new ArrayList<>();
        this.memberIdList = new ArrayList<>();
    }

    public Team(UUID id, List<UUID> memberIdList) {
        this.id = id;
        this.memberIdList = memberIdList;
    }

    public Team(UUID id, List<ActivityType> activityTypeList, List<UUID> memberIdList) {
        this.id = id;
        this.activityTypeList = activityTypeList;
        this.memberIdList = memberIdList;
    }

    public UUID getID() {
        return id;
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

    public ActivityType getOperatedActivityType() {
        return this.operatedActivityType;
    }

    public String getTargetActivityTypeName() {
        return this.targetActivityName;
    }

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

    public void addMemberToTeam(UUID memberTarget) throws DuplicateMemberException {
        if(this.isUserExist(memberTarget)) {
            throw new DuplicateMemberException();
        }
        else {
            this.memberIdList.add(memberTarget);
        }
    }

    public void deleteMemberFromTeam(UUID memberTarget) throws MemberNotInGroupException {
        if(!this.isUserExist(memberTarget)) {
            throw new MemberNotInGroupException();
        }
        this.memberIdList.removeIf(member -> member.equals(memberTarget));
    }

    private boolean isUserExist(UUID memberTarget) {
        for(UUID member: this.memberIdList) {
            if(member.equals(memberTarget)) {
                return true;
            }
        }
        return false;
    }
}
