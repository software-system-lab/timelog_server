package ssl.ois.timelog.service.repository;

import ssl.ois.timelog.service.user.dto.UserDTO;

public interface UserRepository {
    public void save(UserDTO userDTO);
    public UserDTO findByUserID(String userID);
}
