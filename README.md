# Yahtzee_SAS_Cucumber
Verify Yahtzee_Api endpoints
# Test Plan 
## Introduction
This document outlines the test plan for verifying the functionality of several API endpoints specified in the OpenAPI document. The test cases cover various operations on player names, dice, and Yahtzee-related endpoints. The purpose is to ensure the API behaves as expected under various scenarios, and all test cases will be executed in an end-to-end (E2E) testing flow.

## Authentication
Authentication for accessing the API endpoints is done using a userName and password, which are securely stored as environment variables. No sensitive information is shared in the test cases.

## Features to Be Tested
The following API endpoints are included in the test plan:

GET /playerName: Retrieve the player's name.
PUT /playerName: Update the player's name.
GET /die/{id}: Retrieve the value of an individual die by its ID.
PUT /die: Update the value of a die.
POST /die/{id}: Roll an individual die.
GET /dice: Retrieve the values of all dice.
POST /rollDice: Roll all dice.
GET /isYahtzee: Check if the dice result forms a Yahtzee.
## Features Not to Be Tested
Currently, no specific features are excluded from testing in this phase. (Includes only functional testing)

## Testing Levels and Types
### Test Levels
System Testing: Full system testing will be conducted to verify the integration of various API components and their interactions.
### Test Types
Functional Testing:
- Manual and Automated
- End-to-End (E2E) Testing: A comprehensive flow of all API endpoints in sequence, simulating real-world usage.
## Test Execution Flow
The tests are designed to execute in an E2E flow, with some of the operations depending on the outcomes of previous tests. This ensures that the entire user journey through the API is thoroughly validated.

## Exit Criteria
The testing process will be considered complete under the following conditions:

- All functional testing is complete with no unresolved functional bugs.
- All remaining bugs are of low severity.
- No more than 10% of medium-severity bugs are open.
- All critical paths in the E2E flow have been verified and passed.
## Test Deliverables
At the end of the testing process, the following deliverables will be provided:

- Test Cases/Scripts
- Test Summary Reports
## Conclusion
This test plan ensures a comprehensive coverage of the core API endpoints in the OpenAPI document. The tests aim to validate that the API behaves as expected and handles both positive and negative scenarios. The automated and manual tests will focus on system-level and E2E testing to ensure all the endpoints work correctly in an integrated environment.