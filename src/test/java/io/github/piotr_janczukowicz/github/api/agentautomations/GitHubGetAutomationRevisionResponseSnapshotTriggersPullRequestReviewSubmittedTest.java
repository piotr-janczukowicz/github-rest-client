package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldAccept_whenStatesRepeat() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED,
                                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedState
                                        .APPROVED))
                        .build();

        assertThat(value.states()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted value =
                GitHubGetAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationRevisionResponseSnapshotTriggersPullRequestReviewSubmitted())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
