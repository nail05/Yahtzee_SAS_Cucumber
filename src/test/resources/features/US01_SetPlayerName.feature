@US01
Feature: As a user, I want to set the player name


  Scenario: Set Player Name  via PUT request- POSITIVE - EXPECTED-->204
    Given Accept header is "application/json"
    And Request Content Type header is "application/json"
    And I send username and password for authentication
    When I send PUT request to "/playerName" endpoint with a new player name
    Then status code should be 204


  Scenario: Set Player Name  via PUT request- NEGATIVE - BAD REQUEST - EXPECTED-->400
    Given Accept header is "application/json"
    And Request Content Type header is "application/json"
    And I send username and password for authentication
    When I send PUT request to "/playerName" endpoint with an invalid player name integer
    Then status code should be 400
    And "status" field should be "failed"
    And response body should contain an appropriate error message

  Scenario: Set Player Name  via PUT request- NEGATIVE - BAD REQUEST - EXPECTED-->400
    Given Accept header is "application/json"
    And Request Content Type header is "application/json"
    And I send username and password for authentication
    When I send PUT request to "/playerName" endpoint with an invalid player name empty
    Then status code should be 400
    And "status" field should be "failed"
    And response body should contain an appropriate error message

  Scenario: Set Player Name  via PUT request- NEGATIVE - UNAUTHORIZED - EXPECTED-->401
    Given Accept header is "application/json"
    And Request Content Type header is "application/json"
    When I send PUT request to "/playerName" endpoint with a new player name
    Then status code should be 401
    And "status" field should be "failed"
    And response body should contain an appropriate error message

