package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAutofixTest {

    @Test
    void constructor_shouldThrow_whenStatusNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAutofixBuilder.builder(CodeScanningCatalogFixtures.codeScanningAutofix())
                        .status(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldThrow_whenStartedAtNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAutofixBuilder.builder(CodeScanningCatalogFixtures.codeScanningAutofix())
                        .startedAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("startedAt");
    }
}
