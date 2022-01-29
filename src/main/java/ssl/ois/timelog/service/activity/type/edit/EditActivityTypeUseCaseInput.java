package ssl.ois.timelog.service.activity.type.edit;

public class EditActivityTypeUseCaseInput {
    private String targetActivityTypeName;
    private String activityTypeName;
    private boolean isEnable;
    private boolean isPrivate;
    private String unitID;

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

    public boolean getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean getIsPrivate() {
        return this.isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getUnitID() {
        return this.unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
}