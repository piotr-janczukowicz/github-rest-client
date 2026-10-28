package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.AUTHORIZATION;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.client;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.GitHubClients;
import io.github.piotr_janczukowicz.http.client.authentication.AuthenticationProviders;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAppsOauthApplicationsApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void checkToken_shouldPostPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                "/applications/Iv1.25b5d1e65ffc4022/token",
                okJson(jsonBytes(GitHubAppsOauthApplicationsApiIntegrationTest.class, "authorization.json")));

        final GitHubAuthorization authorization;
        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            authorization = new GitHubAppsOauthApplicationsApi(wireMock.caller())
                    .checkToken("Iv1.25b5d1e65ffc4022", AppsCatalogFixtures.checkTokenPayload());
        }

        assertThat(authorization).isEqualTo(AppsCatalogFixtures.authorization());
        HTTP.verifyRequested(POST, "/applications/Iv1.25b5d1e65ffc4022/token");
    }

    @Test
    void checkToken_shouldSendBasicCredentials_whenClientCredentials() throws Exception {
        HTTP.stub(
                POST,
                "/applications/Iv1.25b5d1e65ffc4022/token",
                okJson(jsonBytes(GitHubAppsOauthApplicationsApiIntegrationTest.class, "authorization.json")));

        try (final GitHubClient client = GitHubClients.create(
                AuthenticationProviders.basic("Iv1.25b5d1e65ffc4022", "s3cr3t"),
                TestClients.USER_AGENT,
                TestClients.configuration(HTTP.baseUri()))) {
            new GitHubAppsOauthApplicationsApi(TestClients.caller(client))
                    .checkToken("Iv1.25b5d1e65ffc4022", AppsCatalogFixtures.checkTokenPayload());
        }

        HTTP.verifyHeader(
                POST,
                "/applications/Iv1.25b5d1e65ffc4022/token",
                AUTHORIZATION,
                "Basic SXYxLjI1YjVkMWU2NWZmYzQwMjI6czNjcjN0");
    }

    @Test
    void deleteToken_shouldSendDelete_whenNoContent() throws Exception {
        HTTP.stub(DELETE, "/applications/Iv1.25b5d1e65ffc4022/token", noContent());

        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            new GitHubAppsOauthApplicationsApi(wireMock.caller())
                    .deleteToken("Iv1.25b5d1e65ffc4022", AppsCatalogFixtures.deleteTokenPayload());
        }

        HTTP.verifyRequested(DELETE, "/applications/Iv1.25b5d1e65ffc4022/token");
    }
}
