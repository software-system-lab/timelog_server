package ssl.ois.timelog.cucumber.common;


import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.user.UserRepository;

public class UserLogin {
    private String userID;
    private User user;
    private UserRepository userRepository;

    public UserLogin(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void process(String userID)
            throws Exception {
        this.user = this.userRepository.findByUserID(userID);
        if (this.user == null) {
            this.user = new User(UUID.fromString(userID));
            this.userRepository.save(this.user);

            ActivityType activityType = new ActivityType("Other", true, false);
            this.user.addActivityType(activityType);
            this.userRepository.save(this.user);
            this.userID = this.user.getID().toString();
        }
    }

    public String getUserID() {
        return userID;
    }

    public User getUser() {
        return this.user;
    }
}
