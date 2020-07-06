package ssl.ois.timelog.cucumber.timebox;

import io.cucumber.java.en.Given;
import ssl.ois.timelog.cucumber.common.UserLogin;

public class timelogStepDefinition {
    @Given("[timebox] I log in to Timelog with user ID {string}")
    public void timebox_I_log_in_to_Timelog_with_user_ID(String string) {
        UserLogin userLoginService = new UserLogin(this.userRepository);
        try {
            userLoginService.process(userID);
            this.userID = userLoginService.getUserID();
            this.user = userLoginService.getUser();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @When("I create a timebox with the title {string} and start date {string} due date {string}")
    public void i_create_a_timebox_with_the_title_and_start_date_due_date(String string, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("{string} has been created with the start date {string} due date {string}")
    public void has_been_created_with_the_start_date_due_date(String string, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}