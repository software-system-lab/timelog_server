package ssl.ois.timelog.service.user.belong;

import ssl.ois.timelog.model.activity.type.ActivityType;

import java.util.List;
import java.util.UUID;
import java.lang.String;
import java.util.ArrayList;

public class GetMemberOfUseCaseOutput {
	static class Team {
		private String teamName;
		private UUID teamID;
		private List<ActivityType> activityTypeList;

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

		public List<ActivityType> getActivityTypeList() {
			return activityTypeList;
		}

		public void setActivityTypeList(List<ActivityType> activityTypeList) {
			this.activityTypeList = activityTypeList;
		}

		public Team (){}

		public Team(String teamName, UUID teamID, List<ActivityType> activityTypeList) {
			this.teamID = teamID;
			this.teamName = teamName;
			this.activityTypeList = activityTypeList;
		}
	}

	private final List<Team> teamList;

	public GetMemberOfUseCaseOutput(){
			this.teamList = new ArrayList<>();
	}

	public List<Team> getTeamList() {
		return this.teamList;
	}

	public void addTeamToList(String teamName, UUID teamID, List<ActivityType> activityTypeList) {
		this.teamList.add(new Team(teamName, teamID, activityTypeList));
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
