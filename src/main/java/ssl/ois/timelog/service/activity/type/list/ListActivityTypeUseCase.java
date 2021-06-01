package ssl.ois.timelog.service.activity.type.list;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.connect.Unit;
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
        for(int i = 0 ; i < input.getUnitIdList().size(); i++){
            Unit user = this.unitRepository.findByUnitID(input.getUnitIdList().get(i));
            String userName = "";
            try{
                userName = amsManager.getNameById(input.getUnitIdList().get(i));
            }
            catch(AccountErrorException e){
                throw new GetActivityTypeErrorException();
            }
            
            userName = userName.replaceAll("\"","");

            output.addUnitDTOtoList(user.getID().toString(), userName, user.getActivityTypeList());
        }
    }
}