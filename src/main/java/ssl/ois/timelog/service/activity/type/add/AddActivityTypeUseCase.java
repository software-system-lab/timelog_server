package ssl.ois.timelog.service.activity.type.add;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeRepository;

public class AddActivityTypeUseCase {
    private ActivityTypeRepository activityTypeRepository;

    public AddActivityTypeUseCase(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public void execute(AddActivityTypeUseCaseInput input, AddActivityTypeUseCaseOutput output)
            throws DuplicateActivityTypeException, DatabaseErrorException {
        ActivityType activityType = new ActivityType(input.getActivityTypeName());

        this.activityTypeRepository.addActivityType(input.getUserID(), activityType);
        output.setActivityTypeName(activityType.getName());
    }
}
