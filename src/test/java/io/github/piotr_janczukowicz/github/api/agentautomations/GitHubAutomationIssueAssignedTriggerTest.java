package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAutomationIssueAssignedTriggerTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubAutomationIssueAssignedTriggerBuilder builder = GitHubAutomationIssueAssignedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIssueAssignedTrigger())
                .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubAutomationIssueAssignedTriggerBuilder builder = GitHubAutomationIssueAssignedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIssueAssignedTrigger())
                .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubAutomationIssueAssignedTriggerBuilder builder = GitHubAutomationIssueAssignedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIssueAssignedTrigger())
                .assignees(List.of("assignees", "assignees"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldAcceptNull_whenAssigneesNull() {
        final GitHubAutomationIssueAssignedTrigger value = GitHubAutomationIssueAssignedTriggerBuilder.builder(
                        AgentAutomationsCatalogFixtures.automationIssueAssignedTrigger())
                .assignees(null)
                .build();

        assertThat(value.assignees()).isNull();
    }
}
