package ssl.ois.timelog.service.activity_type.edit;

import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;

import java.text.ParseException;

public class EditActivityTypeUseCase {
    private final ActivityTypeRepository activityTypeRepository;

    public EditActivityTypeUseCase(ActivityTypeRepository activityTypeRepository){this.activityTypeRepository = activityTypeRepository;}

    public void execute(EditActivityTypeUseCaseInput input) throws SaveActivityTypeErrorException, ParseException, GetActivityTypeErrorException {
        ActivityType activityType = activityTypeRepository.findById(input.getId());

        if(input.getActivityName() != null){
            activityType.setActivityName(input.getActivityName());
        }

        if(input.isEnable() != null){
            activityType.setEnable(input.isEnable());
        }

        if(input.isPrivate() != null){
            activityType.setPrivate(input.isPrivate());
        }

        activityTypeRepository.updateActivityType(activityType);
    }
}
