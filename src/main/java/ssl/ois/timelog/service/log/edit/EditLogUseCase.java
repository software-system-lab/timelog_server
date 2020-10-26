package ssl.ois.timelog.service.log.edit;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.repository.log.LogRepository;

@Service
public class EditLogUseCase {
    private LogRepository logRepository;

    public EditLogUseCase(LogRepository logRepo) {
        this.logRepository = logRepo;
    }

    public void execute(EditLogUseCaseInput input, EditLogUseCaseOutput output) throws GetLogErrorException, SaveLogErrorException {
        UUID userID = UUID.fromString(input.getUserID());
        Log log = new Log(userID, input.getTitle(), input.getStartTime(), input.getEndTime(), input.getDescription(), input.getActivityTypeName());

        logRepository.update(log, input.getLogID());
        output.setLogID(log.getID().toString());
    }
}
