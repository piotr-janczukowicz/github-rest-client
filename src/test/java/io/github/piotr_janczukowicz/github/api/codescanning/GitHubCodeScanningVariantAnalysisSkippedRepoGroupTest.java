package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningVariantAnalysisSkippedRepoGroupTest {

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisSkippedRepoGroupBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysisSkippedRepoGroup())
                .repositories(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}
