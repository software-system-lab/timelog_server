package ssl.ois.timelog.service.activity.type.add;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import java.util.UUID;

@Service
public class AddActivityTypeUseCase {
    private UnitRepository unitRepository;

    public AddActivityTypeUseCase(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public void execute(AddActivityTypeUseCaseInput input, AddActivityTypeUseCaseOutput output)
            throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException {
        UnitInterface user = this.unitRepository.findByUserID(input.getUserID());
        UUID activityUserMapperID = this.unitRepository.findActivityUserMapperID(input.getUserID(),input.getActivityTypeName());
        ActivityType activityType;
        if(activityUserMapperID == null){
            activityType = new ActivityType(input.getActivityTypeName(), input.getIsEnable(), input.getIsPrivate());
        } else {
            activityType = new ActivityType(activityUserMapperID,input.getActivityTypeName(), input.getIsEnable(), input.getIsPrivate());
        }
        user.addActivityType(activityType);
        this.unitRepository.addActivityType(user);

        output.setActivityTypeName(activityType.getName());
    }
}
