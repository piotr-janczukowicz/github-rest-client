package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRevokeCredentialTypeCredentialTypeTest {

    @Test
    void fromWire_shouldReturnClassicPat_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeCredentialType.fromWire("classic_pat"))
                .isEqualTo(GitHubRevokeCredentialTypeCredentialType.CLASSIC_PAT);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnClassicPat() {
        assertThat(GitHubRevokeCredentialTypeCredentialType.CLASSIC_PAT.wireValue())
                .isEqualTo("classic_pat");
    }

    @Test
    void fromWire_shouldReturnFineGrainedPat_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeCredentialType.fromWire("fine_grained_pat"))
                .isEqualTo(GitHubRevokeCredentialTypeCredentialType.FINE_GRAINED_PAT);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnFineGrainedPat() {
        assertThat(GitHubRevokeCredentialTypeCredentialType.FINE_GRAINED_PAT.wireValue())
                .isEqualTo("fine_grained_pat");
    }

    @Test
    void fromWire_shouldReturnSshKey_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeCredentialType.fromWire("ssh_key"))
                .isEqualTo(GitHubRevokeCredentialTypeCredentialType.SSH_KEY);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnSshKey() {
        assertThat(GitHubRevokeCredentialTypeCredentialType.SSH_KEY.wireValue()).isEqualTo("ssh_key");
    }

    @Test
    void fromWire_shouldReturnOauthAppToken_whenKnownValue() {
        assertThat(GitHubRevokeCredentialTypeCredentialType.fromWire("oauth_app_token"))
                .isEqualTo(GitHubRevokeCredentialTypeCredentialType.OAUTH_APP_TOKEN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnOauthAppToken() {
        assertThat(GitHubRevokeCredentialTypeCredentialType.OAUTH_APP_TOKEN.wireValue())
                .isEqualTo("oauth_app_token");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubRevokeCredentialTypeCredentialType.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubRevokeCredentialTypeCredentialType.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
