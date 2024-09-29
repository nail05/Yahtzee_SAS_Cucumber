@US04
Feature: As a player, I want to roll all five dice


  Scenario: Player Rolls all five dice and see their new values  via POST request- POSITIVE - EXPECTED-->200
    Given Accept header is "application/json"
    When I send POST request to "/rollDice" endpoint
    Then status code should be 200
    And "status" field should be "success"
    And data field should have valid and unique id and valid values
    And response should match the "DieArrayResponseSchema"

