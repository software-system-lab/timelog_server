package ssl.ois.timelog.service.log.add;

import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.exception.log.SaveLogErrorException;

import java.text.ParseException;

public class AddLogUseCase {
    private final LogRepository logRepository;

    public AddLogUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void execute(AddLogUseCaseInput input) throws SaveLogErrorException, ParseException {
        Log log = new Log(
                input.getUnitId(),
                input.getTitle(),
                SqlDateTimeConverter.toDate(input.getStartTime()),
                SqlDateTimeConverter.toDate(input.getEndTime()),
                input.getDescription(),
                input.getActivityTypeId(),
                input.getCreateBy()
        );
        logRepository.addLog(log);
    }
}
