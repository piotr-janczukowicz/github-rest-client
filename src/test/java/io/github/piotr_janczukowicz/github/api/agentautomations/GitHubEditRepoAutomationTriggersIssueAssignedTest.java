package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
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
                        .assignees(List.of("assignees", "assignees"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldAcceptNull_whenAssigneesNull() {
        final GitHubEditRepoAutomationTriggersIssueAssigned value =
                GitHubEditRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersIssueAssigned())
                        .assignees(null)
                        .build();

        assertThat(value.assignees()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenAssigneesGiven() {
        final List<String> source = new ArrayList<>(List.of("assignees"));
        final GitHubEditRepoAutomationTriggersIssueAssigned value =
                GitHubEditRepoAutomationTriggersIssueAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersIssueAssigned())
                        .assignees(source)
                        .build();

        source.add("assignees");

        assertThat(value.assignees()).hasSize(source.size() - 1);
    }
}
