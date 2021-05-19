package ssl.ois.timelog.service.activity.type.list;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.user.UnitRepository;

@Service
public class ListActivityTypeUseCase {

    private AccountManager amsManager;
    private UnitRepository unitRepository;

    public ListActivityTypeUseCase(UnitRepository unitRepository, AccountManager amsManager) {
        this.unitRepository = unitRepository;
        this.amsManager = amsManager;
    }

    public void execute(ListActivityTypeUseCaseInput input, ListActivityTypeUseCaseOutput output)
            throws DatabaseErrorException, GetActivityTypeErrorException {

        final String urlName = "http://localhost:8080/team/get/name";




        for(int i = 0 ; i < input.getUnitIdList().size(); i++){
            UnitInterface user = this.unitRepository.findByUserID(input.getUnitIdList().get(i));
            RestTemplate restTemplate = new RestTemplate();
            String userName = "";
            try{
                userName = amsManager.getNameById(input.getUnitIdList().get(i));
            }
            catch(AccountErrorException e){
                throw new GetActivityTypeErrorException();
            }
            
            userName = userName.replaceAll("\"","");
            // String userName = restTemplate.postForObject(urlName, input.getUnitIdList().get(i), String.class);
            // userName = userName.replaceAll("\"","");

            output.addUnitDTOtoList(user.getID().toString(), userName, user.getActivityTypeList());
        }
    }
}