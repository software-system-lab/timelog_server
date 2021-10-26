package ssl.ois.timelog.model.activity_type;

import java.util.UUID;

public class ActivityType {
    private final UUID id;
    private String activityName;
    private UUID unitId;
    private Boolean isEnable;
    private Boolean isPrivate;
    private Boolean isDeleted;

    public ActivityType(UUID id, String activityName, UUID unitId, Boolean isEnable, Boolean isPrivate, Boolean isDeleted) {
        this.id = id;
        this.activityName = activityName;
        this.unitId = unitId;
        this.isEnable = isEnable;
        this.isPrivate = isPrivate;
        this.isDeleted = isDeleted;
    }

    public ActivityType(String activityName, UUID unitId, Boolean isEnable, Boolean isPrivate, Boolean isDeleted) {
        this.id = UUID.randomUUID();
        this.activityName = activityName;
        this.unitId = unitId;
        this.isEnable = isEnable;
        this.isPrivate = isPrivate;
        this.isDeleted = isDeleted;
    }

    public UUID getId() {
        return id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public void setUnitId(UUID unitId) {
        this.unitId = unitId;
    }

    public Boolean isEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
