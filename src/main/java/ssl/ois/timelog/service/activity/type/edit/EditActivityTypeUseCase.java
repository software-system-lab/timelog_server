package ssl.ois.timelog.service.activity.type.edit;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.exception.activityType.GetActivityTypeErrorException;
import ssl.ois.timelog.service.exception.activityType.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.repository.activityType.ActivityTypeListRepository;

public class EditActivityTypeUseCase {

    private ActivityTypeListRepository activityTypeListRepository;

    public EditActivityTypeUseCase(ActivityTypeListRepository activityTypeListRepository) {
        this.activityTypeListRepository = activityTypeListRepository;
    }

    public void execute(EditActivityTypeUseCaseInput input, EditActivityTypeUseCaseOutput output)
            throws GetActivityTypeErrorException, SaveActivityTypeErrorException {
        ActivityType activityType = new ActivityType(input.getNewActivityTypeName(), input.getIsEnable(), input.getIsPrivate());
        
        ActivityTypeList activityTypeList = this.activityTypeListRepository.findByUserID(input.getUserID());
        activityTypeList.updateType(input.getOldActivityTypeName(), activityType);

        this.activityTypeListRepository.update(activityTypeList);

        output.setActivityTypeName(activityType.getName());
    }

}