package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestReadyForReview())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestReadyForReview())
                        .paths(null);

        final GitHubCreateRepoAutomationTriggersPullRequestReadyForReview sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestReadyForReview())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder builder =
                GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTriggersPullRequestReadyForReview())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
