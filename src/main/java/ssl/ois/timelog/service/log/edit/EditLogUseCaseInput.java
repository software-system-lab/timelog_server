package ssl.ois.timelog.service.log.edit;

public class EditLogUseCaseInput {
    private String id;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private String activityTypeId;

    public EditLogUseCaseInput(){}

    public EditLogUseCaseInput(
            String id, String title, String startTime, String endTime,
            String description, String activityTypeId, String createBy) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.activityTypeId = activityTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
