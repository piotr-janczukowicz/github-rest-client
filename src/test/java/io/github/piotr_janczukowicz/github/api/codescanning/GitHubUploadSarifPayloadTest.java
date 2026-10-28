package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubUploadSarifPayloadTest {

    @Test
    void constructor_shouldThrow_whenCommitShaNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUploadSarifPayloadBuilder.builder(CodeScanningCatalogFixtures.uploadSarifPayload())
                        .commitSha(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("commitSha");
    }

    @ParameterizedTest
    @MethodSource("invalidCommitShas")
    void constructor_shouldThrow_whenCommitShaInvalid(final String commitSha) {
        final GitHubUploadSarifPayloadBuilder builder = GitHubUploadSarifPayloadBuilder.builder(
                        CodeScanningCatalogFixtures.uploadSarifPayload())
                .commitSha(commitSha);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("commitSha");
    }

    @Test
    void constructor_shouldThrow_whenRefNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUploadSarifPayloadBuilder.builder(CodeScanningCatalogFixtures.uploadSarifPayload())
                        .ref(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }

    @Test
    void constructor_shouldThrow_whenRefInvalidFormat() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUploadSarifPayloadBuilder.builder(CodeScanningCatalogFixtures.uploadSarifPayload())
                        .ref("x".repeat(1))
                        .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ref");
    }

    @Test
    void constructor_shouldThrow_whenSarifNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUploadSarifPayloadBuilder.builder(CodeScanningCatalogFixtures.uploadSarifPayload())
                        .sarif(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sarif");
    }

    private static Stream<String> invalidCommitShas() {
        return Stream.of("x".repeat(65), "x".repeat(39), "x".repeat(40));
    }
}
