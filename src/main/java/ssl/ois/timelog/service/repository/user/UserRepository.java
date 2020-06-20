package ssl.ois.timelog.service.repository.user;

import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;

public interface UserRepository {
    public void save(User user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException;
    public User findByUserID(String userID) throws DatabaseErrorException;
}
