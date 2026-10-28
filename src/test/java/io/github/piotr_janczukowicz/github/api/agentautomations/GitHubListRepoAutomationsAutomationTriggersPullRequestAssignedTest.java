package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestAssigned())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestAssigned())
                        .paths(null);

        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestAssigned())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestAssigned())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldLeaveAssigneesNull_whenNull() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestAssigned())
                        .assignees(null);

        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssigned sut = builder.build();

        assertThat(sut.assignees()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenAssigneesTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestAssigned())
                        .assignees(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }

    @Test
    void constructor_shouldThrow_whenAssigneesDuplicated() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestAssigned())
                        .assignees(List.of("a", "a"));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("assignees");
    }
}
