package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewEventReviewLinksTest {

    @Test
    void constructor_shouldThrow_whenHtmlNull() {
        final GitHubPullRequestReviewEventReviewLinksBuilder builder =
                GitHubPullRequestReviewEventReviewLinksBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewEventReviewLinks())
                        .html(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("html");
    }

    @Test
    void constructor_shouldThrow_whenPullRequestNull() {
        final GitHubPullRequestReviewEventReviewLinksBuilder builder =
                GitHubPullRequestReviewEventReviewLinksBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewEventReviewLinks())
                        .pullRequest(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("pullRequest");
    }
}
