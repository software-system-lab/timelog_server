package ssl.ois.timelog.ut.service.log;

import org.junit.Before;
import org.junit.Test;
import ssl.ois.timelog.adapter.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.memory.MemoryUserRepository;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.log.LogRecord;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.log.RecordInput;
import ssl.ois.timelog.service.log.RecordOutput;
import ssl.ois.timelog.service.user.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LogRecordTest {
    private User user;
    private UserRepository userRepository;
    private LogRepository logRepository;

    @Before
    public void setup() {
        this.user = new User("David");
        this.userRepository = new MemoryUserRepository();
        this.logRepository = new MemoryLogRepository();
    }

    @Test
    public void testLogRecord() {
        String title = "Study for Design Pattern";
        String startTime = "2020/04/21 15:00";
        String endTime = "2020/04/21 18:00";
        String description = "Composite Pattern";
        RecordInput inputData = new RecordInput(this.user.getUserID().toString(),
                title, startTime, endTime, description);
        RecordOutput outputData = new RecordOutput();
        LogRecord logRecord = new LogRecord(this.userRepository, this.logRepository);
        logRecord.execute(inputData, outputData);
        assertNotNull(outputData.getLogID());
    }

}
