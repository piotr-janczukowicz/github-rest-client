package io.github.piotr_janczukowicz.github.api.releases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubReleaseTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubReleaseBuilder.builder().build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
