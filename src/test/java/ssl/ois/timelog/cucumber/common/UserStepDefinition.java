package ssl.ois.timelog.cucumber.common;

import static org.junit.Assert.fail;

import java.util.UUID;

import io.cucumber.java.en.Given;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.activity.type.add.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.activityType.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.repository.activityType.ActivityTypeRepository;
import ssl.ois.timelog.service.repository.user.UserRepository;

public class UserStepDefinition {
    private String userID;
    private UserRepository userRepository;
    private ActivityTypeRepository activityTypeRepository;

    public UserStepDefinition() {
        this.userRepository = new MemoryUserRepository();
        this.activityTypeRepository = new MemoryActivityTypeRepository();
    }
    
    @Given("I log in to Timelog with user ID {string}")
    public void i_log_in_to_Timelog_with_user_ID(String userID) {
        if (this.userRepository.findByUserID(userID) == null) {
            this.userRepository.save(new User(UUID.fromString(userID)));

            ActivityType activityType = new ActivityType("Other");
            try {
                this.activityTypeRepository.addActivityType(userID, activityType);
            } catch (DuplicateActivityTypeException e) {
                fail(e.getMessage());
            }
        }

        this.userID = userID;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public String getUserID() {
        return userID;
    }
}
