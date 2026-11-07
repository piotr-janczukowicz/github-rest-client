package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotCodeReviewRequestTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubCopilotCodeReviewRequestBuilder builder = GitHubCopilotCodeReviewRequestBuilder.builder(
                        PullrequestsCatalogFixtures.copilotCodeReviewRequest())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubCopilotCodeReviewRequestBuilder builder = GitHubCopilotCodeReviewRequestBuilder.builder(
                        PullrequestsCatalogFixtures.copilotCodeReviewRequest())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }
}
