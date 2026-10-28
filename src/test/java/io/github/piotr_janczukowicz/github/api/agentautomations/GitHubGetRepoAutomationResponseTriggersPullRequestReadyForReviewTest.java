package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReadyForReview())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReadyForReview())
                        .paths(null);

        final GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReview sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReadyForReview())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder builder =
                GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .getRepoAutomationResponseTriggersPullRequestReadyForReview())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
