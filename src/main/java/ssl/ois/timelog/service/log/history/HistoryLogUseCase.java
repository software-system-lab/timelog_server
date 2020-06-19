package ssl.ois.timelog.service.log.history;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.repository.log.LogRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryLogUseCase {
    private LogRepository logRepository;

    public HistoryLogUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void execute(HistoryLogUseCaseInput input, HistoryLogUseCaseOutput output) {
        Date endDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            endDate = dateFormat.parse(input.getEndDate());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        List<Log> logList = this.logRepository.findByPeriod(input.getUserID(),
                input.getStartDate(), dateFormat.format(endDate));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        List<LogDTO> logDTOList = new ArrayList<>();
        for (Log log: logList) {
            LogDTO logDTO = new LogDTO();
            logDTO.setActivityTypeName(log.getActivityTypeName());
            logDTO.setTitle(log.getTitle());
            logDTO.setStartTime(simpleDateFormat.format(log.getStartTime()));
            logDTO.setEndTime(simpleDateFormat.format(log.getEndTime()));
            logDTOList.add(logDTO);
        }

        output.setLogDTOList(logDTOList);
    }
}
