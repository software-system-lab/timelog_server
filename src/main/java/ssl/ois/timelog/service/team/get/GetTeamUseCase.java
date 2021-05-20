package ssl.ois.timelog.service.team.get;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.client.RestClientException;

import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.team.GetTeamErrorException;
import ssl.ois.timelog.service.manager.AccountManager;

import org.springframework.web.client.RestTemplate;

@Service
public class GetTeamUseCase {
    private AccountManager accountManager;

    public GetTeamUseCase(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void execute(GetTeamUseCaseInput input, GetTeamUseCaseOutput output)throws GetTeamErrorException {
        try {
            final String urlMember = "http://localhost:8080/team/get/members";
            final String urlGetName = "http://localhost:8080/team/get/name";
            final String urlLeader = "http://localhost:8080/team/get/leader";
            final String urlGetUid = "http://localhost:8080/team/get/uuid/user";


            
            RestTemplate restTemplate = new RestTemplate();

            UUID teamId = accountManager.getTeamIdByTeamName(input.getGroupname());


            //--------------------------------------------------------------
            List<String> result = restTemplate.postForObject(urlMember, input, List.class);

            //Get Name of Member
            System.out.println("OK Template");
            for(int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i));
            }
            //--------------------------------------------------------------

            //Get UUID of members
            System.out.println("TeamID getTeamIdByTeamName");
            System.out.println(teamId.toString());
            Map<UUID, Role> teamMap = accountManager.getTeamRoleRelation(teamId.toString());
            
            
            System.out.println("New Template");
            for(Map.Entry<UUID, Role> entry:teamMap.entrySet()){
                if(entry.getValue().equals(Role.LEADER)){
                    System.out.println(accountManager.getNameById("\""+entry.getKey().toString()+"\""));
                    output.setLeader(accountManager.getNameById("\""+entry.getKey().toString()+"\""), entry.getKey());
                }
                output.addMemberToList(accountManager.getNameById("\""+entry.getKey().toString()+"\""), entry.getKey());
            }
            

            // //Get Leader 
            // String leaderUsername = restTemplate.postForObject(urlLeader, input, String.class);
            // leaderUsername = leaderUsername.replaceAll("^\"|\"$", "");

            // //Get UUID Of Leader
            // String leaderUid = restTemplate.postForObject(urlGetUid, leaderUsername, String.class);
            // leaderUid = leaderUid.replaceAll("^\"|\"$", "");

            // UUID leaderID = UUID.fromString(leaderUid);
            // output.setLeader(leaderUsername, leaderID);
        } catch (AccountErrorException e) {
            throw new GetTeamErrorException();
        }
    }
}
