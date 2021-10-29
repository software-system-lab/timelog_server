package ssl.ois.timelog.ut.service.log;

import org.junit.Before;
import org.junit.Test;
import ssl.ois.timelog.adapter.manager.memory.MemoryAMSManager;
import ssl.ois.timelog.adapter.presenter.log.history.LogHistoryPresenter;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUnitRepository;
import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.log.add.AddLogUseCase;
import ssl.ois.timelog.service.log.add.AddLogUseCaseInput;
import ssl.ois.timelog.service.log.add.AddLogUseCaseOutput;
import ssl.ois.timelog.service.log.history.HistoryLogUseCase;
import ssl.ois.timelog.service.log.history.HistoryLogUseCaseInput;
import ssl.ois.timelog.service.log.history.HistoryLogUseCaseOutput;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.user.UnitRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExecuteDashBoardCaseTest {
    private LogRepository logRepository;
    private UnitRepository unitRepository;
    private AccountManager accountManager;
    private User user;

    private String logID;
    private HistoryLogUseCaseOutput output;
    private String logTitle = "Study for Design Pattern";
    private String startTime = "2020/04/21 15:00";
    private String endTime = "2020/04/21 18:00";
    private String activityTypeName = "DP";



    @Before
    public void setup() {
        this.user = new User(UUID.randomUUID());
        logRepository = new MemoryLogRepository();
        unitRepository = new MemoryUnitRepository();
        HashMap<String, Unit> users = new HashMap<>();
        users.put("user", user);
        accountManager = new MemoryAMSManager(users);
        String description = "Composite Pattern";

        AddLogUseCaseInput inputData = new AddLogUseCaseInput(user.getID().toString(), user.getID().toString(), this.logTitle, this.startTime, this.endTime,
                description, this.activityTypeName);
        AddLogUseCaseOutput outputData = new AddLogUseCaseOutput();
        AddLogUseCase addLogUseCase = new AddLogUseCase(this.logRepository);
        try {
            addLogUseCase.execute(inputData, outputData);
        } catch (SaveLogErrorException | DatabaseErrorException e) {
            fail(e.getMessage());
        }
        this.logID = outputData.getLogID();
    }

    @Test
    public void executeDashBoard() {
        HistoryLogUseCaseInput input = new HistoryLogUseCaseInput();
        this.output =  new LogHistoryPresenter();
        HistoryLogUseCase service = new HistoryLogUseCase(this.logRepository, unitRepository, accountManager);
        List<String> filterList = new ArrayList<>();
        filterList.add(this.activityTypeName);

        input.setUserID(this.user.getID().toString());
        input.setStartDate(this.startTime);
        input.setEndDate(this.endTime);
        input.setFilterList(filterList);

        try {
            service.execute(input, this.output);
        } catch (ParseException | DatabaseErrorException | AccountErrorException e) {
            fail(e.getMessage());
        }

        for (LogDTO log: output.getLogDTOList()) {
            assertEquals(this.logID, log.getId().toString());
            assertEquals(this.logTitle, log.getTitle());
            assertEquals(this.startTime, log.getStartTime());
            assertEquals(this.endTime, log.getEndTime());
            assertEquals(this.activityTypeName, log.getActivityTypeName());
        }
    }
}