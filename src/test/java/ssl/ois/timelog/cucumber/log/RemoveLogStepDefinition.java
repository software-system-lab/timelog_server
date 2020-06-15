package ssl.ois.timelog.cucumber.log;

import static org.junit.Assert.assertNull;

import java.util.UUID;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.log.add.AddLogUseCase;
import ssl.ois.timelog.service.log.add.AddLogUseCaseInput;
import ssl.ois.timelog.service.log.add.AddLogUseCaseOutput;
import ssl.ois.timelog.service.log.remove.RemoveLogUseCase;
import ssl.ois.timelog.service.log.remove.RemoveLogUseCaseInput;
import ssl.ois.timelog.service.log.remove.RemoveLogUseCaseOutput;

public class RemoveLogStepDefinition {
    
    private UserStepDefinition userStepDefinition;
    private LogRepository logRepository;
    private String logID;

    public RemoveLogStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Before
    public void setup() {
        this.logRepository = new MemoryLogRepository();
    }

    @Given("I have added a log with title {string} and start time {string} and end time {string} and description {string} and activity type {string} before")
    public void i_have_added_a_log_with_title_and_start_time_and_end_time_and_description_and_activity_type_before(String title, String startTime, String endTime, String description, String activityTypeName) {
        AddLogUseCase addLogUseCase = new AddLogUseCase(this.logRepository);
        AddLogUseCaseInput addLogUseCaseInput = new AddLogUseCaseInput();
        AddLogUseCaseOutput addLogUseCaseOutput = new AddLogUseCaseOutput();
        addLogUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        addLogUseCaseInput.setTitle(title);
        addLogUseCaseInput.setStartTime(startTime);
        addLogUseCaseInput.setEndTime(endTime);
        addLogUseCaseInput.setDescription(description);
        addLogUseCaseInput.setActivityTypeName(activityTypeName);

        addLogUseCase.execute(addLogUseCaseInput, addLogUseCaseOutput);

        this.logID = addLogUseCaseOutput.getLogID();
    }

    @When("I remove the log from my log history")
    public void i_remove_the_log_from_my_log_history() {
        RemoveLogUseCase removeLogUseCase = new RemoveLogUseCase(this.logRepository);
        RemoveLogUseCaseInput removeLogUseCaseInput = new RemoveLogUseCaseInput();
        RemoveLogUseCaseOutput removeLogUseCaseOutput = new RemoveLogUseCaseOutput();

        removeLogUseCaseInput.setLogID(this.logID);

        removeLogUseCase.execute(removeLogUseCaseInput, removeLogUseCaseOutput);
    }

    @Then("The log should be removed from my log history")
    public void the_log_should_be_removed_from_my_log_history() {
        assertNull(this.logRepository.findByID(UUID.fromString(this.logID)));
    }
}