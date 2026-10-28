package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationResponseTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(null);

        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmitted sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesDuplicated() {
        final GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }
}
