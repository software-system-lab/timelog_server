package ssl.ois.timelog.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssl.ois.timelog.model.log.Log;

import java.net.ConnectException;
import java.util.UUID;

@Service
public class AddLogUseCase {
    private LogRepository logRepository;

    public AddLogUseCase(@Autowired LogRepository logRepo) {
        this.logRepository = logRepo;
    }
    public void execute(AddLogUseCaseInput input, AddLogUseCaseOutput output) {
        UUID userID = UUID.fromString(input.getUserID());
        Log log = new Log(userID, input.getTitle(), input.getStartTime(), input.getEndTime(), input.getDescription(), input.getActivityName());
        try {
            logRepository.save(log);
        } catch (ConnectException e) {
            return;
        }
        output.setLogID(log.getLogID().toString());
    }
}
