package ssl.ois.timelog.service.team.dashboard;

import java.util.List;


public class TeamDashboardUseCaseInput {
    private String teamID;
    private String startDate;
    private String endDate;
    private List<String> memberList;
    private String groupname;
    private List<String> filterList;
    private Boolean personal;
    private String teamName;

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

    public List<String> getMemberList() {
        return this.memberList;
    }

    public void setMemberList(List<String> memberList) {
		this.memberList = memberList;
	}

    public String getGroupname() {
		return groupname;
	}

    public void setGroupname(String groupname) { this.groupname = groupname; }

    public List<String> getFilterList() { return this.filterList; }

    public void setFilterList(List<String> filterList) { this.filterList = filterList; }

    public Boolean getPersonal() { return personal; }

    public void setPersonal(Boolean personal) { this.personal = personal; }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}

