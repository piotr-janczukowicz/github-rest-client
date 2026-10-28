package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewCommentEventCommentLinksTest {

    @Test
    void constructor_shouldThrow_whenSelfNull() {
        final GitHubPullRequestReviewCommentEventCommentLinksBuilder builder =
                GitHubPullRequestReviewCommentEventCommentLinksBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventCommentLinks())
                        .self(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("self");
    }

    @Test
    void constructor_shouldThrow_whenHtmlNull() {
        final GitHubPullRequestReviewCommentEventCommentLinksBuilder builder =
                GitHubPullRequestReviewCommentEventCommentLinksBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventCommentLinks())
                        .html(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("html");
    }

    @Test
    void constructor_shouldThrow_whenPullRequestNull() {
        final GitHubPullRequestReviewCommentEventCommentLinksBuilder builder =
                GitHubPullRequestReviewCommentEventCommentLinksBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventCommentLinks())
                        .pullRequest(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pullRequest");
    }
}
