package ssl.ois.timelog.cucumber.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.cucumber.common.UserIDStepDefinition;
import ssl.ois.timelog.service.repository.ActivityTypeRepository;
import ssl.ois.timelog.service.repository.UserRepository;
import ssl.ois.timelog.service.user.enter.EnterUseCase;
import ssl.ois.timelog.service.user.enter.EnterUseCaseInput;
import ssl.ois.timelog.service.user.enter.EnterUseCaseOutput;

public class UserEnterStepDefinition {
    private UserIDStepDefinition userIDStepDefinition;
    private UserRepository userRepository;
    private ActivityTypeRepository activityTypeListRepository;
    private EnterUseCaseInput enterUseCaseInput;

    public UserEnterStepDefinition(UserIDStepDefinition userIDStepDefinition) {
        this.userIDStepDefinition = userIDStepDefinition;
    }

    @Given("My name is {string}")
    public void my_name_is(String userName) {
        this.enterUseCaseInput = new EnterUseCaseInput();
        this.enterUseCaseInput.setUserName(userName);
        this.enterUseCaseInput.setUserID(this.userIDStepDefinition.getUserID());
    }

    @When("I first time enter Timelog")
    public void i_first_time_enter_Timelog() {
        this.userRepository = new MemoryUserRepository();

        this.activityTypeListRepository = new MemoryActivityTypeRepository();

        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.activityTypeListRepository);
        EnterUseCaseOutput enterUseCaseOutput = new EnterUseCaseOutput();

        enterUseCase.execute(enterUseCaseInput, enterUseCaseOutput);
    }

    @Then("I will get my activity type list")
    public void i_will_get_my_activity_type_list() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The activity type list only contains {string}")
    public void the_activity_type_list_only_contains(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
