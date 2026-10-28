package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestMergeConflict())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestMergeConflict())
                        .paths(null);

        final GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflict sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestMergeConflict())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder builder =
                GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationResponseTriggersPullRequestMergeConflict())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
