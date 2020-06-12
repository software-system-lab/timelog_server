package ssl.ois.timelog.service.user.enter;

import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.ActivityTypeListRepository;
import ssl.ois.timelog.service.repository.UserRepository;
import ssl.ois.timelog.service.user.dto.UserDTO;
import ssl.ois.timelog.service.user.dto.UserDTOConverter;

import java.util.UUID;

public class EnterUseCase {
    private UserRepository userRepository;
    private ActivityTypeListRepository activityTypeListRepository;

    public EnterUseCase(UserRepository userRepository, ActivityTypeListRepository activityTypeListRepository) {
        this.userRepository = userRepository;
        this.activityTypeListRepository = activityTypeListRepository;
    }

    public void execute(EnterUseCaseInput input, EnterUseCaseOutput output) {
        String userID = input.getUserID();
        UserDTO userDTO = this.userRepository.findByUserID(userID);

        ActivityTypeList activityTypeList;
        if (userDTO == null) {
            // First time login to Timelog

            // Create User
            User user = new User(UUID.fromString(userID), input.getUserName());
            this.userRepository.save(UserDTOConverter.toDTO(user));

            // Create ActivityTypeList for the user.
            activityTypeList = new ActivityTypeList(userID);
            activityTypeList.newType("Others");
            this.activityTypeListRepository.save(activityTypeList);
            output.setActivityTypeList(activityTypeList);
        } 
    }
}
