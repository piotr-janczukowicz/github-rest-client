package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersIssueAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersIssueAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubGetAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersIssueAssigned())
                        .assignees(null);

        final GitHubGetAutomationResponseTriggersIssueAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubGetAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersIssueAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubGetAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersIssueAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
