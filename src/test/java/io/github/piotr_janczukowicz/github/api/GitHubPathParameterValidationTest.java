package io.github.piotr_janczukowicz.github.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import io.github.piotr_janczukowicz.github.api.apps.GitHubAppApi;
import io.github.piotr_janczukowicz.github.api.apps.GitHubAppsOauthApplicationsApi;
import io.github.piotr_janczukowicz.github.api.apps.GitHubCheckTokenPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryApi;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.TestGitHubClients;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubPathParameterValidationTest {

    private static final GitHubCheckTokenPayload PAYLOAD = new GitHubCheckTokenPayload("token");

    private HttpTransport transport;
    private GitHub github;

    @BeforeEach
    void setUpGitHub() {
        transport = HttpTransportMocks.mock();
        github = new GitHub(GitHubCaller.of(TestGitHubClients.raw(transport)));
    }

    @Test
    void get_shouldThrowNullPointer_whenOwnerIsNull() throws Exception {
        final GitHubRepositoryApi repository = github.repositories().repository();

        final Throwable thrown = catchThrowable(() -> repository.get(null, "hello"));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
        verify(transport, never()).execute(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void get_shouldThrowIllegalArgument_whenOwnerIsBlank(final String owner) throws Exception {
        final GitHubRepositoryApi repository = github.repositories().repository();

        final Throwable thrown = catchThrowable(() -> repository.get(owner, "hello"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{owner}");
        verify(transport, never()).execute(any());
    }

    @Test
    void get_shouldThrowNullPointer_whenRepoIsNull() throws Exception {
        final GitHubRepositoryApi repository = github.repositories().repository();

        final Throwable thrown = catchThrowable(() -> repository.get("octo-org", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
        verify(transport, never()).execute(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void get_shouldThrowIllegalArgument_whenRepoIsBlank(final String repo) throws Exception {
        final GitHubRepositoryApi repository = github.repositories().repository();

        final Throwable thrown = catchThrowable(() -> repository.get("octo-org", repo));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{repo}");
        verify(transport, never()).execute(any());
    }

    @Test
    void checkToken_shouldThrowNullPointer_whenClientIdIsNull() throws Exception {
        final GitHubAppsOauthApplicationsApi oauth = github.apps().oauthApplications();

        final Throwable thrown = catchThrowable(() -> oauth.checkToken(null, PAYLOAD));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
        verify(transport, never()).execute(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void checkToken_shouldThrowIllegalArgument_whenClientIdIsBlank(final String clientId) throws Exception {
        final GitHubAppsOauthApplicationsApi oauth = github.apps().oauthApplications();

        final Throwable thrown = catchThrowable(() -> oauth.checkToken(clientId, PAYLOAD));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{client_id}");
        verify(transport, never()).execute(any());
    }

    @Test
    void getBySlug_shouldThrowNullPointer_whenAppSlugIsNull() throws Exception {
        final GitHubAppApi app = github.apps().app();

        final Throwable thrown = catchThrowable(() -> app.getBySlug(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
        verify(transport, never()).execute(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void getBySlug_shouldThrowIllegalArgument_whenAppSlugIsBlank(final String appSlug) throws Exception {
        final GitHubAppApi app = github.apps().app();

        final Throwable thrown = catchThrowable(() -> app.getBySlug(appSlug));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{app_slug}");
        verify(transport, never()).execute(any());
    }
}
