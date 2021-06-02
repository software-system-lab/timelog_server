package ssl.ois.timelog.service.activity.type.edit;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.user.UnitRepository;

import java.util.UUID;

@Service
public class EditActivityTypeUseCase {

    private UnitRepository unitRepository;

    public EditActivityTypeUseCase(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public void execute(EditActivityTypeUseCaseInput input, EditActivityTypeUseCaseOutput output)
            throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException {
        Unit user = this.unitRepository.findByUnitID(input.getUnitID());
        UUID activityUserMapperID = this.unitRepository.findActivityUserMapperID(input.getUnitID(),input.getTargetActivityTypeName());
        
        ActivityType activityType = new ActivityType(activityUserMapperID,input.getActivityTypeName(), input.getIsEnable(), input.getIsPrivate());

        user.editActivityType(input.getTargetActivityTypeName(), activityType);
        this.unitRepository.editActivityType(user);
        output.setActivityTypeName(activityType.getName());
        output.setIsEnable(activityType.isEnable());
        output.setIsPrivate(activityType.isPrivate());
    }
}
