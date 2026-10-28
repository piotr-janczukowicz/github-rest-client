package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewEventReviewLinksHtmlTest {

    @Test
    void constructor_shouldThrow_whenHrefNull() {
        final GitHubPullRequestReviewEventReviewLinksHtmlBuilder builder =
                GitHubPullRequestReviewEventReviewLinksHtmlBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewEventReviewLinksHtml())
                        .href(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("href");
    }
}
