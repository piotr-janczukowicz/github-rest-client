package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestEventPullRequestTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final GitHubPullRequestEventPullRequestBuilder builder = GitHubPullRequestEventPullRequestBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEventPullRequest())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenHeadNull() {
        final GitHubPullRequestEventPullRequestBuilder builder = GitHubPullRequestEventPullRequestBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEventPullRequest())
                .head(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("head");
    }

    @Test
    void constructor_shouldThrow_whenBaseNull() {
        final GitHubPullRequestEventPullRequestBuilder builder = GitHubPullRequestEventPullRequestBuilder.builder(
                        ActivityCatalogFixtures.pullRequestEventPullRequest())
                .base(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("base");
    }
}
