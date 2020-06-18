package ssl.ois.timelog.service.activity.type.edit;

public class EditActivityTypeUseCaseInput {
    private String oldActivityTypeName;
    private String newActivityTypeName;
    private Boolean isEnable;
    private Boolean isPrivate;
    private String userID;

    public String getOldActivityTypeName() {
        return this.oldActivityTypeName;
    }

    public void setOldActivityTypeName(String oldActivityTypeName) {
        this.oldActivityTypeName = oldActivityTypeName;
    }

    public String getNewActivityTypeName() {
        return this.newActivityTypeName;
    }

    public void setNewActivtiyTypeName(String newActivityTypeName) {
        this.newActivityTypeName = newActivityTypeName;
    }

    public Boolean getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Boolean getIsPrivate() {
        return this.isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}