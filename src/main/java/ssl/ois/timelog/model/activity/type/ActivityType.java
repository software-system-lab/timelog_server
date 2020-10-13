package ssl.ois.timelog.model.activity.type;

public class ActivityType {
    private String name;
    private boolean isEnable;
    private boolean isPrivate;
    private boolean isDeleted;

    public ActivityType(String name, Boolean isEnable, Boolean isPrivate, Boolean isDeleted) {
        this.name = name;
        this.isEnable = isEnable;
        this.isPrivate = isPrivate;
        this.isDeleted = isDeleted;
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
