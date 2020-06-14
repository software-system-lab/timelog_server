package ssl.ois.timelog.cucumber.user.enter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.repository.ActivityTypeListRepository;
import ssl.ois.timelog.service.repository.UserRepository;
import ssl.ois.timelog.service.user.enter.EnterUseCase;
import ssl.ois.timelog.service.user.enter.EnterUseCaseInput;
import ssl.ois.timelog.service.user.enter.EnterUseCaseOutput;

public class UserFirstEnterStepDefinition {
    private UserStepDefinition userStepDefinition;
    private UserRepository userRepository;
    private ActivityTypeListRepository activityTypeListRepository;
    private LogRepository logRepository;
    private EnterUseCaseOutput enterUseCaseOutput;
    private List<ActivityType> activityTypeList;

    public UserFirstEnterStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Before
    public void setup() {
        this.userRepository = new MemoryUserRepository();
        this.activityTypeListRepository = new MemoryActivityTypeListRepository();
        this.logRepository = new MemoryLogRepository();
    }

    @When("I first time enter Timelog")
    public void i_first_time_enter_Timelog() {
        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.activityTypeListRepository, this.logRepository);
        EnterUseCaseInput enterUseCaseInput = new EnterUseCaseInput();
        enterUseCaseInput.setUserName(this.userStepDefinition.getUserName());
        enterUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        this.enterUseCaseOutput = new EnterUseCaseOutput();

        enterUseCase.execute(enterUseCaseInput, enterUseCaseOutput);
    }

    @Then("I will get my activity type list")
    public void i_will_get_my_activity_type_list() {
        this.activityTypeList = this.enterUseCaseOutput.getActivityTypeList();
    }

    @Then("The activity type list only contains {string}")
    public void the_activity_type_list_only_contains(String activityTypeName) {
        assertEquals(1, this.activityTypeList.size());
        assertEquals(activityTypeName, this.activityTypeList.get(0).getName());

        ActivityTypeList activityTypes = this.activityTypeListRepository.findByUserID(this.userStepDefinition.getUserID());

        assertEquals(1, activityTypes.getTypeList().size());
        assertEquals(activityTypeName, activityTypes.getTypeList().get(0).getName());
    }
}
