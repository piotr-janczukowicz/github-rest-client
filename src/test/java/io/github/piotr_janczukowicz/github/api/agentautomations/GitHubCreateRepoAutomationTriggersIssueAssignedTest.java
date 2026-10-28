package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersIssueAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersIssueAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersIssueAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersIssueAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersIssueAssigned())
                        .assignees(null);

        final GitHubCreateRepoAutomationTriggersIssueAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubCreateRepoAutomationTriggersIssueAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersIssueAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubCreateRepoAutomationTriggersIssueAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersIssueAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
