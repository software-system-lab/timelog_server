package ssl.ois.timelog.cucumber.common;

import static org.junit.Assert.fail;

import java.util.UUID;

import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.repository.user.UserRepository;

public class UserLogin {
    private String userID;
    private UserRepository userRepository;

    public UserLogin(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void process(String userID) {
//        try {
//            if (this.userRepository.findByUserID(userID) == null) {
//                this.userRepository.save(new User(UUID.fromString(userID)));
//
//                try {
//                    this.userRepository.save(activityTypeList);
//                } catch (SaveActivityTypeErrorException e) {
//                    fail(e.getMessage());
//                }
//            }
//
//            this.userID = userID;
//        } catch (DatabaseErrorException e) {
//            e.printStackTrace();
//        }

        this.userID = userID;
    }
    public String getUserID() {
        return userID;
    }
}
