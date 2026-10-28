package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningVariantAnalysisTest {

    @Test
    void constructor_shouldThrow_whenControllerRepoNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysis())
                .controllerRepo(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("controllerRepo");
    }

    @Test
    void constructor_shouldThrow_whenActorNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysis())
                .actor(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("actor");
    }

    @Test
    void constructor_shouldThrow_whenQueryLanguageNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysis())
                .queryLanguage(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("queryLanguage");
    }

    @Test
    void constructor_shouldThrow_whenQueryPackUrlNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysis())
                .queryPackUrl(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("queryPackUrl");
    }

    @Test
    void constructor_shouldThrow_whenStatusNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysis())
                .status(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldLeaveScannedRepositoriesNull_whenNull() {
        final GitHubCodeScanningVariantAnalysis built = GitHubCodeScanningVariantAnalysisBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysis())
                .scannedRepositories(null)
                .build();

        assertThat(built.scannedRepositories()).isNull();
    }
}
