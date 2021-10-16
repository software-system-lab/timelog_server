package ssl.ois.timelog.service.log.add;

public class AddLogUseCaseInput {
    private String unitId;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private String activityTypeId;
    private String createBy;

    public AddLogUseCaseInput(){}

    public AddLogUseCaseInput(String unitId, String title, String startTime, String endTime, String description, String activityTypeId, String createBy) {
        this.unitId = unitId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.activityTypeId = activityTypeId;
        this.createBy = createBy;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
