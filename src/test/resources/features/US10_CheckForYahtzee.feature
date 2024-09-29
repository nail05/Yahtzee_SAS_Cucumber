@US10
Feature: As a player, I want to check for Yahtzee


  Scenario: Player check the values of all five dice for Yahtzee via GET request- POSITIVE - EXPECTED-->200
    Given Accept header is "application/json"
    When I send GET request to "/isYahtzee" endpoint
    Then status code should be 200
    And "status" field should be "success"
    And data field should be boolean
    And response should match the "BooleanResponseSchema"