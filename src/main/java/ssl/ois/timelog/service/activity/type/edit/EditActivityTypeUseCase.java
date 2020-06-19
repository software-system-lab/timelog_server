package ssl.ois.timelog.service.activity.type.edit;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeRepository;

@Service
public class EditActivityTypeUseCase {

    private ActivityTypeRepository activityTypeRepository;

    public EditActivityTypeUseCase(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public void execute(EditActivityTypeUseCaseInput input, EditActivityTypeUseCaseOutput output)
            throws ActivityTypeNotExistException, DatabaseErrorException {
        ActivityType activityType = new ActivityType(input.getNewActivityTypeName(), input.getIsEnable(), input.getIsPrivate());

        this.activityTypeRepository.updateActivityType(input.getUserID(),
            input.getOldActivityTypeName(), activityType);

        output.setActivityTypeName(activityType.getName());
    }
}
