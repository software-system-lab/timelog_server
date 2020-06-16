package ssl.ois.timelog.cucumber.activity;

import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCase;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCaseOutput;
import ssl.ois.timelog.service.repository.ActivityTypeListRepository;

import static org.junit.jupiter.api.Assertions.assertNull;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class AddDuplicateActivityTypeStepDefinition {
    
    private UserStepDefinition userStepDefinition;
    private ActivityTypeListRepository activityTypeListRepository;
    private String activityTypeName;
    private AddActivityTypeUseCaseOutput addActivityTypeUseCaseOutput;

    public AddDuplicateActivityTypeStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Before
    public void setup() {
        this.activityTypeListRepository = new MemoryActivityTypeListRepository();
        
    }

    @Given("I have added {string} to my activity type list")
    public void i_have_added_to_my_activity_type_list(String activityTypeName) {
        ActivityTypeList activityTypeList = new ActivityTypeList(this.userStepDefinition.getUserID());
        this.activityTypeListRepository.save(activityTypeList);

        AddActivityTypeUseCase addActivityTypeUseCase = new AddActivityTypeUseCase(this.activityTypeListRepository);
        AddActivityTypeUseCaseInput addActivityTypeUseCaseInput = new AddActivityTypeUseCaseInput();

        addActivityTypeUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        addActivityTypeUseCaseInput.setActivityTypeName(activityTypeName);

        addActivityTypeUseCase.execute(addActivityTypeUseCaseInput, new AddActivityTypeUseCaseOutput());

        this.activityTypeName = activityTypeName;
    }

    @When("I add the same activity type to my activity type list again")
    public void i_add_the_same_activity_type_to_my_activity_type_list_again() {
        AddActivityTypeUseCase addActivityTypeUseCase = new AddActivityTypeUseCase(this.activityTypeListRepository);
        AddActivityTypeUseCaseInput addActivityTypeUseCaseInput = new AddActivityTypeUseCaseInput();
        this.addActivityTypeUseCaseOutput = new AddActivityTypeUseCaseOutput();

        addActivityTypeUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        addActivityTypeUseCaseInput.setActivityTypeName(this.activityTypeName);

        addActivityTypeUseCase.execute(addActivityTypeUseCaseInput, this.addActivityTypeUseCaseOutput);
    }

    @Then("Timelog should reject this command")
    public void timelog_should_reject_this_command() {
        assertNull(this.addActivityTypeUseCaseOutput.getActivityTypeName());
    }
}