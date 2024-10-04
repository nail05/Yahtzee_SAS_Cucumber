@US01
Feature: As a user, I want to get the player name

@wip
  Scenario: Get Player Name  via GET request-POSITIVE - EXPECTED-->200
    Given Accept header is "application/json"
    When I send GET request to "/playerName" endpoint
    Then status code should be 200
    And content type is "application/json"
    And "status" field should be "success"
    And name field should match with the updated name
    And response should match the "StringResponseSchema"

