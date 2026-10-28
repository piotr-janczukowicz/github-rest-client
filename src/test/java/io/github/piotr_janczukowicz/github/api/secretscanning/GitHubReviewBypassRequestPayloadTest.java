package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewBypassRequestPayloadTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubReviewBypassRequestPayload sut = SecretScanningCatalogFixtures.reviewBypassRequestPayload();

        assertSoftly(softly -> {
            softly.assertThat(sut.status()).isNotNull();
            softly.assertThat(sut.message()).isNotNull();
        });
    }

    @Test
    void constructor_shouldThrow_whenStatusNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReviewBypassRequestPayload(null, "message"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubReviewBypassRequestPayload(GitHubReviewBypassRequestStatus.APPROVE, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }

    @Test
    void constructor_shouldThrow_whenMessageTooLong() {
        final String message = "x".repeat(2049);

        final Throwable thrown = catchThrowable(
                () -> new GitHubReviewBypassRequestPayload(GitHubReviewBypassRequestStatus.APPROVE, message));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("message");
    }
}
