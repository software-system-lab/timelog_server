package ssl.ois.timelog.service.user.enter;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.exception.activityType.GetActivityTypeErrorException;
import ssl.ois.timelog.service.exception.activityType.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.user.InitUserDataErrorException;
import ssl.ois.timelog.service.repository.activityType.ActivityTypeListRepository;
import ssl.ois.timelog.service.repository.user.UserRepository;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class EnterUseCase {
    private UserRepository userRepository;
    private ActivityTypeListRepository activityTypeListRepository;
    private LogRepository logRepository;

    public EnterUseCase(UserRepository userRepository, ActivityTypeListRepository activityTypeListRepository,
            LogRepository logRepository) {
        this.userRepository = userRepository;
        this.activityTypeListRepository = activityTypeListRepository;
        this.logRepository = logRepository;
    }

    public void execute(EnterUseCaseInput input, EnterUseCaseOutput output)
            throws SaveActivityTypeErrorException, InitUserDataErrorException {
        String userID = input.getUserID();
        User user = this.userRepository.findByUserID(userID);

        ActivityTypeList activityTypeList;
        if (user == null) {
            // First time login to Timelog

            // Create User
            this.userRepository.save(new User(UUID.fromString(userID)));

            // Create ActivityTypeList for the user.
            activityTypeList = new ActivityTypeList(userID);
            activityTypeList.newType("Others");
            this.activityTypeListRepository.save(activityTypeList);
            output.setActivityTypeList(activityTypeList.getTypeList());

            output.setLogList(new ArrayList<Log>());
        } else {
            try {
                List<ActivityType> activities = this.activityTypeListRepository.findByUserID(userID).getTypeList();
                output.setActivityTypeList(activities);
                output.setLogList(this.logRepository.getByUserID(userID));
            } catch (GetActivityTypeErrorException | GetLogErrorException e) {
                throw new InitUserDataErrorException(userID);
            }
        }
    }
}
