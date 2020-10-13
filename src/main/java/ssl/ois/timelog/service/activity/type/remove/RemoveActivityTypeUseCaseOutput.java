package ssl.ois.timelog.service.activity.type.remove;

public class RemoveActivityTypeUseCaseOutput {
    private String activityTypeName;
    private Boolean isDeleted;

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
