package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseSecurityAnalysisSettingsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubEnterpriseSecurityAnalysisSettings value =
                EnterpriseAdminCatalogFixtures.enterpriseSecurityAnalysisSettings();

        assertThat(value.advancedSecurityEnabledForNewRepositories()).isTrue();
        assertThat(value.advancedSecurityEnabledForNewUserNamespaceRepositories())
                .isTrue();
        assertThat(value.dependabotAlertsEnabledForNewRepositories()).isTrue();
        assertThat(value.secretScanningEnabledForNewRepositories()).isTrue();
        assertThat(value.secretScanningPushProtectionEnabledForNewRepositories())
                .isTrue();
        assertThat(value.secretScanningPushProtectionCustomLink())
                .isEqualTo("sample-secret_scanning_push_protection_custom_link");
        assertThat(value.secretScanningNonProviderPatternsEnabledForNewRepositories())
                .isTrue();
        assertThat(value.secretScanningValidityChecksEnabled()).isTrue();
    }
}
