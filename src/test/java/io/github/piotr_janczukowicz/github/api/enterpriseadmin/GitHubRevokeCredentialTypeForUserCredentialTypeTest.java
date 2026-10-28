package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeForUserCredentialTypeTest {

    @Test
    void fromWire_shouldReturnClassicPat_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.fromWire("classic_pat"))
                .isEqualTo(GitHubRevokeCredentialTypeForUserCredentialType.CLASSIC_PAT);
    }

    @Test
    void fromWire_shouldReturnFineGrainedPat_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.fromWire("fine_grained_pat"))
                .isEqualTo(GitHubRevokeCredentialTypeForUserCredentialType.FINE_GRAINED_PAT);
    }

    @Test
    void fromWire_shouldReturnSshKey_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.fromWire("ssh_key"))
                .isEqualTo(GitHubRevokeCredentialTypeForUserCredentialType.SSH_KEY);
    }

    @Test
    void fromWire_shouldReturnOauthAppToken_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.fromWire("oauth_app_token"))
                .isEqualTo(GitHubRevokeCredentialTypeForUserCredentialType.OAUTH_APP_TOKEN);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubRevokeCredentialTypeForUserCredentialType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
