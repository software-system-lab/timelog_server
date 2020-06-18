package ssl.ois.timelog.service.activity.type.remove;

import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.exception.activityType.GetActivityTypeErrorException;
import ssl.ois.timelog.service.exception.activityType.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.repository.activityType.ActivityTypeListRepository;

public class RemoveActivityTypeUseCase {
    private ActivityTypeListRepository activityTypeListRepository;

    public RemoveActivityTypeUseCase(ActivityTypeListRepository activityTypeListRepository) {
        this.activityTypeListRepository = activityTypeListRepository;
    }

    public void execute(RemoveActivityTypeUseCaseInput input, RemoveActivityTypeUseCaseOutput output)
            throws GetActivityTypeErrorException, SaveActivityTypeErrorException {
        ActivityTypeList activityTypeList = this.activityTypeListRepository.findByUserID(input.getUserID());
        activityTypeList.removeType(input.getActivityTypeName());
       
        this.activityTypeListRepository.update(activityTypeList);
        output.setActivityTypeName(input.getActivityTypeName());
    }
}
