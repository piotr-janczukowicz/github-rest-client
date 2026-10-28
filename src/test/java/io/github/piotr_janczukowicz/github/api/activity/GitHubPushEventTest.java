package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPushEventTest {

    @Test
    void constructor_shouldThrow_whenRefNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubPushEvent(1L, 2L, null, "head", "before", null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }
}
