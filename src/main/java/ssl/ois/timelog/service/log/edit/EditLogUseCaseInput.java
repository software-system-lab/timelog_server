package ssl.ois.timelog.service.log.edit;

public class EditLogUseCaseInput {
    private String logID;
    private String userID;
    private String activityUnitID;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private String activityTypeName;

    public EditLogUseCaseInput() {
        //empty constructor
    }

    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getActivityUnitID() {
        return activityUnitID;
    }

    public void setActivityUnitID(String activityUnitID) {
        this.activityUnitID = activityUnitID;
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

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

}
