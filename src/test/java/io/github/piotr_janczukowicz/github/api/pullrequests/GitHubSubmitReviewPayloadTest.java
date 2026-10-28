package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSubmitReviewPayloadTest {

    @Test
    void constructor_shouldThrow_whenEventNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSubmitReviewPayload("body", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("event");
    }

    @Test
    void constructor_shouldAcceptValues_whenEventPresent() {
        final GitHubSubmitReviewPayload payload = new GitHubSubmitReviewPayload(
                "Here is the body for the review.", GitHubPullRequestReviewEvent.REQUEST_CHANGES);

        assertThat(payload.event()).isEqualTo(GitHubPullRequestReviewEvent.REQUEST_CHANGES);
        assertThat(payload.body()).isEqualTo("Here is the body for the review.");
    }
}
