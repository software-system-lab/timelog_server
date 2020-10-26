package ssl.ois.timelog.service.activity.type.remove;

public class RemoveActivityTypeUseCaseOutput {
    private String activityTypeName;
    private Boolean isDeleted;

    public String getActivityTypeName() {
        return this.activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
