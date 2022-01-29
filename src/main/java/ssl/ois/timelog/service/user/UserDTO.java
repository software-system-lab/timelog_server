package ssl.ois.timelog.service.user;

public class UserDTO {
    private String userId;
    private String userName;
    private String displayName;
    private String email;

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }
}
