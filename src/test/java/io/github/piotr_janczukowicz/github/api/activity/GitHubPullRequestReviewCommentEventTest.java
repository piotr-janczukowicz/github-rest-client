package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewCommentEventTest {

    @Test
    void constructor_shouldThrow_whenActionNull() {
        final GitHubPullRequestReviewCommentEventBuilder builder = GitHubPullRequestReviewCommentEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestReviewCommentEvent())
                .action(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("action");
    }

    @Test
    void constructor_shouldThrow_whenPullRequestNull() {
        final GitHubPullRequestReviewCommentEventBuilder builder = GitHubPullRequestReviewCommentEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestReviewCommentEvent())
                .pullRequest(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pullRequest");
    }

    @Test
    void constructor_shouldThrow_whenCommentNull() {
        final GitHubPullRequestReviewCommentEventBuilder builder = GitHubPullRequestReviewCommentEventBuilder.builder(
                        ActivityCatalogFixtures.pullRequestReviewCommentEvent())
                .comment(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("comment");
    }
}
