package csie.ntut.edu.tw.timelog.service;

import csie.ntut.edu.tw.timelog.model.Log;
import csie.ntut.edu.tw.timelog.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LogService {
    private LogRepository logRepo;

    @Autowired
    public LogService(LogRepository logRepo) {
        this.logRepo = logRepo;
    }

    public boolean newLog(Log log) {
        System.out.println("********************");
        System.out.println(log);
        System.out.println("********************");
        Log result = this.logRepo.save(log);
        return result.equals(log);
    }
}
