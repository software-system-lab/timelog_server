package ssl.ois.timelog.service.activity.type.remove;

public class RemoveActivityTypeUseCaseInput {
    private String activityTypeName;
    private String userID;
    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID(){
        return userID;
    }
}
