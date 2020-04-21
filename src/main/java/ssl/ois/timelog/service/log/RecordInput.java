package ssl.ois.timelog.service.log;

import java.util.UUID;

public class RecordInput {
    private UUID userID;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private String activityName;

    public UUID getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public String getActivityName() {
        return activityName;
    }

    public RecordInput(UUID userID, String title, String startTime, String endTime, String description) {
        this.userID = userID;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.activityName = "Others";
    }
}
