package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCredentialAuthorizationCredentialTypeTest {

    @Test
    void fromWire_shouldReturnPersonalAccessToken_whenKnownValue() {
        assertThat(GitHubCredentialAuthorizationCredentialType.fromWire("personal access token"))
                .isEqualTo(GitHubCredentialAuthorizationCredentialType.PERSONAL_ACCESS_TOKEN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnPersonalAccessToken() {
        assertThat(GitHubCredentialAuthorizationCredentialType.PERSONAL_ACCESS_TOKEN.wireValue())
                .isEqualTo("personal access token");
    }

    @Test
    void fromWire_shouldReturnSshKey_whenKnownValue() {
        assertThat(GitHubCredentialAuthorizationCredentialType.fromWire("SSH key"))
                .isEqualTo(GitHubCredentialAuthorizationCredentialType.SSH_KEY);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnSshKey() {
        assertThat(GitHubCredentialAuthorizationCredentialType.SSH_KEY.wireValue())
                .isEqualTo("SSH key");
    }

    @Test
    void fromWire_shouldReturnOauthAppToken_whenKnownValue() {
        assertThat(GitHubCredentialAuthorizationCredentialType.fromWire("OAuth app token"))
                .isEqualTo(GitHubCredentialAuthorizationCredentialType.OAUTH_APP_TOKEN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnOauthAppToken() {
        assertThat(GitHubCredentialAuthorizationCredentialType.OAUTH_APP_TOKEN.wireValue())
                .isEqualTo("OAuth app token");
    }

    @Test
    void fromWire_shouldReturnGithubAppToken_whenKnownValue() {
        assertThat(GitHubCredentialAuthorizationCredentialType.fromWire("GitHub app token"))
                .isEqualTo(GitHubCredentialAuthorizationCredentialType.GITHUB_APP_TOKEN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnGithubAppToken() {
        assertThat(GitHubCredentialAuthorizationCredentialType.GITHUB_APP_TOKEN.wireValue())
                .isEqualTo("GitHub app token");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubCredentialAuthorizationCredentialType.fromWire("not-a-known-value"))
                .isEqualTo(GitHubCredentialAuthorizationCredentialType.UNKNOWN);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCredentialAuthorizationCredentialType.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
