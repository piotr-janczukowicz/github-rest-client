package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewCommentEventCommentLinksSelfTest {

    @Test
    void constructor_shouldThrow_whenHrefNull() {
        final GitHubPullRequestReviewCommentEventCommentLinksSelfBuilder builder =
                GitHubPullRequestReviewCommentEventCommentLinksSelfBuilder.builder(
                                ActivityCatalogFixtures.pullRequestReviewCommentEventCommentLinksSelf())
                        .href(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("href");
    }
}
