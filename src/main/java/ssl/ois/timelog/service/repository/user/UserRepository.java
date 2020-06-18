package ssl.ois.timelog.service.repository.user;

import ssl.ois.timelog.model.user.User;

public interface UserRepository {
    public void save(User userDTO);
    public User findByUserID(String userID);
}
