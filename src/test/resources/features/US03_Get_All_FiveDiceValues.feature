@US03
Feature: As a player, I want to get the values of all five dice


  Scenario: Player verifies current state of all five dice and see the values  via GET request - EXPECTED-->200
    Given Accept header is "application/json"
    When I send GET request to "/dice" endpoint
    Then status code should be 200
    And "status" field should be "success"
    And data field should have valid and unique id and valid values
    And response should match the "DieArrayResponseSchema"