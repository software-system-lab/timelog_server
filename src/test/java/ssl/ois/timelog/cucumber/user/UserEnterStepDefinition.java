package ssl.ois.timelog.cucumber.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.cucumber.common.UserIDStepDefinition;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
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
    private EnterUseCaseOutput enterUseCaseOutput;
    private List<ActivityType> activityTypeList;

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
        this.enterUseCaseOutput = new EnterUseCaseOutput();

        enterUseCase.execute(enterUseCaseInput, enterUseCaseOutput);
    }

    @Then("I will get my activity type list")
    public void i_will_get_my_activity_type_list() {
        this.activityTypeList = this.enterUseCaseOutput.getActivityTypeList().getTypeList();
    }

    @Then("The activity type list only contains {string}")
    public void the_activity_type_list_only_contains(String activityTypeName) {
        assertEquals(1, this.activityTypeList.size());
        assertEquals(activityTypeName, this.activityTypeList.get(0).getName());
    }

    @Then("I should have an activity type list in the database.")
    public void i_should_have_an_activity_type_list_in_the_database() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
