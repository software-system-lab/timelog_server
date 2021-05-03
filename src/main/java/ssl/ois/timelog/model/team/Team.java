package ssl.ois.timelog.model.team;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.timebox.Timebox;
import ssl.ois.timelog.model.unit.Unit;
import ssl.ois.timelog.service.exception.team.DuplicateMemberException;
import ssl.ois.timelog.service.exception.team.MemberNotInGroupException;


public class Team extends Unit {

//    private List<UUID> memberIdList;
//    private UUID leaderId;
    private Map<UUID, Role> memberRoleMap;

    public Team(UUID id) {
        super(id);
    }

    public Team(UUID id, List<ActivityType> activityTypeList, Map<UUID, Role> memberRoleMap) {
        super(id, activityTypeList);
        this.memberRoleMap = memberRoleMap;
    }

    public UUID getLeaderId() {
        UUID leaderID = null;
        for(Map.Entry<UUID, Role> entry : memberRoleMap.entrySet()) {
            if (entry.getValue().equals(Role.LEADER))
                leaderID = entry.getKey();
        }
        return leaderID;
    }
}
