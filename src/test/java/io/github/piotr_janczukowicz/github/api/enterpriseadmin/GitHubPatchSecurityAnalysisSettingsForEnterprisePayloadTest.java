package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPatchSecurityAnalysisSettingsForEnterprisePayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubPatchSecurityAnalysisSettingsForEnterprisePayload value =
                EnterpriseAdminCatalogFixtures.patchSecurityAnalysisSettingsForEnterprisePayload();

        assertThat(value.advancedSecurityEnabledForNewRepositories()).isTrue();
        assertThat(value.advancedSecurityEnabledNewUserNamespaceRepos()).isTrue();
        assertThat(value.dependabotAlertsEnabledForNewRepositories()).isTrue();
        assertThat(value.secretScanningEnabledForNewRepositories()).isTrue();
        assertThat(value.secretScanningPushProtectionEnabledForNewRepositories())
                .isTrue();
        assertThat(value.secretScanningPushProtectionCustomLink())
                .isEqualTo("sample-secret_scanning_push_protection_custom_link");
        assertThat(value.secretScanningNonProviderPatternsEnabledForNewRepositories())
                .isTrue();
    }
}
