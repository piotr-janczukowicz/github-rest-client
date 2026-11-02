package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldAccept_whenStatesRepeat() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED));

        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmitted value = builder.build();

        assertThat(value.states()).hasSize(2);
    }

    @Test
    void constructor_shouldAcceptNull_whenPathsNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmitted value =
                GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(null)
                        .build();

        assertThat(value.paths()).isNull();
    }
}
