package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestAssigned())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestAssigned())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestAssigned())
                        .assignees(List.of("assignees", "assignees"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssigned value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestAssigned())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }

    @Test
    void constructor_shouldAcceptNull_whenAssigneesNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssigned value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestAssigned())
                        .assignees(null)
                        .build();

        assertThat(value.assignees()).isNull();
    }
}
