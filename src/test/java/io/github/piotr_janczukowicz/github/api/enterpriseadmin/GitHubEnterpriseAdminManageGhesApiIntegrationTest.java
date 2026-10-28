package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.client.GhesVersion;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.GitHubManagementCredentials;
import io.github.piotr_janczukowicz.github.client.TestGitHubClients;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.http.transport.HttpTransports;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminManageGhesApiIntegrationTest {

    private static final String BASIC_OF_CONSOLE = "Basic YXBpX2tleTpzZWNyZXQ=";
    private static final String BEARER_OF_CLIENT = "Bearer " + TestClients.TOKEN;
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminManageGhesApiIntegrationTest.class;
    private static final String AUTHORIZATION = "Authorization";
    private static final String VERSION_PATH = "/manage/v1/version";
    private static final String SSH_PATH = "/manage/v1/access/ssh";
    private static final String STATS_PATH = "/api/v3/enterprise/stats/all";
    private static final GitHubManagementCredentials CREDENTIALS = new GitHubManagementCredentials("secret");

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getVersion_shouldSendTheConsolePasswordAsBasicAuth_whenClientAuthenticatesWithBearer() throws Exception {
        HTTP.stub(GET, VERSION_PATH, okJson(emptyList()));

        final List<GitHubManageVersion> versions;
        try (final HttpTransport transport = transport()) {
            versions = manageApi(transport).getVersion(CREDENTIALS);
        }

        assertThat(versions).isEmpty();
        HTTP.verifyHeader(GET, VERSION_PATH, AUTHORIZATION, BASIC_OF_CONSOLE);
    }

    @Test
    void setManageSsh_shouldSendTheConsolePasswordAsBasicAuthWithTheBody_whenClientAuthenticatesWithBearer()
            throws Exception {
        HTTP.stub(POST, SSH_PATH, okJson(emptyList()));

        try (final HttpTransport transport = transport()) {
            manageApi(transport).setManageSsh(CREDENTIALS, EnterpriseAdminCatalogFixtures.manageSetSshPayload());
        }

        HTTP.verifyHeader(POST, SSH_PATH, AUTHORIZATION, BASIC_OF_CONSOLE);
        HTTP.verifyBody(POST, SSH_PATH, "{\"key\":\"key\"}");
    }

    @Test
    void getAllStats_shouldKeepTheBearerOfTheClient_whenSameClientAlsoCallsTheConsole() throws Exception {
        HTTP.stub(GET, VERSION_PATH, okJson(emptyList()));
        HTTP.stub(GET, STATS_PATH, okJson(jsonBytes(RESOURCES, "enterprise-overview.json")));

        try (final HttpTransport transport = transport()) {
            final GitHubCaller caller = caller(transport);
            new GitHubEnterpriseAdminManageGhesApi(caller).getVersion(CREDENTIALS);
            new GitHubEnterpriseAdminAdminStatsApi(caller).getAllStats();
        }

        HTTP.verifyHeader(GET, VERSION_PATH, AUTHORIZATION, BASIC_OF_CONSOLE);
        HTTP.verifyHeader(GET, STATS_PATH, AUTHORIZATION, BEARER_OF_CLIENT);
    }

    private static HttpTransport transport() {
        return HttpTransports.okHttp();
    }

    private static GitHubEnterpriseAdminManageGhesApi manageApi(final HttpTransport transport) {
        return new GitHubEnterpriseAdminManageGhesApi(caller(transport));
    }

    private static GitHubCaller caller(final HttpTransport transport) {
        final GitHubClient client = TestGitHubClients.ghes(
                transport, TestClients.configuration(HTTP.uri("/api/v3/")), new GhesVersion(3, 22));
        return GitHubCaller.of(client);
    }

    private static byte[] emptyList() {
        return "[]".getBytes(StandardCharsets.UTF_8);
    }
}
