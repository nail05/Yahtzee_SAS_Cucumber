@US06
Feature: As a player, I want to set value of an individual die


  Scenario Outline: Set value of an individual die  via PUT request - POSITIVE - EXPECTED-->204
    Given Accept header is "application/json"
    And Request Content Type header is "application/json"
    And I send username and password for authentication
    When I send PUT request to "/die" endpoint with <die ID> and <value>
    Then status code should be 204


    Examples:
      | die ID | value |
      | 1      | 3     |
      | 2      | 6     |
      | 3      | 4     |
      | 4      | 4     |
      | 5      | 1     |


  Scenario Outline: Get an individual die  via GET request - POSITIVE - EXPECTED-->200
    Given Accept header is "application/json"
    And path param id with value <id>
    When I send GET request to "/die/{id}" endpoint
    Then status code should be 200
    And content type is "application/json"
    And "status" field should be "success"
    And id field should match with the path param
    And die should have valid values in any representations


    Examples:
      | id |
      | 1  |
      | 2  |
      | 3  |
      | 4  |
      | 5  |

