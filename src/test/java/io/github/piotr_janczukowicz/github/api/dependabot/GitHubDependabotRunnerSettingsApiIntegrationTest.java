package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.dependabotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDependabotRunnerSettingsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String RUNNER_SETTINGS_PATH = "/repos/octocat/Hello-World/dependabot/runner-settings";
    private static final String SETTINGS_FIXTURE = "dependabot-runner-settings.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRepositoryRunnerSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RUNNER_SETTINGS_PATH,
                okJson(jsonBytes(GitHubDependabotRunnerSettingsApiIntegrationTest.class, SETTINGS_FIXTURE)));

        final GitHubDependabotRunnerSettings settings;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            settings = dependabot.api().runnerSettings().getRepositoryRunnerSettings(OWNER, REPO);
        }

        final GitHubDependabotRunnerSettings expected = CODEC.decode(
                jsonBytes(GitHubDependabotRunnerSettingsApiIntegrationTest.class, SETTINGS_FIXTURE),
                GitHubDependabotRunnerSettings.class);

        assertThat(settings).isEqualTo(expected);
        HTTP.verifyRequested(GET, RUNNER_SETTINGS_PATH);
    }

    @Test
    void getRepositoryRunnerSettings_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, RUNNER_SETTINGS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            final GitHubDependabotRunnerSettingsApi runnerSettings =
                    dependabot.api().runnerSettings();

            thrown = catchThrowable(() -> runnerSettings.getRepositoryRunnerSettings(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setRepositoryRunnerSettings_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                RUNNER_SETTINGS_PATH,
                okJson(jsonBytes(GitHubDependabotRunnerSettingsApiIntegrationTest.class, SETTINGS_FIXTURE)));
        final GitHubSetRepositoryRunnerSettingsPayload payload =
                DependabotCatalogFixtures.setRepositoryRunnerSettingsPayload();

        final GitHubDependabotRunnerSettings settings;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            settings = dependabot.api().runnerSettings().setRepositoryRunnerSettings(OWNER, REPO, payload);
        }

        assertThat(settings).isEqualTo(DependabotCatalogFixtures.dependabotRunnerSettings());
        HTTP.verifyRequested(PUT, RUNNER_SETTINGS_PATH);
        HTTP.verifyBody(PUT, RUNNER_SETTINGS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
