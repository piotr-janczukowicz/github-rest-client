package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.client;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallation;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAppsInstallationsApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listInstallationsForAuthenticatedUser_shouldReturnInstallations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/user/installations",
                okJson(jsonBytes(
                        GitHubAppsInstallationsApiIntegrationTest.class,
                        "list-installations-for-authenticated-user-page.json")));

        final GitHubPage<GitHubInstallation> page;
        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            page = new GitHubAppsInstallationsApi(wireMock.caller())
                    .listInstallationsForAuthenticatedUser(GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.installation());
        HTTP.verifyRequested(GET, "/user/installations");
        HTTP.verifyQueryParam(GET, "/user/installations", "page", "1");
    }

    @Test
    void revokeInstallationAccessToken_shouldSendDelete_whenNoContent() throws Exception {
        HTTP.stub(DELETE, "/installation/token", noContent());

        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            new GitHubAppsInstallationsApi(wireMock.caller()).revokeInstallationAccessToken();
        }

        HTTP.verifyRequested(DELETE, "/installation/token");
    }
}
