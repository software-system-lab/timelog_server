package ssl.ois.timelog.cucumber.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.log.add.*;
import io.cucumber.java.en.Then;

public class AddLogStepDefinition {
    private UserStepDefinition userStepDefinition;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private LogRepository logRepository;
    private AddLogUseCaseOutput addLogUseCaseOutput;
    private Log log;

    public AddLogStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Before
    public void setup() {
        this.logRepository = new MemoryLogRepository();
    }

    @Given("I {string} from {string} to {string}, the description is {string}")
    public void i_from_to_the_description_is(String title, String startTime, String endTime, String description) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    @When("I add a log with activity type {string} to Timelog")
    public void i_add_a_log_with_activity_type_to_Timelog(String activityTypeName) {
        AddLogUseCase usecase = new AddLogUseCase(this.logRepository);
        AddLogUseCaseInput addLogUseCaseInput = new AddLogUseCaseInput();
        this.addLogUseCaseOutput= new AddLogUseCaseOutput();
        
        addLogUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        addLogUseCaseInput.setTitle(this.title);
        addLogUseCaseInput.setStartTime(this.startTime);
        addLogUseCaseInput.setEndTime(this.endTime);
        addLogUseCaseInput.setDescription(this.description);
        addLogUseCaseInput.setActivityTypeName(activityTypeName);

        usecase.execute(addLogUseCaseInput, this.addLogUseCaseOutput);
    }

    @Then("The log should be stored in the Timelog")
    public void the_log_should_be_stored_in_the_Timelog() {
        String logID = this.addLogUseCaseOutput.getLogID();

        this.log = this.logRepository.findByID(UUID.fromString(logID));

        assertNotNull(this.log);
    }

    @Then("The log has title {string}")
    public void the_log_has_title(String title) {
        assertEquals(title, this.log.getTitle());
    }

    @Then("The log has start time with {string}")
    public void the_log_has_start_time_with(String startTime) {
        assertEquals(startTime, this.log.getDateFormat().format(this.log.getStartTime()));
    }

    @Then("The log has end time with {string}")
    public void the_log_has_end_time_with(String endTime) {
        assertEquals(endTime, this.log.getDateFormat().format(this.log.getEndTime()));
    }

    @Then("The log has description {string}")
    public void the_log_has_description(String description) {
        assertEquals(description, this.log.getDescription());
    }

    @Then("The log has activity type {string}")
    public void the_log_has_activity_type(String activityTypeName) {
        assertEquals(activityTypeName, this.log.getActivityTypeName());
    }
}