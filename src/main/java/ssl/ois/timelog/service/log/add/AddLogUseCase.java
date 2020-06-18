package ssl.ois.timelog.service.log.add;

import java.net.ConnectException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.repository.log.LogRepository;

@Service
public class AddLogUseCase {
    private LogRepository logRepository;

    public AddLogUseCase(LogRepository logRepo) {
        this.logRepository = logRepo;
    }
    public void execute(AddLogUseCaseInput input, AddLogUseCaseOutput output) {
        UUID userID = UUID.fromString(input.getUserID());
        Log log = new Log(userID, input.getTitle(), input.getStartTime(), input.getEndTime(), input.getDescription(), input.getActivityTypeName());
        try {
            logRepository.save(log);
        } catch (ConnectException e) {
            return;
        }
        output.setLogID(log.getID().toString());
    }
}
