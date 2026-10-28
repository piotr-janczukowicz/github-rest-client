package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestAssigned())
                        .paths(null);

        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestAssigned())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestAssigned())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestAssigned())
                        .assignees(null);

        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
