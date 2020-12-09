package ssl.ois.timelog.model.activity.type;

import java.util.UUID;

public class ActivityType {
    private final UUID id;
    private String name;
    private boolean isEnable;
    private boolean isPrivate;
    private boolean isDeleted;

    public ActivityType(UUID activity_id, String name, Boolean isEnable, Boolean isPrivate, Boolean isDeleted) {
        this.id = activity_id;
        this.name = name;
        this.isEnable = isEnable;
        this.isPrivate = isPrivate;
        this.isDeleted = isDeleted;
    }

    public ActivityType(String name, Boolean isEnable, Boolean isPrivate, Boolean isDeleted) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.isEnable = isEnable;
        this.isPrivate = isPrivate;
        this.isDeleted = isDeleted;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
