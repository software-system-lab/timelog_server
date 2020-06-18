package ssl.ois.timelog.cucumber.common;

import static org.junit.Assert.fail;

import java.util.UUID;

import io.cucumber.java.en.Given;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeListRepository;
import ssl.ois.timelog.service.repository.user.UserRepository;

public class UserStepDefinition {
    private String userID;
    private UserRepository userRepository;
    private ActivityTypeListRepository activityTypeListRepository;

    public UserStepDefinition() {
        this.userRepository = new MemoryUserRepository();
        this.activityTypeListRepository = new MemoryActivityTypeListRepository();
    }
    
    @Given("I log in to Timelog with user ID {string}")
    public void i_log_in_to_Timelog_with_user_ID(String userID) {
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

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public ActivityTypeListRepository getAcivityTypeListRepository() {
        return this.activityTypeListRepository;
    }

    public String getUserID() {
        return userID;
    }
}
