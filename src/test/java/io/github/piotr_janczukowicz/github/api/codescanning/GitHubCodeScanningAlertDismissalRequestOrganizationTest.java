package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertDismissalRequestOrganizationTest {

    @Test
    void gitHubCodeScanningAlertDismissalRequestOrganization_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCodeScanningAlertDismissalRequestOrganization value =
                CodeScanningCatalogFixtures.codeScanningAlertDismissalRequestOrganization();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.name()).isEqualTo("octo-org");
    }
}
