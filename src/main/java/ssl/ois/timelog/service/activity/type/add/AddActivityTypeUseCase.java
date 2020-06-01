package ssl.ois.timelog.service.activity.type.add;

import ssl.ois.timelog.model.user.ActivityTypeList;
import ssl.ois.timelog.service.repository.ActivityTypeRepository;

public class AddActivityTypeUseCase {
    private ActivityTypeRepository activityTypeRepository;

    public AddActivityTypeUseCase(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }
    public void execute(AddActivityTypeUseCaseInput input, AddActivityTypeUseCaseOutput output) {
        ActivityTypeList activityTypeList = this.activityTypeRepository.findByUserID(input.getUserID());
        activityTypeList.newType(input.getActivityTypeName());
        this.activityTypeRepository.update(activityTypeList);
        output.setActivityTypeName(input.getActivityTypeName());
    }
}
