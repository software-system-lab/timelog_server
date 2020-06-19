package ssl.ois.timelog.cucumber.activity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.UUID;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCase;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCaseOutput;
import ssl.ois.timelog.service.activity.type.add.DuplicateActivityTypeException;
import ssl.ois.timelog.service.activity.type.edit.EditActivityTypeUseCase;
import ssl.ois.timelog.service.activity.type.edit.EditActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity.type.edit.EditActivityTypeUseCaseOutput;
import ssl.ois.timelog.service.activity.type.remove.RemoveActivityTypeUseCase;
import ssl.ois.timelog.service.activity.type.remove.RemoveActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity.type.remove.RemoveActivityTypeUseCaseOutput;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeRepository;
import ssl.ois.timelog.service.repository.user.UserRepository;
import io.cucumber.java.en.Then;

public class ActivityStepDefinition {

    private UserRepository userRepository;
    private ActivityTypeRepository activityTypeRepository;
    private String userID;
    private String activityTypeName;
    private boolean errorOccurred;

    @Before
    public void setup() {
        this.userRepository = new MemoryUserRepository();
        this.activityTypeRepository = new MemoryActivityTypeRepository();

        this.errorOccurred = false;
    }

    @Given("[Activity] I log in to Timelog with user ID {string}")
    public void activity_i_log_in_to_Timelog_with_user_ID(String userID) {
        if (this.userRepository.findByUserID(userID) == null) {
            this.userRepository.save(new User(UUID.fromString(userID)));

            ActivityType activityType = new ActivityType("Other");
            try {
                this.activityTypeRepository.addActivityType(userID, activityType);
            } catch (DuplicateActivityTypeException e) {
                fail(e.getMessage());
            }
        }

        this.userID = userID;
    }

    @Given("I have {string} course in this semester")
    public void i_have_course_in_this_semester(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    @When("I add it to my activity type list")
    public void i_add_it_to_my_activity_type_list() {
        AddActivityTypeUseCase addActivityTypeUseCase = new AddActivityTypeUseCase(this.activityTypeRepository);
        AddActivityTypeUseCaseInput addActivityTypeUseCaseInput = new AddActivityTypeUseCaseInput();
        AddActivityTypeUseCaseOutput addActivityTypeUseCaseOutput = new AddActivityTypeUseCaseOutput();

        addActivityTypeUseCaseInput.setUserID(this.userID);
        addActivityTypeUseCaseInput.setActivityTypeName(this.activityTypeName);

        try {
            addActivityTypeUseCase.execute(addActivityTypeUseCaseInput, addActivityTypeUseCaseOutput);
        } catch (DuplicateActivityTypeException e) {
            fail(e.getMessage());
        }
    }

    @Then("{string} is in my activity type list")
    public void is_in_my_activity_type_list(String activityTypeName) {
        boolean found = false;
        for (ActivityType activityType : this.activityTypeRepository.getActivityTypeList(userID)) {
            if (activityType.getName().equals(activityTypeName)) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Given("I have already had {string} in my activity type list")
    public void i_have_already_had_in_my_activity_type_lis(String activityTypeName) {
        AddActivityTypeUseCase addActivityTypeUseCase = new AddActivityTypeUseCase(this.activityTypeRepository);
        AddActivityTypeUseCaseInput addActivityTypeUseCaseInput = new AddActivityTypeUseCaseInput();
        AddActivityTypeUseCaseOutput addActivityTypeUseCaseOutput = new AddActivityTypeUseCaseOutput();

        addActivityTypeUseCaseInput.setUserID(this.userID);
        addActivityTypeUseCaseInput.setActivityTypeName(activityTypeName);

        try {
            addActivityTypeUseCase.execute(addActivityTypeUseCaseInput, addActivityTypeUseCaseOutput);
        } catch (DuplicateActivityTypeException e) {
            fail(e.getMessage());
        }

        this.activityTypeName = addActivityTypeUseCaseOutput.getActivityTypeName();
    }

    @When("I add an activity type with same name")
    public void i_add_an_activity_type_with_same_name() {
        AddActivityTypeUseCase addActivityTypeUseCase = new AddActivityTypeUseCase(this.activityTypeRepository);
        AddActivityTypeUseCaseInput addActivityTypeUseCaseInput = new AddActivityTypeUseCaseInput();
        AddActivityTypeUseCaseOutput addActivityTypeUseCaseOutput = new AddActivityTypeUseCaseOutput();

        addActivityTypeUseCaseInput.setUserID(this.userID);
        addActivityTypeUseCaseInput.setActivityTypeName(this.activityTypeName);

        try {
            addActivityTypeUseCase.execute(addActivityTypeUseCaseInput, addActivityTypeUseCaseOutput);
        } catch (DuplicateActivityTypeException e) {
            System.out.println("duplicate activity type");
            this.errorOccurred = true;
        }
    }

    @Then("Timelog should reject this command")
    public void timelog_should_reject_this_command() {
        assertTrue(this.errorOccurred);
    }

    @When("I remove it from my activity type list")
    public void i_remove_it_from_my_activity_type_list() {
        RemoveActivityTypeUseCase removeActivityTypeUseCase = new RemoveActivityTypeUseCase(
                this.activityTypeRepository);
        RemoveActivityTypeUseCaseInput removeActivityTypeUseCaseInput = new RemoveActivityTypeUseCaseInput();
        RemoveActivityTypeUseCaseOutput removeActivityTypeUseCaseOutput = new RemoveActivityTypeUseCaseOutput();

        removeActivityTypeUseCaseInput.setActivityTypeName(this.activityTypeName);
        removeActivityTypeUseCaseInput.setUserID(this.userID);

        try {
            removeActivityTypeUseCase.execute(removeActivityTypeUseCaseInput, removeActivityTypeUseCaseOutput);
        } catch (ActivityTypeNotExistException e) {
            fail(e.getMessage());
        }
    }

    @Then("{string} is not in my activity type list")
    public void is_not_in_my_activity_type_list(String activityTypeName) {

        for(ActivityType activityType: this.activityTypeRepository.getActivityTypeList(this.userID)) {
            if(activityType.getName().equals(activityTypeName)) {
                fail("Activity type is not removed from the repository");
            }
        }
    }

    @When("I change the activity name to {string} and set its state to disabled and private")
    public void i_change_the_activity_name_to_and_set_its_state_to_disabled_and_private(String newActivityTypeName) {
        EditActivityTypeUseCase editActivityTypeUseCase = new EditActivityTypeUseCase(this.activityTypeRepository);
        EditActivityTypeUseCaseInput editActivityTypeUseCaseInput = new EditActivityTypeUseCaseInput();
        EditActivityTypeUseCaseOutput editActivityTypeUseCaseOutput = new EditActivityTypeUseCaseOutput();

        editActivityTypeUseCaseInput.setUserID(this.userID);
        editActivityTypeUseCaseInput.setOldActivityTypeName(this.activityTypeName);
        editActivityTypeUseCaseInput.setNewActivtiyTypeName(newActivityTypeName);
        editActivityTypeUseCaseInput.setIsEnable(false);
        editActivityTypeUseCaseInput.setIsPrivate(true);

        try {
            editActivityTypeUseCase.execute(editActivityTypeUseCaseInput, editActivityTypeUseCaseOutput);
        } catch (ActivityTypeNotExistException e) {
            fail("Fail to edit the activity type");
        }
    }

    @Then("The activity type {string} should change its name to {string} and become disabled and private")
    public void the_activity_type_should_change_its_name_to(String oldActivityTypeName, String newActivityTypeName) {
        Boolean oldFound = false;
        Boolean newFound = false;

        for (ActivityType activityType: this.activityTypeRepository.getActivityTypeList(this.userID)) {
            if (activityType.getName().equals(oldActivityTypeName)) {
                oldFound = true;
            }
            if (activityType.getName().equals(newActivityTypeName) &&
                activityType.isEnable() == false &&
                activityType.isPrivate() == true) {
                    newFound = true;
            }
        }

        assertFalse(oldFound);
        assertTrue(newFound);
    }
}
