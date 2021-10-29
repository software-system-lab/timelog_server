package ssl.ois.timelog.service.activity_type.list;

import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.exception.DatabaseErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;

import java.util.List;

public class ListActivityTypeUseCase {
    private final ActivityTypeRepository activityTypeRepository;

    public ListActivityTypeUseCase(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public void execute(ListActivityTypeUseCaseInput input, ListActivityTypeUseCaseOutput output) throws DatabaseErrorException {
        List<ActivityType> activityTypeList = activityTypeRepository.findByUnitId(input.getUnitId());
        output.setActivityTypes(activityTypeList);
    }
}
