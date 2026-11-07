package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolvedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolvedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolvedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolved())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolvedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolvedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolved())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolvedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolvedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolved())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolved value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolvedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolved())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
