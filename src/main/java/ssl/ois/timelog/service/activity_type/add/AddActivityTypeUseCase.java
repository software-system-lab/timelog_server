package ssl.ois.timelog.service.activity_type.add;

import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;

import java.text.ParseException;
import java.util.UUID;

public class AddActivityTypeUseCase {
    private final ActivityTypeRepository activityTypeRepository;

    public AddActivityTypeUseCase(ActivityTypeRepository activityTypeRepository){this.activityTypeRepository = activityTypeRepository;}

    public void execute(AddActivityTypeUseCaseInput input) throws SaveActivityTypeErrorException, DuplicateActivityTypeException, ParseException {
        try{
            ActivityType activityType = activityTypeRepository.findByNameAndUnitId(input.getActivityName(), input.getUnitId());
            if(activityType.isDeleted()){
                activityType.setDeleted(false);
                activityTypeRepository.editActivityType(activityType);
            } else {
                throw new DuplicateActivityTypeException();
            }
        }catch(GetActivityTypeErrorException e){
            ActivityType activityType = new ActivityType(
                    input.getActivityName(),
                    UUID.fromString(input.getUnitId()),
                    input.isEnable(),
                    input.isPrivate(),
                    false
            );
            activityTypeRepository.addActivityType(activityType);
        }
    }
}
