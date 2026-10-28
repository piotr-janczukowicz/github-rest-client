package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(null);

        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmitted sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesDuplicated() {
        final GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }
}
