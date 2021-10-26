package ssl.ois.timelog.service.activity_type.remove;

import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.RemoveActivityTypeException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;

public class RemoveActivityTypeUseCase {
    private final ActivityTypeRepository activityTypeRepository;

    public RemoveActivityTypeUseCase(ActivityTypeRepository activityTypeRepository){this.activityTypeRepository = activityTypeRepository;}

    public void execute(RemoveActivityTypeUseCaseInput input) throws RemoveActivityTypeException, GetActivityTypeErrorException {
        ActivityType activityType = activityTypeRepository.findById(input.getId());

        activityType.setDeleted(true);

        try{
            activityTypeRepository.updateActivityType(activityType);
        }catch (SaveActivityTypeErrorException e){
            throw new RemoveActivityTypeException(input.getId());
        }
    }
}
