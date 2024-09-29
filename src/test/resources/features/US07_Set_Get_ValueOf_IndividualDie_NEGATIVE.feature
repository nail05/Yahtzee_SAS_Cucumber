@US07
Feature: As a player, I want to see notifications when I try to SET/GET the value of an individual die with invalid inputs


  Scenario Outline: Set value of an individual die  via PUT request - NEGATIVE - EXPECTED-->400
    Given Accept header is "application/json"
    And Request Content Type header is "application/json"
    And I send username and password for authentication
    When I send PUT request to "/die" endpoint with <die ID> and <value>
    Then status code should be 400
    And "status" field should be "failed"
    And response body should contain an appropriate error message
    And response should match the "StringResponseSchema"

    Examples:
      | die ID | value |
      | 0      | 7     |
      | 6      | 4     |
      | -1     | 4     |
      | 4      | 0     |
      | 5      | -6    |


  Scenario Outline: Set value of an individual die  via PUT request - NEGATIVE - UNAUTHORIZED - EXPECTED-->401
    Given Accept header is "application/json"
    And Request Content Type header is "application/json"
    When I send PUT request to "/die" endpoint with <die ID> and <value>
    Then status code should be 401
    And "status" field should be "failed"
    And response body should contain an appropriate error message
    And response should match the "StringResponseSchema"

    Examples:
      | die ID | value |
      | 1      | 3     |
      | 2      | 4     |
      | 3      | 5     |
      | 4      | 6     |
      | 5      | 1     |



  Scenario Outline: Get an individual die via GET request - NEGATIVE - EXPECTED-->400
    Given Accept header is "application/json"
    And path param id with value <id>
    When I send GET request to "/die/{id}" endpoint
    Then status code should be 400
    And content type is "application/json"
    And "status" field should be "failed"
    And response body should contain an appropriate error message


    Examples:
      | id       |
      | 0        |
      | 6        |
      | -1       |
      | 55555555 |