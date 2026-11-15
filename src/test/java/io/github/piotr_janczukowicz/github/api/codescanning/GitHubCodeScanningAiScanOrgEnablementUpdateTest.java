package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAiScanOrgEnablementUpdateTest {

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenNoPropertyGiven() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeScanningAiScanOrgEnablementUpdate(null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("prScan");
    }

    @Test
    void constructor_shouldAccept_whenOnlyPrScanGiven() {
        final GitHubCodeScanningAiScanOrgEnablementUpdate update =
                new GitHubCodeScanningAiScanOrgEnablementUpdate(GitHubCodeScanningAiScanPrScan.ENABLED, null);

        assertThat(update.prScan()).isEqualTo(GitHubCodeScanningAiScanPrScan.ENABLED);
    }

    @Test
    void constructor_shouldAccept_whenOnlyRepositoryScanGiven() {
        final GitHubCodeScanningAiScanOrgEnablementUpdate update =
                new GitHubCodeScanningAiScanOrgEnablementUpdate(null, GitHubCodeScanningAiScanPrScan.DISABLED);

        assertThat(update.repositoryScan()).isEqualTo(GitHubCodeScanningAiScanPrScan.DISABLED);
    }
}
