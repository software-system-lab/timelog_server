package ssl.ois.timelog.service.log.edit;

import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.exception.log.GetLogErrorException;
import ssl.ois.timelog.exception.log.SaveLogErrorException;
import ssl.ois.timelog.model.log.Log;

import java.text.ParseException;
import java.util.UUID;

public class EditLogUseCase {
    private final LogRepository logRepository;

    public EditLogUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void execute(EditLogUseCaseInput input) throws GetLogErrorException, SaveLogErrorException, ParseException {
        Log log = logRepository.findById(input.getId());

        // update log from input
        log.setTitle(input.getTitle());
        log.setStartTime(SqlDateTimeConverter.toDate(input.getStartTime()));
        log.setEndTime(SqlDateTimeConverter.toDate(input.getEndTime()));
        log.setActivityTypeId(input.getActivityTypeId());

        logRepository.updateLog(log);

//        Log log = new Log(
//                input.getId(),
//                input.getTitle(),
//                SqlDateTimeConverter.toDate(input.getStartTime()),
//                SqlDateTimeConverter.toDate(input.getEndTime()),
//                input.getDescription(),
//                input.getActivityTypeId(),
//                input.getCreateBy()
//        );
    }
}
