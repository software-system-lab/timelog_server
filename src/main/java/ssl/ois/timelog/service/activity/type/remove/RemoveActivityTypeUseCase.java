package ssl.ois.timelog.service.activity.type.remove;

import ssl.ois.timelog.model.user.ActivityTypeList;
import ssl.ois.timelog.service.repository.ActivityTypeRepository;

public class RemoveActivityTypeUseCase {
    private ActivityTypeRepository activityTypeRepository;

    public RemoveActivityTypeUseCase(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }
    public void execute(RemoveActivityTypeUseCaseInput input, RemoveActivityTypeUseCaseOutput output) {
        ActivityTypeList activityTypeList = this.activityTypeRepository.findByUserID(input.getUserID());
        activityTypeList.removeType(input.getActivityTypeName());
       
        this.activityTypeRepository.update(activityTypeList);
        output.setActivityTypeName(input.getActivityTypeName());
    }
}
