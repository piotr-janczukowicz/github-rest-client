package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewCommentCopilotCodeReviewSourceTest {

    @Test
    void constructor_shouldThrow_whenKindNull() {
        final GitHubPullRequestReviewCommentCopilotCodeReviewSourceBuilder builder =
                GitHubPullRequestReviewCommentCopilotCodeReviewSourceBuilder.builder(
                                PullrequestsCatalogFixtures.pullRequestReviewCommentCopilotCodeReviewSource())
                        .kind(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("kind");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubPullRequestReviewCommentCopilotCodeReviewSourceBuilder builder =
                GitHubPullRequestReviewCommentCopilotCodeReviewSourceBuilder.builder(
                                PullrequestsCatalogFixtures.pullRequestReviewCommentCopilotCodeReviewSource())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
