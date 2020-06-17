package ssl.ois.timelog.service.activity.type.edit;

import ssl.ois.timelog.service.repository.ActivityTypeListRepository;

public class EditActivityTypeUseCase {

    private ActivityTypeListRepository activityTypeListRepository;

    public EditActivityTypeUseCase(ActivityTypeListRepository activityTypeListRepository) {
        this.activityTypeListRepository = activityTypeListRepository;
    }
    
    public void execute(EditActivityTypeUseCaseInput input, EditActivityTypeUseCaseOutput output) {
        
    }

}