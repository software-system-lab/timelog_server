package ssl.ois.timelog.service.log.list;

import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.exception.AccountErrorException;
import ssl.ois.timelog.exception.DatabaseErrorException;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListLogUseCase {
    private final LogRepository logRepository;

    public ListLogUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void execute(ListLogUseCaseInput input, ListLogUseCaseOutput output)
            throws ParseException, DatabaseErrorException, AccountErrorException {
        Date endDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        endDate = dateFormat.parse(input.getEndDate());
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        List<Log> logList = this.logRepository.findByPeriod(input.getUnitId(),
                input.getStartDate(), dateFormat.format(endDate));

        output.setLogDTOList(logList);
    }
}
