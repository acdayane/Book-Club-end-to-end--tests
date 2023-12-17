Feature: User register

  Scenario: User is not registered
    Given user is unknown
    When user is registered with success
    Then user is known

  Scenario: User with small password
    Given user with small password
    When user fail to register
    Then user is still unknown