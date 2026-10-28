package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewEventTest {

    @Test
    void constructor_shouldThrow_whenActionNull() {
        final GitHubPullRequestReviewEventBuilder builder = GitHubPullRequestReviewEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestReviewEvent())
                .action(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("action");
    }

    @Test
    void constructor_shouldThrow_whenReviewNull() {
        final GitHubPullRequestReviewEventBuilder builder = GitHubPullRequestReviewEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestReviewEvent())
                .review(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("review");
    }

    @Test
    void constructor_shouldThrow_whenPullRequestNull() {
        final GitHubPullRequestReviewEventBuilder builder = GitHubPullRequestReviewEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestReviewEvent())
                .pullRequest(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pullRequest");
    }
}
