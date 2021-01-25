package ssl.ois.timelog.service.user.belong;

import java.util.List;


public class GetTeamUseCaseOutput {
    private List<List<String>> memberList;
    private List<String> leader;


	public List<String> getLeader() {
		return this.leader;
	}

	public void setLeader(List<String> leader) {
		this.leader = leader;
	}

    public GetTeamUseCaseOutput(){
        
    }

	public List<String> getMemberOfList() {
		return this.memberOfList;
	}

	public void setMemberOfList(List<List<String>> memberList) {
		this.memberList = memberList;
    }

    public void addMemberToList(List<String> member) {
		this.memberList.add(member);
	}
}
