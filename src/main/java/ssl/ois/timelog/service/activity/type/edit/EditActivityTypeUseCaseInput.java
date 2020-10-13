package ssl.ois.timelog.service.activity.type.edit;

public class EditActivityTypeUseCaseInput {
    private String targetActivityTypeName;
    private String activityTypeName;
    private Boolean isEnable;
    private Boolean isPrivate;
    private Boolean isDeleted;
    private String userID;

    public String getTargetActivityTypeName() {
        return this.targetActivityTypeName;
    }

    public void setTargetActivityTypeName(String targetActivityTypeName) {
        this.targetActivityTypeName = targetActivityTypeName;
    }

    public String getActivityTypeName() {
        return this.activityTypeName;
    }

    public void setActivtiyTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
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

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}