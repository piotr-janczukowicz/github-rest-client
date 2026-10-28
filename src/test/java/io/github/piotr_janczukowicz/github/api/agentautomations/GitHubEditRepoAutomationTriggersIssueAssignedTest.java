package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersIssueAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationTriggersIssueAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersIssueAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubEditRepoAutomationTriggersIssueAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersIssueAssigned())
                        .assignees(null);

        final GitHubEditRepoAutomationTriggersIssueAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubEditRepoAutomationTriggersIssueAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersIssueAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubEditRepoAutomationTriggersIssueAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersIssueAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
