package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningVariantAnalysisRepositoryTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisRepositoryBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysisRepository())
                .name(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenFullNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningVariantAnalysisRepositoryBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningVariantAnalysisRepository())
                .fullName(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("fullName");
    }
}
