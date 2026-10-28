package io.github.piotr_janczukowicz.github.api.interactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubInteractionLimitResponseTest {

    @Test
    void constructor_shouldThrow_whenLimitNull() {
        final Throwable thrown = catchThrowable(
                () -> new GitHubInteractionLimitResponse(null, "repository", Instant.parse("2018-08-17T04:18:39Z")));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("limit");
    }

    @Test
    void constructor_shouldThrow_whenOriginNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubInteractionLimitResponse(
                GitHubInteractionGroup.COLLABORATORS_ONLY, null, Instant.parse("2018-08-17T04:18:39Z")));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("origin");
    }

    @Test
    void constructor_shouldThrow_whenExpiresAtNull() {
        final Throwable thrown = catchThrowable(() ->
                new GitHubInteractionLimitResponse(GitHubInteractionGroup.COLLABORATORS_ONLY, "repository", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("expiresAt");
    }
}
