package ssl.ois.timelog.model.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Log {
    private UUID id;
    private UUID userID;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private String activityTypeName;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd HH:mm");

    public Log(UUID logID,
               UUID userID,
               String title,
               String startTime,
               String endTime,
               String description,
               String activityTypeName) {
        this.id = logID;
        this.title = title;
        this.userID = userID;
        this.activityTypeName = activityTypeName;
        this.description = description;
        try {
            this.startTime = dateFormat.parse(startTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid Start Time: " + startTime);
        }
        try {
            this.endTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid End Time format: " + endTime);
        }
    }

    public Log(UUID userID,
               String title,
               String startTime,
               String endTime,
               String description,
               String activityTypeName) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.userID = userID;
        this.activityTypeName = activityTypeName;
        this.description = description;
        try {
            this.startTime = dateFormat.parse(startTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid Start Time: " + startTime);
        }
        try {
            this.endTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid End Time format: " + endTime);
        }
    }

    public int getMinutes() {
        long time = this.endTime.getTime() - this.startTime.getTime();
        return (int)(time / (60 * 1000));
    }

    public double getHours() {
        long time = this.endTime.getTime() - this.startTime.getTime();
        return (double)time / (60 * 60 * 1000);
    }

    public UUID getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }
}
