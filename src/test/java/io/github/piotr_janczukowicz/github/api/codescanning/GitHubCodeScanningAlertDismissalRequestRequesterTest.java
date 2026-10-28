package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertDismissalRequestRequesterTest {

    @Test
    void gitHubCodeScanningAlertDismissalRequestRequester_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCodeScanningAlertDismissalRequestRequester value =
                CodeScanningCatalogFixtures.codeScanningAlertDismissalRequestRequester();

        assertThat(value.actorId()).isEqualTo(583_231L);
        assertThat(value.actorName()).isEqualTo("octocat");
    }
}
