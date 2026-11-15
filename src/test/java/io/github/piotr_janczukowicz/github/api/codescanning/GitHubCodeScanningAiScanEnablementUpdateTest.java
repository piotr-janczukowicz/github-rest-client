package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAiScanEnablementUpdateTest {

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenNoPropertyGiven() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeScanningAiScanEnablementUpdate(null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("prScan");
    }

    @Test
    void constructor_shouldAccept_whenOnlyPrScanGiven() {
        final GitHubCodeScanningAiScanEnablementUpdate update =
                new GitHubCodeScanningAiScanEnablementUpdate(GitHubCodeScanningAiScanPrScan.ENABLED, null);

        assertThat(update.prScan()).isEqualTo(GitHubCodeScanningAiScanPrScan.ENABLED);
    }

    @Test
    void constructor_shouldAccept_whenOnlyRepositoryScanGiven() {
        final GitHubCodeScanningAiScanEnablementUpdate update =
                new GitHubCodeScanningAiScanEnablementUpdate(null, GitHubCodeScanningAiScanPrScan.DISABLED);

        assertThat(update.repositoryScan()).isEqualTo(GitHubCodeScanningAiScanPrScan.DISABLED);
    }
}
