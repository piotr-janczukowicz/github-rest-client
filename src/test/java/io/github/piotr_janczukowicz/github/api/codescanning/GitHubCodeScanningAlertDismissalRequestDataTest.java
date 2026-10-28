package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertDismissalRequestDataTest {

    @Test
    void gitHubCodeScanningAlertDismissalRequestData_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCodeScanningAlertDismissalRequestData value =
                CodeScanningCatalogFixtures.codeScanningAlertDismissalRequestData();

        assertThat(value.reason()).isEqualTo("false_positive");
        assertThat(value.alertNumber()).isEqualTo("1");
        assertThat(value.prReviewThreadId()).isEqualTo("PRRT_1");
    }
}
