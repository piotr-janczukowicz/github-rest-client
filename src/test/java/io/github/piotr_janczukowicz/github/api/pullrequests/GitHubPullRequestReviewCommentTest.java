package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPullRequestReviewCommentTest {

    @Test
    void constructor_shouldAcceptSixteenSources_whenCopilotCodeReviewSourcesAtLimit() {
        final GitHubPullRequestReviewCommentBuilder builder = GitHubPullRequestReviewCommentBuilder.builder(
                        PullrequestsCatalogFixtures.pullRequestReviewComment())
                .copilotCodeReviewSources(Collections.nCopies(
                        16, PullrequestsCatalogFixtures.pullRequestReviewCommentCopilotCodeReviewSource()));

        final GitHubPullRequestReviewComment value = builder.build();

        assertThat(value.copilotCodeReviewSources()).hasSize(16);
    }

    @Test
    void constructor_shouldThrow_whenCopilotCodeReviewSourcesAboveLimit() {
        final GitHubPullRequestReviewCommentBuilder builder = GitHubPullRequestReviewCommentBuilder.builder(
                        PullrequestsCatalogFixtures.pullRequestReviewComment())
                .copilotCodeReviewSources(Collections.nCopies(
                        17, PullrequestsCatalogFixtures.pullRequestReviewCommentCopilotCodeReviewSource()));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("copilotCodeReviewSources");
    }

    @Test
    void constructor_shouldAcceptNull_whenCopilotCodeReviewSourcesNull() {
        final GitHubPullRequestReviewCommentBuilder builder = GitHubPullRequestReviewCommentBuilder.builder(
                        PullrequestsCatalogFixtures.pullRequestReviewComment())
                .copilotCodeReviewSources(null);

        final GitHubPullRequestReviewComment value = builder.build();

        assertThat(value.copilotCodeReviewSources()).isNull();
    }
}
