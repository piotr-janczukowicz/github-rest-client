package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersPullRequestAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .assignees(List.of("assignees", "assignees"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubEditRepoAutomationTriggersPullRequestAssigned value =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenPathsGiven() {
        final List<String> source = new ArrayList<>(List.of("paths"));
        final GitHubEditRepoAutomationTriggersPullRequestAssigned value =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .paths(source)
                        .build();

        source.add("paths");

        assertThat(value.paths()).hasSize(source.size() - 1);
    }

    @Test
    void constructor_shouldAcceptNull_whenAssigneesNull() {
        final GitHubEditRepoAutomationTriggersPullRequestAssigned value =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .assignees(null)
                        .build();

        assertThat(value.assignees()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenAssigneesGiven() {
        final List<String> source = new ArrayList<>(List.of("assignees"));
        final GitHubEditRepoAutomationTriggersPullRequestAssigned value =
                GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestAssigned())
                        .assignees(source)
                        .build();

        source.add("assignees");

        assertThat(value.assignees()).hasSize(source.size() - 1);
    }
}
