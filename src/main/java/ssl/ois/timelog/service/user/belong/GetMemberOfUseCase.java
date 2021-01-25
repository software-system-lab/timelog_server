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
            final String url = "http://localhost:8080/get/groups/byuser";

            RestTemplate restTemplate = new RestTemplate();
            List<String> result = restTemplate.postForObject(url, input, List.class);
            output.setMemberOfList(result);
        } catch (RestClientException e) {
            throw new GetMemberOfErrorException();
        }
    }
}
