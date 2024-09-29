package com.yahtzee.stepDefinitions;

import com.github.javafaker.Faker;
import com.yahtzee.utilities.ApiUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.sql.SQLOutput;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class API_StepDefs {

    Response res;
    ValidatableResponse valRes;
    RequestSpecification reqSpec;
    String playerName;
    Map<String, Object> setPlayerNameBodyFalse;
    Map<String, Integer> dieReqBody;
    List<Map<String, Integer>> allDieReqBodies = new ArrayList<>();
    List<Map<String, Integer>> allDieResponseBodies = new ArrayList<>();
    List<Map<Integer, Integer>> valuesOfFiveDice;
    int id;

    @Given("Accept header is {string}")
    public void accept_header_is(String accHeader) {
        reqSpec = given()
                .accept(accHeader);
    }

    @When("I send GET request to {string} endpoint")
    public void ı_send_get_request_to_endpoint(String endPoint) {
        Locale.setDefault(Locale.US);
        res = reqSpec.when()
                .get(endPoint)
                .prettyPeek();
        valRes = res.then();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(Integer expectedStatusCode) {

        valRes.statusCode(expectedStatusCode);
    }

    @Then("content type is {string}")
    public void content_type_is(String expectedResponseContentType) {
        valRes.contentType(expectedResponseContentType);
    }

    @Then("{string} field should be {string}")
    public void field_should_be(String field, String value) {
        valRes.body(field, is(value));
    }

    @And("response should match the {string}")
    public void responseShouldMatchThe(String schemaType) {// Validate schema
        switch (schemaType) {
            case "StringResponseSchema":
                valRes.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/StringResponseSchema.json"));
                System.out.println("StringResponseSchema Validation PASSED!");
                break;

            case "DieArrayResponseSchema":
                valRes.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/DieArrayResponseSchema.json"));
                System.out.println("DieArrayResponseSchema Validation PASSED!");
                break;
            case "BooleanResponseSchema":
                valRes.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/BooleanResponseSchema.json"));
                System.out.println("BooleanResponseSchema Validation PASSED!");
                break;
            default:
                // Fail if schema type is not one of the expected formats
                throw new IllegalArgumentException("Unexpected content type: " + schemaType);
        }
    }

    @And("response body should contain an appropriate error message")
    public void responseBodyShouldContainAnAppropriateErrorMessage() {
        valRes.body("data", is(notNullValue()));
        String errorMessage = res.path("data");
        System.out.println("Error Message = " + errorMessage);
    }


    @And("Request Content Type header is {string}")
    public void requestContentTypeHeaderIs(String reqContentType) {
        reqSpec.contentType(reqContentType);
    }

    @And("I send username and password for authentication")
    public void ıSendUsernameAndPasswordForAuthentication() {
        String userName = System.getenv("userName");
        String password = System.getenv("password");;
        reqSpec.auth().preemptive().basic(userName, password);
    }


    @When("I send PUT request to {string} endpoint with a new player name")
    public void ıSendPUTRequestToEndpointWithANewPlayerName(String endPoint) {
        Locale.setDefault(Locale.US);
        Map<String, String> playerNameBody = new HashMap<>();
        Faker faker = new Faker();
        playerName = faker.name().firstName();

     System.out.println("playerName = " + playerName);

        playerNameBody.put("name", playerName);
        reqSpec.body(playerNameBody);
        res = reqSpec.when()
                .put(endPoint)
                .prettyPeek();
        valRes = res.then();
    }


    @And("name field should match with the updated name")
    public void nameFieldShouldMatchWithTheUpdatedName() {
        JsonPath jp = res.jsonPath();
        String actualPlayerName = jp.getString("name");

        System.out.println("actualPlayerName = " + actualPlayerName);

        Assert.assertEquals("Player Name does not match,FAILED!!!", playerName, actualPlayerName);
        System.out.println("Name field matches with the updated name, SUCCESSFUL");
    }

    @When("I send PUT request to {string} endpoint with an invalid player name integer")
    public void ıSendPUTRequestToEndpointWithAnInvalidPlayerNameInteger(String endPoint) {
        Locale.setDefault(Locale.US);
        Faker faker = new Faker();
        int playerNameInteger = faker.number().randomDigit();  // Integer case
        setPlayerNameBodyFalse = new HashMap<>();
        System.out.println("playerName = " + playerNameInteger);

        setPlayerNameBodyFalse.put("name", playerNameInteger);

        reqSpec.body(setPlayerNameBodyFalse);
        res = reqSpec.when()
                .put(endPoint)
                .prettyPeek();
        valRes = res.then();
    }

    @When("I send PUT request to {string} endpoint with an invalid player name empty")
    public void ıSendPUTRequestToEndpointWithAnInvalidPlayerNameEmpty(String endPoint) {
        Locale.setDefault(Locale.US);

        reqSpec.body("name");
        res = reqSpec.when()
                .put(endPoint)
                .prettyPeek();
        valRes = res.then();
    }

    @When("I send POST request to {string} endpoint")
    public void ıSendPOSTRequestToEndpoint(String endPoint) {
        res = reqSpec.when()
                .post(endPoint)
                .prettyPeek();
        valRes = res.then();

    }


    @And("data field should have valid and unique id and valid values")
    public void dataFieldShouldHaveValidAndUniqueIdAndValidValues() {
        valRes.body("data.id", containsInAnyOrder(1, 2, 3, 4, 5))
                .body("data.value", everyItem(allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(6))));
        valuesOfFiveDice = res.path("data");
        System.out.println("Values of all five dice retrieved SUCCESSFULLY");
    }


    @And("path param id with value {int}")
    public void pathParamIdWithValueId(int id) {
        this.id = id;
        reqSpec.pathParam("id", id);


    }

    @And("id field should match with the path param")
    public void idFieldShouldMatchWithThePathParam() {
        JsonPath jp = res.jsonPath();
        int actualID = jp.getInt("data.id");
        Assert.assertEquals(id, actualID);
    }


    @And("die should have valid values")
    public void dieShouldHaveValidValues() {
        valRes.body("data.value", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(6)));
        System.out.println("Die Rolled Successfully");
    }

    @And("response should match the DieIntResponseSchema")
    public void responseShouldMatchTheDieIntResponseSchema() {
        valRes.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/DieIntResponseSchema.json"));
        System.out.println("DieIntResponseSchema Validation PASSED!");
    }


    @And("die should have valid values in any representations")
    public void dieShouldHaveValidValuesInAnyRepresentations() {
        // Get the content type from the response
        String contentType = res.getHeader("Content-Type");
        System.out.println("contentType = " + contentType);
        int dieId = res.body().path("data.id");  // "data.id" holds die ID
        int dieValueAsInt = 0;  // Variable to store the integer value of the die
        switch (contentType) {
            case "application/json":
            case "application/vnd.yahtzee.int+json":
                // Validate integer representation (1 to 6)
                valRes.body("data.value", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(6)));
                JsonPath jp = res.jsonPath();
                dieValueAsInt = jp.getInt("data.value");
                System.out.println("Die value retrieved successfully in integer representation.");
                break;

            case "application/vnd.yahtzee.float+json":
                // Validate float representation ("1.0" to "6.0")
                String dieValueAsFloat = res.body().path("data.value");
                dieValueAsInt = Integer.parseInt(dieValueAsFloat.substring(0, dieValueAsFloat.indexOf('.')));  // Convert to integer
                valRes.body("data.value", anyOf(
                        equalTo("1.0"), equalTo("2.0"), equalTo("3.0"),
                        equalTo("4.0"), equalTo("5.0"), equalTo("6.0")
                ));
                System.out.println("Die value retrieved successfully in float representation.");
                break;

            case "application/vnd.yahtzee.word+json":
                // Validate word representation ("one" to "six")
                String dieValueAsWord = res.body().path("data.value");
                dieValueAsInt = ApiUtilities.wordToInt(dieValueAsWord);
                valRes.body("data.value", anyOf(
                        equalTo("one"), equalTo("two"), equalTo("three"),
                        equalTo("four"), equalTo("five"), equalTo("six")
                ));
                System.out.println("Die value retrieved successfully in word representation.");
                break;

            case "application/vnd.yahtzee.dots+json":
                // Validate dots representation (".", "..", "...", "....", ".....", "......")
                String dieValueAsDots = res.body().path("data.value");
                dieValueAsInt = ApiUtilities.dotsToInt(dieValueAsDots);  // Convert dots to integer
                valRes.body("data.value", anyOf(
                        equalTo("."), equalTo(".."), equalTo("..."),
                        equalTo("...."), equalTo("....."), equalTo("......")
                ));
                System.out.println("Die value retrieved successfully in dots representation.");
                break;

            default:
                // Fail if content type is not one of the expected formats
                throw new IllegalArgumentException("Unexpected content type: " + contentType);
        }
        Map<String, Integer> dieResponseMap = new LinkedHashMap<>();
        dieResponseMap.put("id", dieId);
        dieResponseMap.put("value", dieValueAsInt);

        System.out.println("Get Individual die feature SUCCESSFUL");
    }


    @When("I send PUT request to {string} endpoint with {int} and {int}")
    public void ıSendPUTRequestToEndpointWithDieIDAndValue(String endpoint, int dieID, int value) {
        Locale.setDefault(Locale.US);
        dieReqBody = new LinkedHashMap<>();
        dieReqBody.put("id", dieID);
        dieReqBody.put("value", value);
        System.out.println("dieReqBody = " + dieReqBody);

        reqSpec.body(dieReqBody);
        res = reqSpec.when()
                .put(endpoint)
                .prettyPeek();
        valRes = res.then();

    }


    @And("data field should be boolean")
    public void dataFieldShouldBeBoolean() {
        valRes.body("data", anyOf(is(true), is(false)));
        boolean isYahtzee = res.path("data");
        System.out.println("isYahtzee = " + isYahtzee);
    }



}
