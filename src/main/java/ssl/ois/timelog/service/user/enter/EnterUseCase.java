package ssl.ois.timelog.service.user.enter;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.user.InitUserDataErrorException;
import ssl.ois.timelog.service.repository.user.UnitRepository;

import java.util.UUID;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.logging.LogFactory;

@Service
public class EnterUseCase {
    private UnitRepository unitRepository;

    public EnterUseCase(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }


    public void execute(EnterUseCaseInput input, EnterUseCaseOutput output) throws DuplicateActivityTypeException,
            InitUserDataErrorException {
        Logger logger = Logger.getLogger(this.getClass().toString());
        try {
            String userID = input.getUserID();
            Unit user = this.unitRepository.findByUnitID(userID);
            if (user == null) {
                // First time login to Timelog
    
                // Create User
                user = new User(UUID.fromString(userID));
                this.unitRepository.save(user);
    
                // Create default activity type "Other" for the user.
                ActivityType activityType = new ActivityType("Other", true, false);
                user.addActivityType(activityType);
                this.unitRepository.addActivityType(user);

                ActivityType labDuty = new ActivityType("LabDuty", true, false);
                user.addActivityType(labDuty);
                this.unitRepository.addActivityType(user);

                ActivityType labProject = new ActivityType("LabProject", true, false);
                user.addActivityType(labProject);
                this.unitRepository.addActivityType(user);

                output.setActivityTypeList(this.unitRepository.findByUnitID(userID).getActivityTypeList());
                output.setLogList(new ArrayList<>());
            } else {
                output.setActivityTypeList(user.getActivityTypeList());
            }
        } catch (DatabaseErrorException | ActivityTypeNotExistException e) {
            logger.warning(e.getMessage());
            throw new InitUserDataErrorException(input.getUserID());
        }
    }
}