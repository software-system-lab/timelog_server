package ssl.ois.timelog.service.activity.type.remove;

public class RemoveActivityTypeUseCaseInput {
    private String activityTypeName;
    private String targetActivityTypeName;
    private String userID;
    private Boolean isEnable;
    private Boolean isPrivate;

    public String getTargetActivityTypeName() {
        return this.targetActivityTypeName;
    }

    public void setTargetActivityTypeName(String targetActivityTypeName) {
        this.targetActivityTypeName = targetActivityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID(){
        return this.userID;
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

}
