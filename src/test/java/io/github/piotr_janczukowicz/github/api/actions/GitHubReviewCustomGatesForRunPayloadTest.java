package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReviewCustomGatesForRunPayloadTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void constructor_shouldThrowNullPointerException_whenEnvironmentNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReviewCustomGatesForRunPayload(
                null, GitHubReviewCustomGatesState.APPROVED, "comment-sample"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environmentName");
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenStateAndCommentNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubReviewCustomGatesForRunPayload("production", null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldAccept_whenOnlyStateSet() {
        final GitHubReviewCustomGatesForRunPayload payload =
                new GitHubReviewCustomGatesForRunPayload("production", GitHubReviewCustomGatesState.APPROVED, null);

        assertThat(payload.comment()).isNull();
    }

    @Test
    void decode_shouldReturnPayload_whenEncodedFromFixture() {
        final GitHubReviewCustomGatesForRunPayload payload = ActionsCatalogFixtures.reviewCustomGatesForRunPayload();

        final byte[] json = CODEC.encode(payload);

        final GitHubReviewCustomGatesForRunPayload decoded =
                CODEC.decode(json, GitHubReviewCustomGatesForRunPayload.class);

        assertThat(decoded).isEqualTo(payload);
    }

    @Test
    void constructor_shouldAccept_whenOnlyCommentSet() {
        final GitHubReviewCustomGatesForRunPayload payload =
                new GitHubReviewCustomGatesForRunPayload("production", null, "looks good");

        assertThat(payload.comment()).isEqualTo("looks good");
    }
}
