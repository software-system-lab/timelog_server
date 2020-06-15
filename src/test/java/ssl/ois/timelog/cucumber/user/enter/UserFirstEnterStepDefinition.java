package ssl.ois.timelog.cucumber.user.enter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.repository.ActivityTypeListRepository;
import ssl.ois.timelog.service.repository.UserRepository;
import ssl.ois.timelog.service.user.enter.EnterUseCase;
import ssl.ois.timelog.service.user.enter.EnterUseCaseInput;
import ssl.ois.timelog.service.user.enter.EnterUseCaseOutput;

public class UserFirstEnterStepDefinition {
    private String userID;
    private UserRepository userRepository;
    private ActivityTypeListRepository activityTypeListRepository;
    private LogRepository logRepository;
    private EnterUseCaseOutput enterUseCaseOutput;

    @Before
    public void setup() {
        this.userRepository = new MemoryUserRepository();
        this.activityTypeListRepository = new MemoryActivityTypeListRepository();
        this.logRepository = new MemoryLogRepository();
    }

    @Given("My user ID is {string}")
    public void my_user_ID_is(String userID) {
        this.userID = userID;
    }

    @When("I first time enter Timelog")
    public void i_first_time_enter_Timelog() {
        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.activityTypeListRepository, this.logRepository);
        EnterUseCaseInput enterUseCaseInput = new EnterUseCaseInput();
        enterUseCaseInput.setUserID(this.userID);
        this.enterUseCaseOutput = new EnterUseCaseOutput();

        enterUseCase.execute(enterUseCaseInput, enterUseCaseOutput);
    }

    @Then("I will get my activity type list that only contains {string}")
    public void i_will_get_my_activity_type_list_that_only_contains(String activityTypeName) {
        // assertion for output of use case
        List<ActivityType> activityTypeListFromOutput = this.enterUseCaseOutput.getActivityTypeList();
        assertEquals(1, activityTypeListFromOutput.size());
        assertEquals(activityTypeName, activityTypeListFromOutput.get(0).getName());

        // verify that the activity type is actually stored
        List<ActivityType> activityTypeListFromRepo = this.activityTypeListRepository.findByUserID(this.userID).getTypeList();
        assertEquals(1, activityTypeListFromRepo.size());
        assertEquals(activityTypeName, activityTypeListFromRepo.get(0).getName());
    }

    @Then("I will get my log list that contains nothing")
    public void i_will_get_my_log_list_that_contains_nothing() {
        List<Log> logs = this.enterUseCaseOutput.getLogList();
        assertTrue(logs.isEmpty(), "The log list is not empty");
    }
}
