package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubDependabotAlertsApiTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final int ALERT_NUMBER = 42;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependabotAlertsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listAlertsForEnterprise_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert-with-repository.json") + "]"));

        final GitHubPage<GitHubDependabotAlertWithRepository> page = api().listAlertsForEnterprise(ENTERPRISE);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DependabotCatalogFixtures.dependabotAlertWithRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/enterprises/octo-enterprise/dependabot/alerts?"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAlertsForEnterprise_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert-with-repository.json") + "]"));

        api().listAlertsForEnterprise(ENTERPRISE, DependabotCatalogFixtures.listAlertsForEnterpriseFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("state", "open,fixed");
            softly.assertThat(requestUri).hasParameter("package", "django,lodash");
            softly.assertThat(requestUri).hasParameter("has", "patch");
            softly.assertThat(requestUri).hasParameter("sort", "epss_percentage");
            softly.assertThat(requestUri).hasParameter("direction", "asc");
        });
    }

    @Test
    void listAlertsForEnterprise_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert-with-repository.json") + "]"));

        api().listAlertsForEnterprise(
                        ENTERPRISE,
                        GitHubListAlertsForEnterpriseFilter.empty(),
                        GitHubCursorRequest.after("cursor-token"));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("after", "cursor-token");
    }

    @Test
    void listAlertsForOrg_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert-with-repository.json") + "]"));

        final GitHubPage<GitHubDependabotAlertWithRepository> page = api().listAlertsForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DependabotCatalogFixtures.dependabotAlertWithRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/orgs/octo-org/dependabot/alerts?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listAlertsForOrg_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert-with-repository.json") + "]"));

        api().listAlertsForOrg(ORG, DependabotCatalogFixtures.listAlertsForOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("artifact_registry", "jfrog-artifactory");
            softly.assertThat(requestUri).hasParameter("runtime_risk", "internet-exposed,sensitive-data");
            softly.assertThat(requestUri).hasParameter("has", "patch,deployment");
            softly.assertThat(requestUri).hasParameter("scope", "development");
        });
    }

    @Test
    void listAlertsForOrg_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert-with-repository.json") + "]"));

        api().listAlertsForOrg(ORG, GitHubListAlertsForOrgFilter.empty(), GitHubCursorRequest.before("cursor-token"));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("before", "cursor-token");
    }

    @Test
    void listAlertsForRepo_shouldReturnAlerts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert.json") + "]"));

        final GitHubPage<GitHubDependabotAlert> page = api().listAlertsForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DependabotCatalogFixtures.dependabotAlert());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/dependabot/alerts?"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAlertsForRepo_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert.json") + "]"));

        api().listAlertsForRepo(OWNER, REPO, DependabotCatalogFixtures.listAlertsForRepoFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("manifest", "path/to/requirements.txt");
            softly.assertThat(requestUri).hasParameter("relationship", "direct");
            softly.assertThat(requestUri).hasParameter("sort", "created");
        });
    }

    @Test
    void listAlertsForRepo_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-alert.json") + "]"));

        api().listAlertsForRepo(
                        OWNER, REPO, GitHubListAlertsForRepoFilter.empty(), GitHubCursorRequest.after("cursor-token"));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("after", "cursor-token");
    }

    @Test
    void getAlert_shouldReturnAlert_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("dependabot-alert.json")));

        final Optional<GitHubDependabotAlert> alert = api().getAlert(OWNER, REPO, ALERT_NUMBER);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(alert).contains(DependabotCatalogFixtures.dependabotAlert());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/dependabot/alerts/42");
        });
    }

    @Test
    void getAlert_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubDependabotAlert> alert = api().getAlert(OWNER, REPO, ALERT_NUMBER);

        assertThat(alert).isEmpty();
    }

    @Test
    void updateAlert_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("dependabot-alert.json")));

        final GitHubDependabotAlert alert =
                api().updateAlert(OWNER, REPO, ALERT_NUMBER, DependabotCatalogFixtures.updateAlertPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(alert).isEqualTo(DependabotCatalogFixtures.dependabotAlert());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/dependabot/alerts/42");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @ParameterizedTest
    @MethodSource("nullFilterCalls")
    void validation_shouldThrow_whenFilterNull(final AlertsCall call) {
        final GitHubDependabotAlertsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void updateAlert_shouldThrow_whenPayloadNull() {
        final GitHubDependabotAlertsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateAlert(OWNER, REPO, ALERT_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final AlertsCall call, final String messageFragment) {
        final GitHubDependabotAlertsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private GitHubDependabotAlertsApi api() {
        return new GitHubDependabotAlertsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDependabotAlertsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<AlertsCall> nullFilterCalls() {
        return Stream.of(
                api -> api.listAlertsForEnterprise(ENTERPRISE, null),
                api -> api.listAlertsForEnterprise(ENTERPRISE, null, GitHubPageRequest.byCursor()),
                api -> api.listAlertsForOrg(ORG, null),
                api -> api.listAlertsForOrg(ORG, null, GitHubPageRequest.byCursor()),
                api -> api.listAlertsForRepo(OWNER, REPO, null),
                api -> api.listAlertsForRepo(OWNER, REPO, null, GitHubPageRequest.byCursor()));
    }

    private static Stream<Arguments> invalidCalls() {
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedPage = GitHubPageRequest.byPage();
        return Stream.of(
                Arguments.of((AlertsCall) api -> api.listAlertsForEnterprise(" "), "enterprise"),
                Arguments.of((AlertsCall) api -> api.listAlertsForOrg(" "), "org"),
                Arguments.of((AlertsCall) api -> api.listAlertsForRepo(" ", REPO), "owner"),
                Arguments.of((AlertsCall) api -> api.listAlertsForRepo(OWNER, " "), "repo"),
                Arguments.of((AlertsCall) api -> api.getAlert(" ", REPO, ALERT_NUMBER), "owner"),
                Arguments.of((AlertsCall) api -> api.getAlert(OWNER, " ", ALERT_NUMBER), "repo"),
                Arguments.of(
                        (AlertsCall) api -> api.updateAlert(
                                " ", REPO, ALERT_NUMBER, DependabotCatalogFixtures.updateAlertPayload()),
                        "owner"),
                Arguments.of(
                        (AlertsCall) api -> api.listAlertsForEnterprise(
                                ENTERPRISE, GitHubListAlertsForEnterpriseFilter.empty(), unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (AlertsCall)
                                api -> api.listAlertsForOrg(ORG, GitHubListAlertsForOrgFilter.empty(), unsupportedPage),
                        "unsupported pagination request: page"),
                Arguments.of(
                        (AlertsCall) api -> api.listAlertsForRepo(
                                OWNER, REPO, GitHubListAlertsForRepoFilter.empty(), unsupportedPage),
                        "unsupported pagination request: page"));
    }

    @FunctionalInterface
    interface AlertsCall {
        void execute(GitHubDependabotAlertsApi api) throws Exception;
    }
}
