package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                        .paths(null);

        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmitted sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesDuplicated() {
        final GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }
}
