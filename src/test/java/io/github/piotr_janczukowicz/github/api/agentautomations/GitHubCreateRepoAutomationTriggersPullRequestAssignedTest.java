package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersPullRequestAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestAssigned())
                        .paths(null);

        final GitHubCreateRepoAutomationTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestAssigned())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestAssigned())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestAssigned())
                        .assignees(null);

        final GitHubCreateRepoAutomationTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
