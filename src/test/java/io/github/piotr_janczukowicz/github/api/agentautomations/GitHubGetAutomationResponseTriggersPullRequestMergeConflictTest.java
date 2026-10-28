package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersPullRequestMergeConflictTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationResponseTriggersPullRequestMergeConflictBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestMergeConflict())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubGetAutomationResponseTriggersPullRequestMergeConflictBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestMergeConflict())
                        .paths(null);

        final GitHubGetAutomationResponseTriggersPullRequestMergeConflict sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetAutomationResponseTriggersPullRequestMergeConflictBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestMergeConflict())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetAutomationResponseTriggersPullRequestMergeConflictBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestMergeConflictBuilder.builder(
                                AgentAutomationsCatalogFixtures.getAutomationResponseTriggersPullRequestMergeConflict())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
