package ssl.ois.timelog.service.team.get;

import ssl.ois.timelog.service.team.Person;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;


public class GetTeamUseCaseOutput {
    private List<Person> memberList;
    private Person leader;

	public Person getLeader() {
		return this.leader;
	}

	public void setLeader(String username, String displayName, UUID userID) {
		this.leader = new Person(username, displayName, userID);
	}

    public GetTeamUseCaseOutput(){
		this.memberList = new ArrayList<>();
    }

	public List<Person> getMemberList() {
		return this.memberList;
	}

    public void addMemberToList(String username, String displayName, UUID userID) {
		this.memberList.add(new Person(username, displayName, userID));
	}
}
