package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningVariantAnalysisSkippedRepositoriesTest {

    @Test
    void constructor_shouldThrow_whenAccessMismatchReposNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCodeScanningVariantAnalysisSkippedRepositoriesBuilder.builder(
                                CodeScanningCatalogFixtures.codeScanningVariantAnalysisSkippedRepositories())
                        .accessMismatchRepos(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("accessMismatchRepos");
    }

    @Test
    void constructor_shouldThrow_whenNotFoundReposNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCodeScanningVariantAnalysisSkippedRepositoriesBuilder.builder(
                                CodeScanningCatalogFixtures.codeScanningVariantAnalysisSkippedRepositories())
                        .notFoundRepos(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("notFoundRepos");
    }

    @Test
    void constructor_shouldThrow_whenNoCodeqlDbReposNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCodeScanningVariantAnalysisSkippedRepositoriesBuilder.builder(
                                CodeScanningCatalogFixtures.codeScanningVariantAnalysisSkippedRepositories())
                        .noCodeqlDbRepos(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("noCodeqlDbRepos");
    }

    @Test
    void constructor_shouldThrow_whenOverLimitReposNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCodeScanningVariantAnalysisSkippedRepositoriesBuilder.builder(
                                CodeScanningCatalogFixtures.codeScanningVariantAnalysisSkippedRepositories())
                        .overLimitRepos(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("overLimitRepos");
    }
}
