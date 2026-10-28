package io.github.piotr_janczukowicz.github.api.reactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateForReleasePayloadTest {

    @Test
    void constructor_shouldThrow_whenContentNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateForReleasePayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("content");
    }
}
