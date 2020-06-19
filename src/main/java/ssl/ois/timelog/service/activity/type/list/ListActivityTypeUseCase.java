package ssl.ois.timelog.service.activity.type.list;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeRepository;

@Service
public class ListActivityTypeUseCase {

    private ActivityTypeRepository activityTypeRepository;

    public ListActivityTypeUseCase(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public void execute(ListActivityTypeUseCaseInput input, ListActivityTypeUseCaseOutput output)
            throws DatabaseErrorException {
        output.setActivityTypeList(activityTypeRepository.getActivityTypeList(input.getUserID()));
    }
}