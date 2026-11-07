package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldAccept_whenStatesRepeat() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED,
                                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED))
                        .build();

        assertThat(value.states()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted value =
                GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
