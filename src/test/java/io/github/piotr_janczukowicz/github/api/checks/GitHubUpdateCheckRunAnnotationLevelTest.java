package io.github.piotr_janczukowicz.github.api.checks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCheckRunAnnotationLevelTest {

    @Test
    void fromWire_shouldReturnValue_whenKnownValue() {
        final GitHubUpdateCheckRunAnnotationLevel value = GitHubUpdateCheckRunAnnotationLevel.fromWire("notice");

        assertThat(value).isEqualTo(GitHubUpdateCheckRunAnnotationLevel.NOTICE);
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateCheckRunAnnotationLevel.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateCheckRunAnnotationLevel.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
