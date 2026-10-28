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
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAppApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getInstallation_shouldReturnInstallation_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, "/app/installations/1", okJson(jsonBytes(GitHubAppApiIntegrationTest.class, "installation.json")));

        final GitHubInstallation installation;
        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            installation =
                    new GitHubAppApi(wireMock.caller()).getInstallation(1L).orElseThrow();
        }

        assertThat(installation).isEqualTo(OrganizationsCatalogFixtures.installation());
        HTTP.verifyRequested(GET, "/app/installations/1");
    }

    @Test
    void deleteInstallation_shouldSendDelete_whenAccepted() throws Exception {
        HTTP.stub(DELETE, "/app/installations/1", noContent());

        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            new GitHubAppApi(wireMock.caller()).deleteInstallation(1L);
        }

        HTTP.verifyRequested(DELETE, "/app/installations/1");
    }
}
