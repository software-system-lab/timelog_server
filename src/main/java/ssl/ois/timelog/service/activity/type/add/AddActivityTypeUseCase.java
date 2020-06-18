package ssl.ois.timelog.service.activity.type.add;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.exception.activityType.GetActivityTypeErrorException;
import ssl.ois.timelog.service.exception.activityType.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.repository.activityType.ActivityTypeListRepository;

public class AddActivityTypeUseCase {
    private ActivityTypeListRepository activityTypeListRepository;

    public AddActivityTypeUseCase(ActivityTypeListRepository activityTypeListRepository) {
        this.activityTypeListRepository = activityTypeListRepository;
    }

    public void execute(AddActivityTypeUseCaseInput input, AddActivityTypeUseCaseOutput output)
            throws GetActivityTypeErrorException, DuplicateActivityTypeException, SaveActivityTypeErrorException {
        ActivityTypeList activityTypeList = this.activityTypeListRepository.findByUserID(input.getUserID());
        
        for(ActivityType activityType: activityTypeList.getTypeList()) {
            if(activityType.getName().equals(input.getActivityTypeName())) {
                throw new DuplicateActivityTypeException();
            }
        }
        
        activityTypeList.newType(input.getActivityTypeName());
        this.activityTypeListRepository.update(activityTypeList);
        output.setActivityTypeName(input.getActivityTypeName());
    }
}
