package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseSecurityProductTest {

    @Test
    void fromWire_shouldReturnAdvancedSecurity_whenKnownValue() {
        assertThat(GitHubEnterpriseSecurityProduct.fromWire("advanced_security"))
                .isEqualTo(GitHubEnterpriseSecurityProduct.ADVANCED_SECURITY);
    }

    @Test
    void fromWire_shouldReturnAdvancedSecurityUserNamespace_whenKnownValue() {
        assertThat(GitHubEnterpriseSecurityProduct.fromWire("advanced_security_user_namespace"))
                .isEqualTo(GitHubEnterpriseSecurityProduct.ADVANCED_SECURITY_USER_NAMESPACE);
    }

    @Test
    void fromWire_shouldReturnDependabotAlerts_whenKnownValue() {
        assertThat(GitHubEnterpriseSecurityProduct.fromWire("dependabot_alerts"))
                .isEqualTo(GitHubEnterpriseSecurityProduct.DEPENDABOT_ALERTS);
    }

    @Test
    void fromWire_shouldReturnSecretScanning_whenKnownValue() {
        assertThat(GitHubEnterpriseSecurityProduct.fromWire("secret_scanning"))
                .isEqualTo(GitHubEnterpriseSecurityProduct.SECRET_SCANNING);
    }

    @Test
    void fromWire_shouldReturnSecretScanningPushProtection_whenKnownValue() {
        assertThat(GitHubEnterpriseSecurityProduct.fromWire("secret_scanning_push_protection"))
                .isEqualTo(GitHubEnterpriseSecurityProduct.SECRET_SCANNING_PUSH_PROTECTION);
    }

    @Test
    void fromWire_shouldReturnSecretScanningNonProviderPatterns_whenKnownValue() {
        assertThat(GitHubEnterpriseSecurityProduct.fromWire("secret_scanning_non_provider_patterns"))
                .isEqualTo(GitHubEnterpriseSecurityProduct.SECRET_SCANNING_NON_PROVIDER_PATTERNS);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubEnterpriseSecurityProduct.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
