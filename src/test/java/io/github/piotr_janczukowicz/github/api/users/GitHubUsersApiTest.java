package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUsersApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void constructor_shouldExposeChildApis_whenGithubHttpProvided() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubUsersApi api = new GitHubUsersApi(TestClients.caller(client));

            final GitHubUserApi user = api.user();
            final GitHubUsersBlockingApi blocking = api.blocking();
            final GitHubUsersEmailsApi emails = api.emails();
            final GitHubUsersFollowersApi followers = api.followers();
            final GitHubUsersGpgKeysApi gpgKeys = api.gpgKeys();
            final GitHubUsersKeysApi keys = api.keys();
            final GitHubUsersSocialAccountsApi socialAccounts = api.socialAccounts();
            final GitHubUsersSshSigningKeysApi sshSigningKeys = api.sshSigningKeys();
            final GitHubUsersAttestationsApi attestations = api.attestations();

            assertSoftly(softly -> {
                softly.assertThat(user).isInstanceOf(GitHubUserApi.class);
                softly.assertThat(blocking).isInstanceOf(GitHubUsersBlockingApi.class);
                softly.assertThat(emails).isInstanceOf(GitHubUsersEmailsApi.class);
                softly.assertThat(followers).isInstanceOf(GitHubUsersFollowersApi.class);
                softly.assertThat(gpgKeys).isInstanceOf(GitHubUsersGpgKeysApi.class);
                softly.assertThat(keys).isInstanceOf(GitHubUsersKeysApi.class);
                softly.assertThat(socialAccounts).isInstanceOf(GitHubUsersSocialAccountsApi.class);
                softly.assertThat(sshSigningKeys).isInstanceOf(GitHubUsersSshSigningKeysApi.class);
                softly.assertThat(attestations).isInstanceOf(GitHubUsersAttestationsApi.class);
            });
        }
    }
}
