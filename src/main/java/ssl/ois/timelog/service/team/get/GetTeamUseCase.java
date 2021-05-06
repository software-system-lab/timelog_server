package ssl.ois.timelog.service.team.get;

import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.client.RestClientException;
import ssl.ois.timelog.service.exception.team.GetTeamErrorException;
import ssl.ois.timelog.service.team.get.GetTeamUseCaseInput;
import ssl.ois.timelog.service.team.get.GetTeamUseCaseOutput;
import org.springframework.web.client.RestTemplate;

@Service
public class GetTeamUseCase {

    public void execute(GetTeamUseCaseInput input, GetTeamUseCaseOutput output)throws GetTeamErrorException {
        try {
            System.out.println("GetTeamUseCase");
            final String urlMember = "http://localhost:8080/team/get/members";
            final String urlGetName = "http://localhost:8080/team/get/name";
            final String urlLeader = "http://localhost:8080/team/get/leader";
            final String urlGetUid = "http://localhost:8080/team/get/uuid/user";

            RestTemplate restTemplate = new RestTemplate();
            List<String> result = restTemplate.postForObject(urlMember, input, List.class);
            System.out.println(result);
            for(int i = 0; i < result.size(); i++) {
                String username = restTemplate.postForObject(urlGetName, result.get(i), String.class);
                UUID userID = UUID.fromString(result.get(i).replaceAll("^\"|\"$", ""));

                output.addMemberToList(username , userID); 
            }
            String leaderUsername = restTemplate.postForObject(urlLeader, input, String.class);
            leaderUsername = leaderUsername.replaceAll("^\"|\"$", "");

            String leaderUid = restTemplate.postForObject(urlGetUid, leaderUsername, String.class);
            leaderUid = leaderUid.replaceAll("^\"|\"$", "");

            UUID leaderID = UUID.fromString(leaderUid);
            output.setLeader(leaderUsername, leaderID);
        } catch (RestClientException e) {
            throw new GetTeamErrorException();
        }
    }
}
