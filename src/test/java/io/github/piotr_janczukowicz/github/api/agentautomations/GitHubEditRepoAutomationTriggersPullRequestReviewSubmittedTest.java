package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReviewSubmitted())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReviewSubmitted())
                        .paths(null);

        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmitted sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReviewSubmitted())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReviewSubmitted())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenStatesNull() {
        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReviewSubmitted())
                        .states(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooSmall() {
        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesTooLarge() {
        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }

    @Test
    void constructor_shouldThrow_whenStatesDuplicated() {
        final GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReviewSubmitted())
                        .states(List.of(
                                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED,
                                GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("states");
    }
}
