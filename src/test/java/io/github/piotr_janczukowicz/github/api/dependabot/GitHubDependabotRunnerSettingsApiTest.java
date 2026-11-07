package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubDependabotRunnerSettingsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String RUNNER_SETTINGS_URL =
            "https://api.github.com/repos/octocat/Hello-World/dependabot/runner-settings";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependabotRunnerSettingsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getRepositoryRunnerSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-runner-settings.json")));

        final GitHubDependabotRunnerSettings settings = api().getRepositoryRunnerSettings(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(settings).isEqualTo(DependabotCatalogFixtures.dependabotRunnerSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(RUNNER_SETTINGS_URL);
        });
    }

    @Test
    void getRepositoryRunnerSettings_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));
        final GitHubDependabotRunnerSettingsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getRepositoryRunnerSettings(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getRepositoryRunnerSettings_shouldThrow_whenOwnerBlank() {
        final GitHubDependabotRunnerSettingsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getRepositoryRunnerSettings(" ", REPO));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void setRepositoryRunnerSettings_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-runner-settings.json")));

        final GitHubDependabotRunnerSettings settings = api().setRepositoryRunnerSettings(
                        OWNER, REPO, DependabotCatalogFixtures.setRepositoryRunnerSettingsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(settings).isEqualTo(DependabotCatalogFixtures.dependabotRunnerSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString(RUNNER_SETTINGS_URL);
        });
    }

    @Test
    void setRepositoryRunnerSettings_shouldThrow_whenPayloadNull() {
        final GitHubDependabotRunnerSettingsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setRepositoryRunnerSettings(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void setRepositoryRunnerSettings_shouldThrow_whenRepoBlank() {
        final GitHubDependabotRunnerSettingsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setRepositoryRunnerSettings(
                OWNER, " ", DependabotCatalogFixtures.setRepositoryRunnerSettingsPayload()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    private GitHubDependabotRunnerSettingsApi api() {
        return new GitHubDependabotRunnerSettingsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDependabotRunnerSettingsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
