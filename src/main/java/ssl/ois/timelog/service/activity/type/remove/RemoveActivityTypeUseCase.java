package ssl.ois.timelog.service.activity.type.remove;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeRepository;

@Service
public class RemoveActivityTypeUseCase {

    private ActivityTypeRepository activityTypeRepository;

    public RemoveActivityTypeUseCase(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public void execute(RemoveActivityTypeUseCaseInput input, RemoveActivityTypeUseCaseOutput output)
            throws ActivityTypeNotExistException, DatabaseErrorException {
        this.activityTypeRepository.removeActivityType(input.getUserID(), input.getActivityTypeName());

        output.setActivityTypeName(input.getActivityTypeName());
    }
}
