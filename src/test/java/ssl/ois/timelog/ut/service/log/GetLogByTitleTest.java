package ssl.ois.timelog.ut.service.log;

import org.junit.Before;
import org.junit.Test;
import ssl.ois.timelog.controller.memory.MemoryLogRepository;
import ssl.ois.timelog.controller.memory.MemoryUserRepository;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.log.*;
import ssl.ois.timelog.service.user.UserRepository;

import static org.junit.Assert.assertEquals;

public class GetLogByTitleTest {
    private User user;
    private LogRepository logRepository;
    private UserRepository userRepository;
    private String logTitle;

    @Before
    public void setup() {
        this.user = new User("Tim");
        this.logRepository = new MemoryLogRepository();
        this.userRepository = new MemoryUserRepository();

        this.logTitle = "Study for Design Pattern";
        String startTime = "2020/04/21 15:00";
        String endTime = "2020/04/21 18:00";
        String description = "Composite Pattern";
        RecordInput inputData = new RecordInput(this.user.getUserID().toString(),
                this.logTitle, startTime, endTime, description);
        RecordOutput outputData = new RecordOutput();

        LogRecord logRecord = new LogRecord(this.userRepository, this.logRepository);
        logRecord.execute(inputData, outputData);
    }

    @Test
    public void getLogByTitle() {
        GetByTitleInput input = new GetByTitleInput();
        GetByTitleOutput output = new GetByTitleOutput();
        GetLogByTitle service = new GetLogByTitle(this.logRepository);

        input.setUserID(this.user.getUserID().toString());
        input.setTitle(this.logTitle);

        service.execute(input, output);

        assertEquals("Timelog development", output.getTitle());
        assertEquals("2020/04/21 15:00", output.getStartTime());
        assertEquals("2020/04/21 18:00", output.getEndTime());
        assertEquals("Composite Pattern", output.getDescription());
        assertEquals("Others", output.getActivityType());
    }
}