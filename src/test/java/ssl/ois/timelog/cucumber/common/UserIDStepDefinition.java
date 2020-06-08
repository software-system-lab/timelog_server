package ssl.ois.timelog.cucumber.common;

import io.cucumber.java.en.Given;

public class UserIDStepDefinition {
    private String userID;
    
    @Given("My user ID is {string}")
    public void my_user_ID_is(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
