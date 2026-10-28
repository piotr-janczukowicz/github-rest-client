package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAiScanEnablementTest {

    @Test
    void constructor_shouldThrow_whenPrScanNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningAiScanEnablementBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAiScanEnablement())
                .prScan(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prScan");
    }
}
