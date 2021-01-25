package ssl.ois.timelog.service.user.belong;

import java.util.List;


public class GetMemberOfUseCaseOutput {
    private List<String> memberOfList;

    public GetMemberOfUseCaseOutput(){
        
    }

	public List<String> getMemberOfList() {
		return this.memberOfList;
	}

	public void setMemberOfList(List<String> memberOfList) {
		this.memberOfList = memberOfList;
	}
}
