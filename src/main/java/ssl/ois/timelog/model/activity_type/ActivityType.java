package ssl.ois.timelog.model.activity_type;

import java.util.UUID;

public class ActivityType {
    private final UUID id;
    private String activityName;
    private UUID unitId;
    private boolean isEnable;
    private boolean isPrivate;
    private boolean isDeleted;

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

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
