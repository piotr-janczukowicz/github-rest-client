package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeForEnterpriseCredentialTypeTest {

    @Test
    void fromWire_shouldReturnClassicPat_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForEnterpriseCredentialType.fromWire("classic_pat"))
                .isEqualTo(GitHubRevokeCredentialTypeForEnterpriseCredentialType.CLASSIC_PAT);
    }

    @Test
    void fromWire_shouldReturnFineGrainedPat_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForEnterpriseCredentialType.fromWire("fine_grained_pat"))
                .isEqualTo(GitHubRevokeCredentialTypeForEnterpriseCredentialType.FINE_GRAINED_PAT);
    }

    @Test
    void fromWire_shouldReturnSshKey_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForEnterpriseCredentialType.fromWire("ssh_key"))
                .isEqualTo(GitHubRevokeCredentialTypeForEnterpriseCredentialType.SSH_KEY);
    }

    @Test
    void fromWire_shouldReturnOauthAppToken_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForEnterpriseCredentialType.fromWire("oauth_app_token"))
                .isEqualTo(GitHubRevokeCredentialTypeForEnterpriseCredentialType.OAUTH_APP_TOKEN);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubRevokeCredentialTypeForEnterpriseCredentialType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
