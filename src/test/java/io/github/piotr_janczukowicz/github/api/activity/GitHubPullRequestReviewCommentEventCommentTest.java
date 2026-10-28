package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewCommentEventCommentTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenDiffHunkNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .diffHunk(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("diffHunk");
    }

    @Test
    void constructor_shouldThrow_whenPathNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .path(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void constructor_shouldThrow_whenCommitIdNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .commitId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commitId");
    }

    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenPullRequestUrlNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .pullRequestUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pullRequestUrl");
    }

    @Test
    void constructor_shouldThrow_whenLinksNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .links(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("links");
    }

    @Test
    void constructor_shouldThrow_whenOriginalCommitIdNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .originalCommitId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("originalCommitId");
    }

    @Test
    void constructor_shouldThrow_whenReactionsNull() {
        final GitHubPullRequestReviewCommentEventCommentBuilder builder =
                GitHubPullRequestReviewCommentEventCommentBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventComment())
                        .reactions(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("reactions");
    }
}
