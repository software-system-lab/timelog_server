package ssl.ois.timelog.model.connect;

import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.team.DuplicateMemberException;
import ssl.ois.timelog.service.exception.team.MemberNotInGroupException;

public interface UnitInterface {
    void addActivityType(ActivityType activityType) throws DuplicateActivityTypeException;
    void editActivityType(String targetActivityTypeName, ActivityType activityTypeToCheck) throws DuplicateActivityTypeException, ActivityTypeNotExistException;
    void removeActivityType(String activityTypeName) throws ActivityTypeNotExistException;
    List<ActivityType> getActivityTypeList();
    UUID getID();
    ActivityType getOperatedActivityType();
    String getTargetActivityTypeName();
    void addMemberToTeam(UUID targetMember) throws UnsupportedOperationException, DuplicateMemberException;
    void deleteMemberFromTeam(UUID targetMember) throws UnsupportedOperationException, MemberNotInGroupException;
    List<UUID>  getMemberTeamList(UUID targetMember) throws UnsupportedOperationException, MemberNotInGroupException;
}