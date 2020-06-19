package ssl.ois.timelog.service.repository.user;

import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;

public interface UserRepository {
    public void save(User userDTO) throws DatabaseErrorException;
    public User findByUserID(String userID) throws DatabaseErrorException;
}
