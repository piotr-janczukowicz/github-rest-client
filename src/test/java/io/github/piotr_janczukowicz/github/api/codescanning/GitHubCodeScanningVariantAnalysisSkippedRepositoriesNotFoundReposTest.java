package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningVariantAnalysisSkippedRepositoriesNotFoundReposTest {

    @Test
    void constructor_shouldThrow_whenRepositoryFullNamesNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCodeScanningVariantAnalysisSkippedRepositoriesNotFoundReposBuilder.builder(
                                CodeScanningCatalogFixtures
                                        .codeScanningVariantAnalysisSkippedRepositoriesNotFoundRepos())
                        .repositoryFullNames(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositoryFullNames");
    }
}
