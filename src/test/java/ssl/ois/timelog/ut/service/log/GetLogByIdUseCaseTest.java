package ssl.ois.timelog.ut.service.log;

import org.junit.Before;
import org.junit.Test;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.log.add.*;
import ssl.ois.timelog.service.log.getByID.*;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class GetLogByIdUseCaseTest {
    private LogRepository logRepository;
    private String logID;

    @Before
    public void setup() {
        User user = new User(UUID.randomUUID());
        this.logRepository = new MemoryLogRepository();

        String logTitle = "Study for Design Pattern";
        String startTime = "2020/04/21 15:00";
        String endTime = "2020/04/21 18:00";
        String description = "Composite Pattern";
        AddLogUseCaseInput inputData = new AddLogUseCaseInput(user.getID().toString(),
                logTitle, startTime, endTime, description);
        AddLogUseCaseOutput outputData = new AddLogUseCaseOutput();
        AddLogUseCase addLogUseCase = new AddLogUseCase(this.logRepository);
        addLogUseCase.execute(inputData, outputData);
        this.logID = outputData.getLogID();
    }

    @Test
    public void getLogById() {
        GetLogByIdUseCaseInput input = new GetLogByIdUseCaseInput();
        GetLogByIdUseCaseOutput output = new GetLogByIdUseCaseOutput();
        GetLogByIdUseCase service = new GetLogByIdUseCase(this.logRepository);

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