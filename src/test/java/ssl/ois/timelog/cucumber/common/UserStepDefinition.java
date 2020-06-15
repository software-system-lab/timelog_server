package ssl.ois.timelog.cucumber.common;

import io.cucumber.java.en.Given;

public class UserStepDefinition {
    private String userID;
    
    @Given("I log in to Timelog with user ID {string}")
    public void i_log_in_to_Timelog_with_user_ID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

}
