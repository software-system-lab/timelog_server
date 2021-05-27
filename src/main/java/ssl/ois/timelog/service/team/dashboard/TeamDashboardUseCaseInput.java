package ssl.ois.timelog.service.team.dashboard;
import java.util.List;
import java.util.UUID;
import ssl.ois.timelog.service.team.Person;



public class TeamDashboardUseCaseInput {
    private String teamID;
    private String startDate;
    private String endDate;
    private List<Person> memberList;
    private String groupname;

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID= teamID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Person> getMemberList() {
		return this.memberList;
	}

    public void setMemberList(List<Person> memberList) {
		this.memberList = memberList;
	}

    public void addMemberToList(String username, UUID userID) {
		this.memberList.add(new Person(username, userID));
	}
    public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}

