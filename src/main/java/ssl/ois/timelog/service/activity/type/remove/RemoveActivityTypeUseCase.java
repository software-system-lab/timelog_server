package ssl.ois.timelog.service.activity.type.remove;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import java.util.UUID;

@Service
public class RemoveActivityTypeUseCase {

    private UnitRepository unitRepository;

    public RemoveActivityTypeUseCase(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public void execute(RemoveActivityTypeUseCaseInput input, RemoveActivityTypeUseCaseOutput output)
            throws DatabaseErrorException, ActivityTypeNotExistException, DuplicateActivityTypeException {
        UnitInterface user = this.unitRepository.findByUserID(input.getUserID());
        UUID activityUserMapperID = this.unitRepository.findActivityUserMapperID(input.getUserID(),input.getActivityTypeName());

        user.removeActivityType(input.getActivityTypeName());

        this.unitRepository.removeActivityType(user);
        output.setActivityTypeName(input.getActivityTypeName());
    }
}
