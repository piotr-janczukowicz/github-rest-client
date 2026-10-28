package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertDismissalRequestRepositoryTest {

    @Test
    void gitHubCodeScanningAlertDismissalRequestRepository_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCodeScanningAlertDismissalRequestRepository value =
                CodeScanningCatalogFixtures.codeScanningAlertDismissalRequestRepository();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.name()).isEqualTo("octo-repo");
        assertThat(value.fullName()).isEqualTo("octo-org/octo-repo");
    }
}
