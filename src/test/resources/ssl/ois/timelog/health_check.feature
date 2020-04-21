Feature: HealthCheck

    Scenario: Perform health check.
        When I perform get request to "/" path
        Then I get status 200 and message "Timelog is health" as response.
