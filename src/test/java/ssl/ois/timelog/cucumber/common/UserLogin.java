package ssl.ois.timelog.cucumber.common;

import static org.junit.Assert.fail;

import java.util.UUID;

import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeListRepository;
import ssl.ois.timelog.service.repository.user.UserRepository;

public class UserLogin {
    private String userID;
    private UserRepository userRepository;
    private ActivityTypeListRepository activityTypeListRepository;

    public UserLogin(UserRepository userRepository, ActivityTypeListRepository activityTypeListRepository) {
        this.userRepository = userRepository;
        this.activityTypeListRepository = activityTypeListRepository;
    }
    
    public void process(String userID) {
        if (this.userRepository.findByUserID(userID) == null) {
            this.userRepository.save(new User(UUID.fromString(userID)));

            ActivityTypeList activityTypeList = new ActivityTypeList(userID);
            activityTypeList.newType("Others");
            try {
                this.activityTypeListRepository.save(activityTypeList);
            } catch (SaveActivityTypeErrorException e) {
                fail(e.getMessage());
            }
        }

        this.userID = userID;
    }
    public String getUserID() {
        return userID;
    }
}
