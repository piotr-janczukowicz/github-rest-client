package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.dependabotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDependabotAlertsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final int ALERT_NUMBER = 42;

    private static final String ENTERPRISE_ALERTS_PATH = "/enterprises/octo-enterprise/dependabot/alerts";
    private static final String ORG_ALERTS_PATH = "/orgs/octo-org/dependabot/alerts";
    private static final String REPO_ALERTS_PATH = "/repos/octocat/hello-world/dependabot/alerts";
    private static final String REPO_ALERT_PATH = "/repos/octocat/hello-world/dependabot/alerts/42";

    private static final JsonType<List<GitHubDependabotAlertWithRepository>> ALERTS_WITH_REPOSITORY_TYPE =
            JsonTypes.listOf(GitHubDependabotAlertWithRepository.class);
    private static final JsonType<List<GitHubDependabotAlert>> ALERTS_TYPE =
            JsonTypes.listOf(GitHubDependabotAlert.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAlertsForEnterprise_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_ALERTS_PATH,
                okJson(listJsonBytes(
                        GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert-with-repository.json")));

        final GitHubPage<GitHubDependabotAlertWithRepository> page;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            page = dependabot
                    .api()
                    .alerts()
                    .listAlertsForEnterprise(ENTERPRISE, DependabotCatalogFixtures.listAlertsForEnterpriseFilter());
        }

        final List<GitHubDependabotAlertWithRepository> expected = CODEC.decode(
                listJsonBytes(GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert-with-repository.json"),
                ALERTS_WITH_REPOSITORY_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ENTERPRISE_ALERTS_PATH);
        HTTP.verifyQueryParam(GET, ENTERPRISE_ALERTS_PATH, "state", "open,fixed");
        HTTP.verifyQueryParam(GET, ENTERPRISE_ALERTS_PATH, "has", "patch");
        HTTP.verifyQueryParam(GET, ENTERPRISE_ALERTS_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, ENTERPRISE_ALERTS_PATH, "page");
    }

    @Test
    void listAlertsForOrg_shouldFollowLinkNext_whenAfterCursorPresent() throws Exception {
        HTTP.stub(
                GET,
                ORG_ALERTS_PATH,
                okJson(listJsonBytes(
                                GitHubDependabotAlertsApiIntegrationTest.class,
                                "dependabot-alert-with-repository.json"))
                        .header(
                                LINK,
                                linkNext(HTTP.uri(
                                        ORG_ALERTS_PATH + "?after=cursor-after-token&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(
                        GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert-with-repository.json")));

        final GitHubPage<GitHubDependabotAlertWithRepository> first;
        final Optional<GitHubPage<GitHubDependabotAlertWithRepository>> second;
        final Optional<GitHubPage<GitHubDependabotAlertWithRepository>> third;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            first = dependabot.api().alerts().listAlertsForOrg(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final List<GitHubDependabotAlertWithRepository> expected = CODEC.decode(
                listJsonBytes(GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert-with-repository.json"),
                ALERTS_WITH_REPOSITORY_TYPE);

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected);
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected);
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ORG_ALERTS_PATH, 2);
        HTTP.verifyQueryParam(GET, ORG_ALERTS_PATH, "after", "cursor-after-token");
    }

    @Test
    void listAlertsForRepo_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ALERTS_PATH,
                okJson(listJsonBytes(GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert.json")));

        final GitHubPage<GitHubDependabotAlert> page;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            page = dependabot
                    .api()
                    .alerts()
                    .listAlertsForRepo(OWNER, REPO, DependabotCatalogFixtures.listAlertsForRepoFilter());
        }

        final List<GitHubDependabotAlert> expected = CODEC.decode(
                listJsonBytes(GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert.json"), ALERTS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, REPO_ALERTS_PATH);
        HTTP.verifyQueryParam(GET, REPO_ALERTS_PATH, "package", "django,lodash");
        HTTP.verifyQueryParam(GET, REPO_ALERTS_PATH, "manifest", "path/to/requirements.txt");
    }

    @Test
    void getAlert_shouldReturnAlert_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ALERT_PATH,
                okJson(jsonBytes(GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert.json")));

        final GitHubDependabotAlert alert;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            alert = dependabot
                    .api()
                    .alerts()
                    .getAlert(OWNER, REPO, ALERT_NUMBER)
                    .orElseThrow();
        }

        final GitHubDependabotAlert expected = CODEC.decode(
                jsonBytes(GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert.json"),
                GitHubDependabotAlert.class);

        assertThat(alert).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_ALERT_PATH);
    }

    @Test
    void getAlert_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_ALERT_PATH, notFound());

        final Optional<GitHubDependabotAlert> alert;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            alert = dependabot.api().alerts().getAlert(OWNER, REPO, ALERT_NUMBER);
        }

        assertThat(alert).isEmpty();
    }

    @Test
    void updateAlert_shouldReturnAlert_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                REPO_ALERT_PATH,
                okJson(jsonBytes(GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert.json")));
        final GitHubUpdateAlertPayload payload = DependabotCatalogFixtures.updateAlertPayload();

        final GitHubDependabotAlert alert;
        try (final GitHubApiWireMockSupport.DependabotApiClient dependabot = dependabotApi(HTTP)) {
            alert = dependabot.api().alerts().updateAlert(OWNER, REPO, ALERT_NUMBER, payload);
        }

        final GitHubDependabotAlert expected = CODEC.decode(
                jsonBytes(GitHubDependabotAlertsApiIntegrationTest.class, "dependabot-alert.json"),
                GitHubDependabotAlert.class);

        assertThat(alert).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, REPO_ALERT_PATH);
        HTTP.verifyHeader(PATCH, REPO_ALERT_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, REPO_ALERT_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
