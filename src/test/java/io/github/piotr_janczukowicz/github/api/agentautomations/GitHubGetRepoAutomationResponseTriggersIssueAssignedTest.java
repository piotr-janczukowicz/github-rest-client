package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersIssueAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersIssueAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersIssueAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubGetRepoAutomationResponseTriggersIssueAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersIssueAssigned())
                        .assignees(List.of("assignees", "assignees"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldAcceptNull_whenAssigneesNull() {
        final GitHubGetRepoAutomationResponseTriggersIssueAssigned value =
                GitHubGetRepoAutomationResponseTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersIssueAssigned())
                        .assignees(null)
                        .build();

        assertThat(value.assignees()).isNull();
    }
}
