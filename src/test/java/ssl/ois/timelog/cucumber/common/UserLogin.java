package ssl.ois.timelog.cucumber.common;


import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.user.UnitRepository;

public class UserLogin {
    private String userID;
    private UnitInterface user;
    private UnitRepository unitRepository;

    public UserLogin(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public void process(String userID)
            throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException {
        this.user = this.unitRepository.findByUserID(userID);
        if (this.user == null) {
            this.user = new User(UUID.fromString(userID));
            this.unitRepository.save(this.user);

            ActivityType activityTypeOther = new ActivityType("Other", true, false);
            ActivityType activityTypeLabProject = new ActivityType("LabProject", true, false);
            ActivityType activityTypeLabDuty = new ActivityType("LabDuty", true, false);
            this.user.addActivityType(activityTypeOther);
            this.unitRepository.addActivityType(this.user);

            this.user.addActivityType(activityTypeLabProject);
            this.unitRepository.addActivityType(this.user);

            this.user.addActivityType(activityTypeLabDuty);
            this.unitRepository.addActivityType(this.user);

            this.userID = this.user.getID().toString();
        }
    }

    public String getUserID() {
        return userID;
    }

    public UnitInterface getUser() {
        return this.user;
    }
}
