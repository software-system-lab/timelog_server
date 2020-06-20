package ssl.ois.timelog.service.activity.type.add;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeRepository;
import ssl.ois.timelog.service.repository.user.UserRepository;

@Service
public class AddActivityTypeUseCase {
    private UserRepository userRepository;
    // private ActivityTypeRepository activityTypeRepository;

    // public AddActivityTypeUseCase(UserRepository userRepository, ActivityTypeRepository activityTypeRepository) {
    //     this.userRepository = userRepository;
    //     this.activityTypeRepository = activityTypeRepository;
    // }

    public AddActivityTypeUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(AddActivityTypeUseCaseInput input, AddActivityTypeUseCaseOutput output)
            throws DuplicateActivityTypeException, DatabaseErrorException {
        User user = this.userRepository.findByUserID(input.getUserID());
        ActivityType activityType = new ActivityType(input.getActivityTypeName(), input.getIsEnable(), input.getIsPrivate());
        user.addActivityType(activityType);

        this.userRepository.save(user);

        output.setActivityTypeName(activityType.getName());

        // ActivityType activityType = new ActivityType(input.getActivityTypeName());

        // this.activityTypeRepository.addActivityType(input.getUserID(), activityType);
        // output.setActivityTypeName(activityType.getName());
    }
}
