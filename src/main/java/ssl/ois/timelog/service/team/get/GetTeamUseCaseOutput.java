package ssl.ois.timelog.service.team.get;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.team.Person;
import java.util.List;
import java.util.UUID;
import java.lang.String;
import java.util.ArrayList;


public class GetTeamUseCaseOutput {
    private List<Person> memberList;
    private Person leader;

	public Person getLeader() {
		return this.leader;
	}

	public void setLeader(String username, UUID userID) {
		Person leader = new Person(username, userID);
		this.leader = leader;
	}

    public GetTeamUseCaseOutput(){
		this.memberList = new ArrayList<>();
    }

	public List<Person> getMemberList() {
		return this.memberList;
	}

    public void addMemberToList(String username, UUID userID) {
		this.memberList.add(new Person(username, userID));
	}
}
