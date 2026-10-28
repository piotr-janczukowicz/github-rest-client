package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersPullRequestAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestAssigned())
                        .paths(null);

        final GitHubGetAutomationResponseTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestAssigned())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestAssigned())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestAssigned())
                        .assignees(null);

        final GitHubGetAutomationResponseTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
