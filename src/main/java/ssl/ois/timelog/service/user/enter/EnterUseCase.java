package ssl.ois.timelog.service.user.enter;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.activityType.ActivityTypeRepository;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.activity.type.add.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.activityType.GetActivityTypeErrorException;
import ssl.ois.timelog.service.exception.activityType.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.user.InitUserDataErrorException;
import ssl.ois.timelog.service.repository.user.UserRepository;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class EnterUseCase {
    private UserRepository userRepository;
    private ActivityTypeRepository activityTypeRepository;
    private LogRepository logRepository;

    public EnterUseCase(UserRepository userRepository, ActivityTypeRepository activityTypeRepository,
            LogRepository logRepository) {
        this.userRepository = userRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.logRepository = logRepository;
    }

    public void execute(EnterUseCaseInput input, EnterUseCaseOutput output) throws DuplicateActivityTypeException,
            InitUserDataErrorException {
        String userID = input.getUserID();
        User user = this.userRepository.findByUserID(userID);

        if (user == null) {
            // First time login to Timelog

            // Create User
            user = new User(UUID.fromString(userID));
            this.userRepository.save(user);
            
            // Create ActivityTypeList for the user.
            ActivityType activityType = new ActivityType("Other");
            this.activityTypeRepository.addActivityType(user.getID().toString(), activityType);

            List<ActivityType> activityTypeList = new ArrayList<>();
            activityTypeList.add(activityType);
            output.setActivityTypeList(activityTypeList);

            output.setLogList(new ArrayList<Log>());
        } else {
            output.setActivityTypeList(this.activityTypeRepository.getActivityTypeList(userID));
            try {
                output.setLogList(this.logRepository.getByUserID(userID));
            } catch (GetLogErrorException e) {
                throw new InitUserDataErrorException(userID);
            }
        }
    }
}
