package ssl.ois.timelog.service.log.edit;

import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.exception.log.GetLogErrorException;
import ssl.ois.timelog.exception.log.SaveLogErrorException;
import ssl.ois.timelog.model.log.Log;

import java.text.ParseException;

public class EditLogUseCase {

    private final LogRepository logRepository;

    public EditLogUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void execute(EditLogUseCaseInput input) throws GetLogErrorException, SaveLogErrorException, ParseException {
        Log log = logRepository.findById(input.getId());

        // update log from input
        if(input.getTitle() != null){
            log.setTitle(input.getTitle());
        }

        if(input.getStartTime() != null){
            log.setStartTime(SqlDateTimeConverter.toDate(input.getStartTime()));
        }

        if(input.getEndTime() != null){
            log.setEndTime(SqlDateTimeConverter.toDate(input.getEndTime()));
        }

        if(input.getActivityTypeId() != null){
            log.setActivityTypeId(input.getActivityTypeId());
        }

        if(input.getDescription() != null){
            log.setDescription(input.getDescription());
        }

        logRepository.updateLog(log);
    }
}
