package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningVariantAnalysisRepoTaskTest {

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisRepoTaskBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysisRepoTask())
                .repository(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }

    @Test
    void constructor_shouldThrow_whenAnalysisStatusNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisRepoTaskBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysisRepoTask())
                .analysisStatus(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("analysisStatus");
    }
}
