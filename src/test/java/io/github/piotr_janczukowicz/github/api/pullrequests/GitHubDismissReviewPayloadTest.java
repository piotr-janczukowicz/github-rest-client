package io.github.piotr_janczukowicz.github.api.pullrequests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDismissReviewPayloadTest {

    @Test
    void constructor_shouldThrow_whenMessageBlank() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubDismissReviewPayload(" ", GitHubDismissReviewEvent.DISMISS));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldAcceptValues_whenMessagePresent() {
        final GitHubDismissReviewPayload payload =
                new GitHubDismissReviewPayload("You are dismissed", GitHubDismissReviewEvent.DISMISS);

        assertThat(payload.message()).isEqualTo("You are dismissed");
        assertThat(payload.event()).isEqualTo(GitHubDismissReviewEvent.DISMISS);
    }
}
