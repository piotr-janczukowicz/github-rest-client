package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemCredentialTypeTest {

    @Test
    void fromWire_shouldReturnClassicPat_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialType.fromWire("classic_pat"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.CLASSIC_PAT);
    }

    @Test
    void fromWire_shouldReturnOauthAppUserToken_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialType.fromWire("oauth_app_user_token"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.OAUTH_APP_USER_TOKEN);
    }

    @Test
    void fromWire_shouldReturnGithubAppUserToken_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialType.fromWire("github_app_user_token"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.GITHUB_APP_USER_TOKEN);
    }

    @Test
    void fromWire_shouldReturnFineGrainedPat_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialType.fromWire("fine_grained_pat"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.FINE_GRAINED_PAT);
    }

    @Test
    void fromWire_shouldReturnSshKey_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialType.fromWire("ssh_key"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.SSH_KEY);
    }

    @Test
    void fromWire_shouldReturnGithubAppInstallation_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialType.fromWire("github_app_installation"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.GITHUB_APP_INSTALLATION);
    }

    @Test
    void fromWire_shouldReturnFederatedJti_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialType.fromWire("federated_jti"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.FEDERATED_JTI);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubEnterpriseTokenInventoryItemCredentialType.fromWire("not-a-known-value"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.UNKNOWN);
    }
}
