package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningCodeqlDatabaseTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningCodeqlDatabaseBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningCodeqlDatabase())
                .name(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenLanguageNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningCodeqlDatabaseBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningCodeqlDatabase())
                .language(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("language");
    }

    @Test
    void constructor_shouldThrow_whenUploaderNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningCodeqlDatabaseBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningCodeqlDatabase())
                .uploader(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("uploader");
    }

    @Test
    void constructor_shouldThrow_whenContentTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningCodeqlDatabaseBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningCodeqlDatabase())
                .contentType(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("contentType");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningCodeqlDatabaseBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningCodeqlDatabase())
                .createdAt(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningCodeqlDatabaseBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningCodeqlDatabase())
                .updatedAt(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeScanningCodeqlDatabaseBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningCodeqlDatabase())
                .url(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
