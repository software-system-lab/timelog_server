package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.service.repository.UserRepository;
import ssl.ois.timelog.service.user.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {
    private Map<String, UserDTO> users;

    public MemoryUserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void save(UserDTO userDTO) {
        this.users.put(userDTO.getID().toString(), userDTO);
    }

    @Override
    public UserDTO findByUserID(String userID) {
        return this.users.get(userID);
    }
}
