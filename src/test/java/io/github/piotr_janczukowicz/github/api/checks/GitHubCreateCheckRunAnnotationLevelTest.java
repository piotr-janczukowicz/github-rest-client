package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCheckRunAnnotationLevelTest {

    @Test
    void fromWire_shouldReturnValue_whenKnownValue() {
        final GitHubCreateCheckRunAnnotationLevel value = GitHubCreateCheckRunAnnotationLevel.fromWire("warning");

        assertThat(value).isEqualTo(GitHubCreateCheckRunAnnotationLevel.WARNING);
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateCheckRunAnnotationLevel.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateCheckRunAnnotationLevel.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
