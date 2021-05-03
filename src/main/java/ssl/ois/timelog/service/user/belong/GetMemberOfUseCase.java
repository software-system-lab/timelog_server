package ssl.ois.timelog.service.user.belong;

import java.util.UUID;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.web.client.RestClientException;
import ssl.ois.timelog.service.exception.team.GetMemberOfErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import org.springframework.web.client.RestTemplate;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.team.Team;
import ssl.ois.timelog.service.exception.team.InitTeamDataErrorException;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;



@Service
public class GetMemberOfUseCase {
    private UnitRepository unitRepository;

    public GetMemberOfUseCase(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public void execute(GetMemberOfUseCaseInput input, GetMemberOfUseCaseOutput output)throws GetMemberOfErrorException, InitTeamDataErrorException, DuplicateActivityTypeException {
        try {
            final String urlGetTeamName = "http://localhost:8080/get/groups/byuser";
            final String ulrGetTeamUid = "http://localhost:8080/get/teamUid";
            RestTemplate restTemplate = new RestTemplate();
            List<String> result = restTemplate.postForObject(urlGetTeamName, input, List.class);

            for(int i = 0; i < result.size(); i++) {
                String uid = restTemplate.postForObject(ulrGetTeamUid, result.get(i), String.class);
                uid = uid.replaceAll("^\"|\"$", "");
                UUID teamID = UUID.fromString(uid);

                UnitInterface team = this.unitRepository.findByUserID(uid);
                if(team == null){
                    team = new Team(teamID);
                    this.unitRepository.insertTeamToUnit(team);

                    ActivityType activityType = new ActivityType("Other", true, false);
                    team.addActivityType(activityType);
                    this.unitRepository.addActivityType(team);
                }
                output.addTeamToList(result.get(i) , teamID);
            }
        } catch (RestClientException e) {
            System.out.println(e);
            throw new GetMemberOfErrorException();
        } catch (DatabaseErrorException | DuplicateActivityTypeException e) {
            System.out.println(e);
            throw new InitTeamDataErrorException();
        } 
    }
}
