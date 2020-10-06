package ssl.ois.timelog.service.activity.type.remove;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.user.UserRepository;

@Service
public class RemoveActivityTypeUseCase {

    private UserRepository userRepository;

    public RemoveActivityTypeUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(RemoveActivityTypeUseCaseInput input, RemoveActivityTypeUseCaseOutput output)
            throws DatabaseErrorException, ActivityTypeNotExistException, DuplicateActivityTypeException {
        User user = this.userRepository.findByUserID(input.getUserID());

        user.deleteActivityType(input.getActivityTypeName());

        this.userRepository.deleteActivityType(user);
        output.setActivityTypeName(input.getActivityTypeName());
    }
}
