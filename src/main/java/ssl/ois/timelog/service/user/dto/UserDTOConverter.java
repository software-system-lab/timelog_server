package ssl.ois.timelog.service.user.dto;

import java.util.UUID;

import ssl.ois.timelog.model.user.User;

public class UserDTOConverter {

    private UserDTOConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getID().toString());
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(UUID.fromString(userDTO.getID()));
    }
    
}