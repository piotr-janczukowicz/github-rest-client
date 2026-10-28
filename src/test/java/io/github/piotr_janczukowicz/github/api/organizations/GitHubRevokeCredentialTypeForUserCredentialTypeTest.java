package io.github.piotr_janczukowicz.github.api.organizations;

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
    void wireValue_shouldReturnWire_whenCalledOnClassicPat() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.CLASSIC_PAT.wireValue())
                .isEqualTo("classic_pat");
    }

    @Test
    void fromWire_shouldReturnFineGrainedPat_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.fromWire("fine_grained_pat"))
                .isEqualTo(GitHubRevokeCredentialTypeForUserCredentialType.FINE_GRAINED_PAT);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnFineGrainedPat() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.FINE_GRAINED_PAT.wireValue())
                .isEqualTo("fine_grained_pat");
    }

    @Test
    void fromWire_shouldReturnSshKey_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.fromWire("ssh_key"))
                .isEqualTo(GitHubRevokeCredentialTypeForUserCredentialType.SSH_KEY);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnSshKey() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.SSH_KEY.wireValue())
                .isEqualTo("ssh_key");
    }

    @Test
    void fromWire_shouldReturnOauthAppToken_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.fromWire("oauth_app_token"))
                .isEqualTo(GitHubRevokeCredentialTypeForUserCredentialType.OAUTH_APP_TOKEN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnOauthAppToken() {
        assertThat(GitHubRevokeCredentialTypeForUserCredentialType.OAUTH_APP_TOKEN.wireValue())
                .isEqualTo("oauth_app_token");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubRevokeCredentialTypeForUserCredentialType.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubRevokeCredentialTypeForUserCredentialType.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
