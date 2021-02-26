package ssl.ois.timelog.service.user.belong;

import java.util.List;
import java.util.UUID;
import java.lang.String;
import java.util.ArrayList;

public class GetMemberOfUseCaseOutput {
	class Team {
		private String teamName;
		private UUID teamID;

		public String getTeamName() {
			return teamName;
		}

		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}

		public UUID getTeamID() {
			return teamID;
		}

		public void setTeamID(UUID teamID) {
			this.teamID = teamID;
		}

		public Team (){}
		public Team(String teamName, UUID teamID) {
			this.teamID = teamID;
			this.teamName = teamName;
		}
	}

	private List<Team> teamList;

    public GetMemberOfUseCaseOutput(){
        this.teamList = new ArrayList<>();
    }

	public List<Team> getTeamList() {
		return this.teamList;
	}

	public void addTeamToList(String teamName, UUID teamID) {
		this.teamList.add(new Team(teamName, teamID));
	}

//
//	public List<UUID> teamIdList() {
//		return teamIdList;
//	}
//
//	public void addTeamID(UUID teamID) {
//		this.teamID.add(teamID);
//	}
//
//	public List<String> getMemberOfList() {
//		return this.memberOfList;
//	}
//
//	public void setMemberOfList(List<String> memberOfList) {
//		this.memberOfList = memberOfList;
//	}
}
