package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Collections;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewCommentTest {

    @Test
    void constructor_shouldAcceptSixteenSources_whenCopilotCodeReviewSourcesAtLimit() {
        final GitHubReviewCommentBuilder builder = GitHubReviewCommentBuilder.builder(
                        PullrequestsCatalogFixtures.reviewComment())
                .copilotCodeReviewSources(
                        Collections.nCopies(16, PullrequestsCatalogFixtures.reviewCommentCopilotCodeReviewSource()));

        final GitHubReviewComment value = builder.build();

        assertThat(value.copilotCodeReviewSources()).hasSize(16);
    }

    @Test
    void constructor_shouldThrow_whenCopilotCodeReviewSourcesAboveLimit() {
        final GitHubReviewCommentBuilder builder = GitHubReviewCommentBuilder.builder(
                        PullrequestsCatalogFixtures.reviewComment())
                .copilotCodeReviewSources(
                        Collections.nCopies(17, PullrequestsCatalogFixtures.reviewCommentCopilotCodeReviewSource()));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("copilotCodeReviewSources");
    }

    @Test
    void constructor_shouldAcceptNull_whenCopilotCodeReviewSourcesNull() {
        final GitHubReviewCommentBuilder builder = GitHubReviewCommentBuilder.builder(
                        PullrequestsCatalogFixtures.reviewComment())
                .copilotCodeReviewSources(null);

        final GitHubReviewComment value = builder.build();

        assertThat(value.copilotCodeReviewSources()).isNull();
    }
}
