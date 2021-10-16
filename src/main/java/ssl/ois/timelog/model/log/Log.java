package ssl.ois.timelog.model.log;

import java.util.Date;
import java.util.UUID;

public class Log {
    private final String id;
    private String unitId;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private String activityTypeId;
    private String activityTypeName;
    private String createBy;

    public Log(final String id,
               final String unitId,
               final String title,
               final Date startTime,
               final Date endTime,
               final String description,
               final String activityTypeId,
               final String activityTypeName,
               final String createBy) {
        this.id = id;
        this.unitId = unitId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityTypeName = activityTypeName;
        this.activityTypeId = activityTypeId;
        this.createBy = createBy;
    }

    public Log(final String unitId,
               final String title,
               final Date startTime,
               final Date endTime,
               final String description,
               final String activityTypeId,
               final String createBy) {
        this.id = UUID.randomUUID().toString();
        this.unitId = unitId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityTypeId = activityTypeId;
        this.createBy = createBy;
    }

    public int getMinutes() {
        final long time = this.endTime.getTime() - this.startTime.getTime();
        return (int)(time / (60 * 1000));
    }

    public double getHours() {
        final long time = this.endTime.getTime() - this.startTime.getTime();
        return (double)time / (60 * 60 * 1000);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(final String unitId) {
        this.unitId = unitId;
    }

    public String getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(final String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(final String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(final String createBy) {
        this.createBy = createBy;
    }

}
