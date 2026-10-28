package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationTriggersPullRequestReadyForReviewTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReadyForReview())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }

    @Test
    void constructor_shouldLeavePathsNull_whenNull() {
        final GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReadyForReview())
                        .paths(null);

        final GitHubEditRepoAutomationTriggersPullRequestReadyForReview sut = builder.build();

        assertThat(sut.paths()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenPathsTooSmall() {
        final GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReadyForReview())
                        .paths(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }

    @Test
    void constructor_shouldThrow_whenPathsItemTooShort() {
        final GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder builder =
                GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder(
                                AgentAutomationsCatalogFixtures.editRepoAutomationTriggersPullRequestReadyForReview())
                        .paths(List.of(""));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("paths");
    }
}
