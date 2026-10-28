package io.github.piotr_janczukowicz.github.api.reactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReactionTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubReaction(1L, null, null, GitHubReactionContent.HEART, Instant.EPOCH));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenContentNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReaction(1L, "n", null, null, Instant.EPOCH));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("content");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubReaction(1L, "n", null, GitHubReactionContent.HEART, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }
}
