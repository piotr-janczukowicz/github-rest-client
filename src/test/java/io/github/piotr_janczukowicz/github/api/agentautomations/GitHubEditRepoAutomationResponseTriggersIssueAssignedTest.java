package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersIssueAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersIssueAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersIssueAssigned())
                        .assignees(null);

        final GitHubEditRepoAutomationResponseTriggersIssueAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersIssueAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationResponseTriggersIssueAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
