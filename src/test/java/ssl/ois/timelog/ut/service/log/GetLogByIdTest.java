package ssl.ois.timelog.ut.service.log;

import org.junit.Before;
import org.junit.Test;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.log.*;
import ssl.ois.timelog.service.user.UserRepository;

import static org.junit.Assert.assertEquals;

public class GetLogByIdTest {
    private User user;
    private LogRepository logRepository;
    private UserRepository userRepository;
    private String logID;

    @Before
    public void setup() {
        this.user = new User("Tim");
        this.logRepository = new MemoryLogRepository();
        this.userRepository = new MemoryUserRepository();

        String logTitle = "Study for Design Pattern";
        String startTime = "2020/04/21 15:00";
        String endTime = "2020/04/21 18:00";
        String description = "Composite Pattern";
        RecordInput inputData = new RecordInput(this.user.getUserID().toString(),
                logTitle, startTime, endTime, description);
        RecordOutput outputData = new RecordOutput();

        LogRecord logRecord = new LogRecord(this.logRepository);
        logRecord.execute(inputData, outputData);
        this.logID = outputData.getLogID();
    }

    @Test
    public void getLogById() {
        GetByIdInput input = new GetByIdInput();
        GetByIdOutput output = new GetByIdOutput();
        GetLogById service = new GetLogById(this.logRepository);

        input.setLogID(this.logID);

        service.execute(input, output);

        assertEquals(this.logID, output.getLogId());
        assertEquals("Study for Design Pattern", output.getTitle());
        assertEquals("2020/04/21 15:00", output.getStartTime());
        assertEquals("2020/04/21 18:00", output.getEndTime());
        assertEquals("Composite Pattern", output.getDescription());
        assertEquals("Others", output.getActivityType());
    }
}