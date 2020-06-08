package ssl.ois.timelog.cucumber.activity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeRepository;
import ssl.ois.timelog.cucumber.common.UserIDStepDefinition;
import ssl.ois.timelog.model.user.ActivityType;
import ssl.ois.timelog.model.user.ActivityTypeList;
import ssl.ois.timelog.service.activity.type.remove.*;
import ssl.ois.timelog.service.repository.ActivityTypeRepository;

import static org.junit.Assert.*;

public class RemoveActivityTypeStepDefinition {
    private RemoveActivityTypeUseCaseOutput removeActivityTypeUseCaseOutput;
    private ActivityTypeRepository activityTypeRepository;
    private UserIDStepDefinition userIDStepDefinition;
    private String activityTypeName;

    public RemoveActivityTypeStepDefinition(UserIDStepDefinition userIDStepDefinition) {
        this.userIDStepDefinition = userIDStepDefinition;
    }

    @Given("I have already had {string} in my activity type list")
    public void i_have_already_had_in_my_activity_type_list(String activityTypeName) {
        this.activityTypeName = activityTypeName;

        ActivityTypeList activityTypeList = new ActivityTypeList();
        activityTypeList.setUserID(this.userIDStepDefinition.getUserID());
        activityTypeList.newType(this.activityTypeName);

        this.activityTypeRepository = new MemoryActivityTypeRepository();
        this.activityTypeRepository.save(activityTypeList);
    }

    @When("I want to remove it from my activity type list")
    public void i_want_to_remove_it_from_my_activity_type_list() {
        RemoveActivityTypeUseCase removeActivityTypeUseCase = new RemoveActivityTypeUseCase(this.activityTypeRepository);
        RemoveActivityTypeUseCaseInput removeActivityTypeUseCaseInput = new RemoveActivityTypeUseCaseInput();
        this.removeActivityTypeUseCaseOutput = new RemoveActivityTypeUseCaseOutput();
        
        removeActivityTypeUseCaseInput.setActivityTypeName(this.activityTypeName);
        removeActivityTypeUseCaseInput.setUserID(this.userIDStepDefinition.getUserID());

        removeActivityTypeUseCase.execute(removeActivityTypeUseCaseInput, this.removeActivityTypeUseCaseOutput);
    }

    @Then("I should get the removed activity type name")
    public void i_should_get_the_removed_activity_type_name() {
        assertEquals(this.activityTypeName, this.removeActivityTypeUseCaseOutput.getActivityTypeName());
    }

    @Then("{string} is not in my activity type list")
    public void is_not_in_my_activity_type_list(String activityTypeName) {
         for(ActivityType activityType : this.activityTypeRepository.findByUserID(this.userIDStepDefinition.getUserID()).getTypeList()) {
             if(activityType.getName().equals(activityTypeName)) {
                 fail("Activity Type is not removed from the repository");
             }
         }
    }
}