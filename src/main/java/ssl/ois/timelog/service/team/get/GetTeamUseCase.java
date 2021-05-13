package ssl.ois.timelog.service.team.get;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.client.RestClientException;
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


            //Get UUID of members
            RestTemplate restTemplate = new RestTemplate();
            List<String> result = restTemplate.postForObject(urlMember, input, List.class);

            //Get Name of Member
            for(int i = 0; i < result.size(); i++) {
                String username = restTemplate.postForObject(urlGetName, result.get(i), String.class);
                UUID userID = UUID.fromString(result.get(i).replaceAll("^\"|\"$", ""));

                output.addMemberToList(username , userID); 
            }

            //Get Leader 
            String leaderUsername = restTemplate.postForObject(urlLeader, input, String.class);
            leaderUsername = leaderUsername.replaceAll("^\"|\"$", "");

            //Get UUID Of Leader
            String leaderUid = restTemplate.postForObject(urlGetUid, leaderUsername, String.class);
            leaderUid = leaderUid.replaceAll("^\"|\"$", "");

            UUID leaderID = UUID.fromString(leaderUid);
            output.setLeader(leaderUsername, leaderID);
        } catch (RestClientException e) {
            throw new GetTeamErrorException();
        }
    }
}
