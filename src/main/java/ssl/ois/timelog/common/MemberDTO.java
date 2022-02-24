package ssl.ois.timelog.common;

import ssl.ois.timelog.model.team.Role;

public class MemberDTO {
    private String userId;
    private String username;
    private String displayName;
    private String email;
    private Role role;

    public MemberDTO() { }

    public MemberDTO(String userId, String username, String displayName, String email, Role role) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
