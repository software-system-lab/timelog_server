package ssl.ois.timelog.model.team;

import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.timebox.Timebox;
import ssl.ois.timelog.model.unit.Unit;
import ssl.ois.timelog.service.exception.team.DuplicateMemberException;
import ssl.ois.timelog.service.exception.team.MemberNotInGroupException;


public class Team extends Unit {

    private List<UUID> memberIdList;
    private UUID leaderId;


	public UUID getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(UUID leaderId) {
		this.leaderId = leaderId;
	}

    public Team(UUID id) {
        super(id);
    }

    public Team(UUID id, List<UUID> memberIdList) {
        super(id);
        this.memberIdList = memberIdList;
    }

    public Team(UUID id, List<ActivityType> activityTypeList, List<UUID> memberIdList) {
        super(id, activityTypeList);
        this.memberIdList = memberIdList;
    }

    @Override
    public void addMemberToTeam(UUID memberTarget) throws DuplicateMemberException {
        if(this.isUserExist(memberTarget)) {
            throw new DuplicateMemberException();
        }
        else {
            this.memberIdList.add(memberTarget);
        }
    }

    @Override
    public void deleteMemberFromTeam(UUID memberTarget) throws MemberNotInGroupException {
        if(!this.isUserExist(memberTarget)) {
            throw new MemberNotInGroupException();
        }
        this.memberIdList.removeIf(member -> member.equals(memberTarget));
    }

    @Override
    public void addTimebox(Timebox timebox) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Timebox> getTimeboxList() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
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
