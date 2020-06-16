package ssl.ois.timelog.cucumber.log;

import static org.junit.Assert.assertNull;

import java.util.UUID;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.cucumber.common.PrepareLogStepDefinition;
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
    private PrepareLogStepDefinition prepareLogStepDefinition;
    private LogRepository logRepository;

    public RemoveLogStepDefinition(UserStepDefinition userStepDefinition, PrepareLogStepDefinition prepareLogStepDefinition) {
        this.userStepDefinition = userStepDefinition;
        this.prepareLogStepDefinition = prepareLogStepDefinition;
        this.prepareLogStepDefinition.setUserStepDefinition(userStepDefinition);
    }

    @Before
    public void setup() {
        this.logRepository = new MemoryLogRepository();
        this.prepareLogStepDefinition.setLogRepository(this.logRepository);
    }

    @When("I remove the log from my log history")
    public void i_remove_the_log_from_my_log_history() {
        RemoveLogUseCase removeLogUseCase = new RemoveLogUseCase(this.logRepository);
        RemoveLogUseCaseInput removeLogUseCaseInput = new RemoveLogUseCaseInput();
        RemoveLogUseCaseOutput removeLogUseCaseOutput = new RemoveLogUseCaseOutput();

        removeLogUseCaseInput.setLogID(this.prepareLogStepDefinition.getLogID());

        removeLogUseCase.execute(removeLogUseCaseInput, removeLogUseCaseOutput);
    }

    @Then("The log should be removed from my log history")
    public void the_log_should_be_removed_from_my_log_history() {
        assertNull(this.logRepository.findByID(UUID.fromString(this.prepareLogStepDefinition.getLogID())));
    }
}