package ada.mod6.bookclub.user;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;

public class UserStepDefinitions {

    private RequestSpecification request = RestAssured.given()
            .baseUri("http://localhost:8080")
            .contentType(ContentType.JSON);
    private Response response = null;
    private User user = new User();

    @Given("user is unknown")
    public void userIsUnknown() {
        user = new User();
        user.setName(RandomStringUtils.randomAlphabetic(10));
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "endtoend@test.com");
        user.setPassword(RandomStringUtils.randomAlphanumeric(6));
    }

    @Given("user with small password")
    public void userWitSmallPassword() {
        user = new User();
        user.setName(RandomStringUtils.randomAlphabetic(10));
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "endtoend@test.com");
        user.setPassword("123");
    }

    @When("user is registered with success")
    public void userIsRegistered() {
        response = request.body(user).when().post("/user/sign-up");
        response.then().statusCode(201);
    }

    @When("user fail to register")
    public void userFailToRegister() {
        response = request.body(user).when().post("/user/sign-up");
        response.then().statusCode(400);
    }

    @Then("user is known")
    public void userIsKnown() {
        response = request.when().get("/user/email/" + user.getEmail());
        response.then().statusCode(200);
        String email = response.jsonPath().get("$.email");
        Assertions.assertEquals(user.getEmail(), email);
    }

    @Then("user is still unknown")
    public void userIsStillUnknown() {
        response = request.when().get("/user/email/" + user.getEmail());
        response.then().statusCode(403);
    }

}
