package ssl.ois.timelog.service.user.dto;

import ssl.ois.timelog.service.DTO;

public class UserDTO extends DTO{
    private String name;

    public UserDTO(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}