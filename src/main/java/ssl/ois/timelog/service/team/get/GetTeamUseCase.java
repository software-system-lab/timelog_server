package ssl.ois.timelog.service.team.get;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import ssl.ois.timelog.common.MemberDTO;
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
        System.out.println(input.getTeamID());
        try {
            //Get UUID of members
            Map<UUID, MemberDTO> teamMap = accountManager.getTeamRoleRelation(input.getTeamID());
            for(Map.Entry<UUID, MemberDTO> entry: teamMap.entrySet()){
                if (entry.getValue().getRole().equals(Role.LEADER)){
                    output.setLeader(
                        entry.getValue().getUsername(),
                        entry.getValue().getDisplayName(),
                        entry.getKey()
                    );
                }

                output.addMemberToList(
                    entry.getValue().getUsername(),
                    entry.getValue().getDisplayName(),
                    entry.getKey()
                );
            }

            accountManager.getMemberRoleOfTeam(input.getTeamID());

        } catch (AccountErrorException e) {
            throw new GetTeamErrorException();
        }
    }
}
