package ssl.ois.timelog.service.activity.type.list;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.repository.user.UnitRepository;

@Service
public class ListActivityTypeUseCase {

    private UnitRepository unitRepository;

    public ListActivityTypeUseCase(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public void execute(ListActivityTypeUseCaseInput input, ListActivityTypeUseCaseOutput output)
            throws DatabaseErrorException {

        final String urlName = "http://localhost:8080/team/get/name";


        for(int i = 0 ; i < input.getUnitIdList().size(); i++){
            UnitInterface user = this.unitRepository.findByUserID(input.getUnitIdList().get(i));
            RestTemplate restTemplate = new RestTemplate();
            String userName = restTemplate.postForObject(urlName, input.getUnitIdList().get(i), String.class);
            userName = userName.replaceAll("\"","");

            output.addUnitDTOtoList(user.getID().toString(), userName, user.getActivityTypeList());
        }
    }
}