package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCodeScanningAnalysisTest {

    @Test
    void constructor_shouldThrow_whenRefNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .ref(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }

    @Test
    void constructor_shouldThrow_whenCommitShaNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .commitSha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commitSha");
    }

    @ParameterizedTest
    @MethodSource("invalidCommitShas")
    void constructor_shouldThrow_whenCommitShaInvalid(final String commitSha) {
        final GitHubCodeScanningAnalysisBuilder builder = GitHubCodeScanningAnalysisBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAnalysis())
                .commitSha(commitSha);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commitSha");
    }

    @Test
    void constructor_shouldThrow_whenAnalysisKeyNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .analysisKey(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("analysisKey");
    }

    @Test
    void constructor_shouldThrow_whenEnvironmentNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .environment(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environment");
    }

    @Test
    void constructor_shouldThrow_whenErrorNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .error(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("error");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .createdAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenSarifIdNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .sarifId(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sarifId");
    }

    @Test
    void constructor_shouldThrow_whenToolNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .tool(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tool");
    }

    @Test
    void constructor_shouldThrow_whenWarningNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeScanningAnalysisBuilder.builder(CodeScanningCatalogFixtures.codeScanningAnalysis())
                        .warning(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("warning");
    }

    private static Stream<String> invalidCommitShas() {
        return Stream.of("x".repeat(65), "x".repeat(39), "x".repeat(40));
    }
}
