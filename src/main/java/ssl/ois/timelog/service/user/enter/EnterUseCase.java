package ssl.ois.timelog.service.user.enter;

import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.activityType.ActivityTypeListRepository;
import ssl.ois.timelog.service.repository.user.UserRepository;

import java.util.UUID;
import java.util.ArrayList;

public class EnterUseCase {
    private UserRepository userRepository;
    private ActivityTypeListRepository activityTypeListRepository;
    private LogRepository logRepository;

    public EnterUseCase(UserRepository userRepository, ActivityTypeListRepository activityTypeListRepository, LogRepository logRepository) {
        this.userRepository = userRepository;
        this.activityTypeListRepository = activityTypeListRepository;
        this.logRepository = logRepository;
    }

    public void execute(EnterUseCaseInput input, EnterUseCaseOutput output) {
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
            output.setActivityTypeList(this.activityTypeListRepository.findByUserID(userID).getTypeList());
            output.setLogList(this.logRepository.getByUserID(userID));
        }
    }
}
