package ssl.ois.timelog.service.user.belong;

import java.util.UUID;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestClientException;
import ssl.ois.timelog.service.exception.team.GetMemberOfErrorException;
import ssl.ois.timelog.service.user.belong.GetMemberOfUseCaseInput;
import ssl.ois.timelog.service.user.belong.GetMemberOfUseCaseOutput;
import org.springframework.web.client.RestTemplate;

@Service
public class GetMemberOfUseCase {

    public void execute(GetMemberOfUseCaseInput input, GetMemberOfUseCaseOutput output)throws GetMemberOfErrorException {
        try {
            final String urlGetTeamName = "http://localhost:8080/get/groups/byuser";
            final String ulrGetTeamUid = "http://localhost:8080/get/teamUid";
            RestTemplate restTemplate = new RestTemplate();
            List<String> result = restTemplate.postForObject(urlGetTeamName, input, List.class);

            for(int i = 0; i < result.size(); i++) {
                String uid = restTemplate.postForObject(ulrGetTeamUid, result.get(i), String.class);
                uid = uid.replaceAll("^\"|\"$", "");

                UUID teamID = UUID.fromString(uid);

                output.addTeamToList(result.get(i) , teamID);
            }
        } catch (RestClientException e) {
            throw new GetMemberOfErrorException();
        }
    }
}
