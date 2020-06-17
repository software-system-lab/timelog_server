package ssl.ois.timelog.service.activity.type.edit;

public class EditActivityTypeUseCaseInput {
    private String activityTypeName;
    private Boolean isEnable;
    private Boolean isPrivate;

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
}