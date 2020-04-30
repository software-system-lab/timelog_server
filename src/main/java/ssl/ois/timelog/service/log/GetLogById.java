package ssl.ois.timelog.service.log;

import ssl.ois.timelog.model.log.Log;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetLogById {
    private LogRepository logRepository;

    public GetLogById(@Autowired LogRepository repo) {
        this.logRepository = repo;
    }

    public void execute(GetByIdInput input, GetByIdOutput output) {
        Log log = this.logRepository.getByID(UUID.fromString(input.getLogID()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        output.setLogId(log.getLogID().toString());
        output.setTitle(log.getTitle());
        output.setStartTime(dateFormat.format(log.getStartTime()));
        output.setEndTime(dateFormat.format(log.getEndTime()));
        output.setActivityType(log.getActivityType());
        output.setDescription(log.getDescription());
    }
}
