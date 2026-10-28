package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestReviewSubmitted())
                        .paths(null);

        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmitted sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesDuplicated() {
        final GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .createRepoAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }
}
