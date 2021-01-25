package ssl.ois.timelog.service.team.get;

import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestClientException;
import ssl.ois.timelog.service.exception.team.GetTeamErrorException;
import ssl.ois.timelog.service.user.belong.GetTeamUseCaseInput;
import ssl.ois.timelog.service.user.belong.GetTeamUseCaseOutput;
import org.springframework.web.client.RestTemplate;

@Service
public class GetTeamUseCase {

    public void execute(GetTeamUseCaseInput input, GetTeamUseCaseOutput output)throws GetTeamErrorException {
        try {
            final String urlMember = "http://localhost:8080/get/members";
            final String urlGetUid = "http://localhost:8080/get/uuid";
            final String urlLeader = "http://localhost:8080/get/leader";

            RestTemplate restTemplate = new RestTemplate();
            List<String> result = restTemplate.postForObject(urlMember, input, List.class);
            for(int i = 0; i < result.size(); i++) {
                String uid = restTemplate.postForObject(urlGetUid, result.get(i), string.class);
                List<String> person = new ArrayList<String>();
                person.add(uid);
                person.add(result.get(i));
                output.addMemberToList(person); 
            }
            String leaderUsername = restTemplate.postForObject(urlLeader, input, string.class);
            String leaderUid = restTemplate.postForObject(urlGetUid, leaderUsername, string.class);
            List<String> leader = new ArrayList<String>();
            person.add(leaderUid);
            person.add(leaderUsername);
            output.setLeader(leader); 

        } catch (RestClientException e) {
            throw new GetMemberOfErrorException();
        }
    }
}
