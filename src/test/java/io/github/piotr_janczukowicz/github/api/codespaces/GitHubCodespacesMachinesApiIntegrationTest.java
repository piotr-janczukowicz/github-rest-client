package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.codespacesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCodespacesMachinesApiIntegrationTest {

    private static final String REPO_MACHINES_PATH = "/repos/octocat/hello-world/codespaces/machines";
    private static final String CODESPACE_MACHINES_PATH = "/user/codespaces/octocat-hello-world-abc123/machines";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listRepoMachines_shouldReturnMachines_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_MACHINES_PATH,
                okJson(jsonBytes(GitHubCodespacesMachinesApiIntegrationTest.class, "codespace-machines-page.json")));

        final GitHubCodespaceMachinesPage page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().machines().listRepoMachines("octocat", "hello-world");
        }

        assertThat(page).isEqualTo(CodespacesCatalogFixtures.codespaceMachinesPage());
        HTTP.verifyRequested(GET, REPO_MACHINES_PATH);
    }

    @Test
    void listCodespaceMachines_shouldReturnMachines_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CODESPACE_MACHINES_PATH,
                okJson(jsonBytes(GitHubCodespacesMachinesApiIntegrationTest.class, "codespace-machines-page.json")));

        final GitHubCodespaceMachinesPage page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().machines().listCodespaceMachines("octocat-hello-world-abc123");
        }

        assertThat(page).isEqualTo(CodespacesCatalogFixtures.codespaceMachinesPage());
        HTTP.verifyRequested(GET, CODESPACE_MACHINES_PATH);
    }
}
