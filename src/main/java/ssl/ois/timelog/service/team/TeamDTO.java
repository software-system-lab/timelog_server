package ssl.ois.timelog.service.team;

import ssl.ois.timelog.service.user.UserDTO;

import java.util.List;

public class TeamDTO {
    private String id;
    private String name;
    private UserDTO leader;
    private List<UserDTO> members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDTO getLeader() {
        return leader;
    }

    public void setLeader(UserDTO leader) {
        this.leader = leader;
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }
}
