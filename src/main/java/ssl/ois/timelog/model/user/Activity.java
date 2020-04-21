package ssl.ois.timelog.model.user;

public class Activity {
    private String name;
    private boolean isEnable;
    private boolean isPrivate;

    Activity(String name) {
        this.name = name;
        this.isEnable = true;
        this.isPrivate = false;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    boolean isEnable() {
        return isEnable;
    }

    void setEnable(boolean enable) {
        isEnable = enable;
    }

    boolean isPrivate() {
        return isPrivate;
    }

    void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
