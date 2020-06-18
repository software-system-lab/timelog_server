package ssl.ois.timelog.model.activity.type;

import java.util.UUID;

public class ActivityType {
    private String name;
    private boolean isEnable;
    private boolean isPrivate;

    public ActivityType(String name) {
        this.name = name;
        this.isEnable = true;
        this.isPrivate = false;
    }

    public ActivityType(String name, Boolean isEnable, Boolean isPrivate) {
        this.name = name;
        this.isEnable = isEnable;
        this.isPrivate = isPrivate;
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
}
