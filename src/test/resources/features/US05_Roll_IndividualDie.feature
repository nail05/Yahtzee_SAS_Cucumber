
@US05
Feature: As a player, I want to roll and get  an individual die


  Scenario Outline: Roll an individual die  via POST request - POSITIVE - EXPECTED-->200
    Given Accept header is "application/json"
    And path param id with value <id>
    When I send POST request to "/rollDie/{id}" endpoint
    Then status code should be 200
    And content type is "application/json"
    And "status" field should be "success"
    And id field should match with the path param
    And die should have valid values


    Examples:
      | id |
      | 1  |
      | 2  |
      | 3  |
      | 4  |
      | 5  |



  Scenario Outline: Roll an individual die with invalid parameters via POST request - NEGATIVE - EXPECTED-->400
    Given Accept header is "application/json"
    And path param id with value <id>
    When I send POST request to "/rollDie/{id}" endpoint
    Then status code should be 400
    And content type is "application/json"
    And "status" field should be "failed"
    And response body should contain an appropriate error message

    Examples:
      | id          |
      | 0           |
      | 6           |
      | -1          |
      | 555555 |