package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersPullRequestAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestAssigned())
                        .paths(null);

        final GitHubGetRepoAutomationResponseTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestAssigned())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestAssigned())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestAssigned())
                        .assignees(null);

        final GitHubGetRepoAutomationResponseTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.getRepoAutomationResponseTriggersPullRequestAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
