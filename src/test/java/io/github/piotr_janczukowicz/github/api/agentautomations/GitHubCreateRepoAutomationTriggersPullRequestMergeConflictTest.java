package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersPullRequestMergeConflictTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestMergeConflict())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestMergeConflict())
                        .paths(null);

        final GitHubCreateRepoAutomationTriggersPullRequestMergeConflict sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestMergeConflict())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestMergeConflict())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
