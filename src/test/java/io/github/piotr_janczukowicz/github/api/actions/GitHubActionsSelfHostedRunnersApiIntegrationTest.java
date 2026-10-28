package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsSelfHostedRunnersApiIntegrationTest {

    private static final String ORG_RUNNERS_PATH = "/orgs/github/actions/runners";
    private static final String ORG_RUNNER_PATH = "/orgs/github/actions/runners/1";
    private static final String ORG_RUNNER_LABELS_PATH = "/orgs/github/actions/runners/1/labels";
    private static final String REPO_RUNNERS_PATH = "/repos/octocat/Hello-World/actions/runners";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listSelfHostedRunnersForOrg_shouldReturnRunners_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_RUNNERS_PATH,
                okJson(jsonBytes(GitHubActionsSelfHostedRunnersApiIntegrationTest.class, "runners.json")));

        final GitHubPage<GitHubRunner> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().selfHostedRunners().listSelfHostedRunnersForOrg("github");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.runners().runners());
        HTTP.verifyQueryParam(GET, ORG_RUNNERS_PATH, "page", "1");
    }

    @Test
    void listRunnerApplicationsForOrg_shouldReturnApplications_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/github/actions/runners/downloads",
                okJson(listJsonBytes(
                        GitHubActionsSelfHostedRunnersApiIntegrationTest.class, "runner-application.json")));

        final List<GitHubRunnerApplication> applications;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            applications = actions.api().selfHostedRunners().listRunnerApplicationsForOrg("github");
        }

        assertThat(applications).containsExactly(ActionsCatalogFixtures.runnerApplication());
    }

    @Test
    void createRegistrationTokenForRepo_shouldReturnToken_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPO_RUNNERS_PATH + "/registration-token",
                createdJson(jsonBytes(
                        GitHubActionsSelfHostedRunnersApiIntegrationTest.class, "authentication-token.json")));

        final GitHubAuthenticationToken token;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            token = actions.api().selfHostedRunners().createRegistrationTokenForRepo("octocat", "Hello-World");
        }

        assertThat(token).isEqualTo(ActionsCatalogFixtures.authenticationToken());
    }

    @Test
    void listLabelsForSelfHostedRunnerForOrg_shouldReturnLabels_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_RUNNER_LABELS_PATH,
                okJson(jsonBytes(GitHubActionsSelfHostedRunnersApiIntegrationTest.class, "runner-labels.json")));

        final Optional<GitHubRunnerLabels> labels;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            labels = actions.api().selfHostedRunners().listLabelsForSelfHostedRunnerForOrg("github", 1L);
        }

        assertThat(labels).contains(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void getRunnerVersionDeprecationForOrg_shouldReturnDeprecation_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/github/actions/runners/deprecations/2.300.0",
                okJson(jsonBytes(
                        GitHubActionsSelfHostedRunnersApiIntegrationTest.class, "runner-version-deprecation.json")));

        final GitHubRunnerVersionDeprecation deprecation;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            deprecation = actions.api()
                    .selfHostedRunners()
                    .getRunnerVersionDeprecationForOrg("github", "2.300.0")
                    .orElseThrow();
        }

        assertThat(deprecation).isEqualTo(ActionsCatalogFixtures.runnerVersionDeprecation());
    }

    @Test
    void getRunnerVersionDeprecationForRepo_shouldReturnDeprecation_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_RUNNERS_PATH + "/deprecations/2.300.0",
                okJson(jsonBytes(
                        GitHubActionsSelfHostedRunnersApiIntegrationTest.class, "runner-version-deprecation.json")));

        final GitHubRunnerVersionDeprecation deprecation;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            deprecation = actions.api()
                    .selfHostedRunners()
                    .getRunnerVersionDeprecationForRepo("octocat", "Hello-World", "2.300.0")
                    .orElseThrow();
        }

        assertThat(deprecation).isEqualTo(ActionsCatalogFixtures.runnerVersionDeprecation());
    }

    @Test
    void deleteSelfHostedRunnerFromOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_RUNNER_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().selfHostedRunners().deleteSelfHostedRunnerFromOrg("github", 1L);
        }

        HTTP.verifyRequested(DELETE, ORG_RUNNER_PATH);
    }
}
