package ssl.ois.timelog.service.team.get;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.team.GetTeamErrorException;
import ssl.ois.timelog.service.manager.AccountManager;

@Service
public class GetTeamUseCase {
    private AccountManager accountManager;

    public GetTeamUseCase(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void execute(GetTeamUseCaseInput input, GetTeamUseCaseOutput output)throws GetTeamErrorException {
        try {
            //Get UUID of members
            Map<UUID, Role> teamMap = accountManager.getTeamRoleRelation(input.getGroupname());
            System.out.println(teamMap.size());
            for(Map.Entry<UUID, Role> entry:teamMap.entrySet()){
                System.out.println("GET TEAM USECASE");
                System.out.println(entry);
                if(entry.getValue().equals(Role.LEADER)){
                    output.setLeader(accountManager.getNameById(entry.getKey().toString()), entry.getKey());

                }
                output.addMemberToList(accountManager.getNameById(entry.getKey().toString()), entry.getKey());
            }

        } catch (AccountErrorException e) {
            throw new GetTeamErrorException();
        }
    }
}
