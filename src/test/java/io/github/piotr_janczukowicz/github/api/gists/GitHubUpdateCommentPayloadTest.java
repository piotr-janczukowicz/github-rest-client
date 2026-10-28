package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCommentPayloadTest {
    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubUpdateCommentPayloadBuilder builder = GitHubUpdateCommentPayloadBuilder.builder(
                        GistsCatalogFixtures.updateCommentPayload())
                .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenBodyTooLong() {
        final GitHubUpdateCommentPayloadBuilder builder = GitHubUpdateCommentPayloadBuilder.builder(
                        GistsCatalogFixtures.updateCommentPayload())
                .body("a".repeat(65_536));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }
}
