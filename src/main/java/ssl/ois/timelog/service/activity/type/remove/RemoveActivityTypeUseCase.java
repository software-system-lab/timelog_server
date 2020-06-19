package ssl.ois.timelog.service.activity.type.remove;

import ssl.ois.timelog.service.exception.activityType.ActivityTypeNotExistException;
import ssl.ois.timelog.service.repository.activityType.ActivityTypeRepository;

public class RemoveActivityTypeUseCase {

    private ActivityTypeRepository activityTypeRepository;

    public RemoveActivityTypeUseCase(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public void execute(RemoveActivityTypeUseCaseInput input, RemoveActivityTypeUseCaseOutput output)
            throws ActivityTypeNotExistException {
        this.activityTypeRepository.removeActivityType(input.getUserID(), input.getActivityTypeName());

        output.setActivityTypeName(input.getActivityTypeName());
    }

    // public void execute(RemoveActivityTypeUseCaseInput input, RemoveActivityTypeUseCaseOutput output)
    //         throws GetActivityTypeErrorException, SaveActivityTypeErrorException {
    //     ActivityTypeList activityTypeList = this.activityTypeListRepository.findByUserID(input.getUserID());
    //     activityTypeList.removeType(input.getActivityTypeName());
       
    //     this.activityTypeListRepository.update(activityTypeList);
    //     output.setActivityTypeName(input.getActivityTypeName());
    // }
}
