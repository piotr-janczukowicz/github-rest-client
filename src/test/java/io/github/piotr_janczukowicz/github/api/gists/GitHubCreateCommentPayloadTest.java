package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCommentPayloadTest {
    @Test
    void constructor_shouldThrow_whenBodyNull() {
        final GitHubCreateCommentPayloadBuilder builder = GitHubCreateCommentPayloadBuilder.builder(
                        GistsCatalogFixtures.createCommentPayload())
                .body(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("body");
    }

    @Test
    void constructor_shouldThrow_whenBodyTooLong() {
        final GitHubCreateCommentPayloadBuilder builder = GitHubCreateCommentPayloadBuilder.builder(
                        GistsCatalogFixtures.createCommentPayload())
                .body("a".repeat(65_536));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("body");
    }
}
