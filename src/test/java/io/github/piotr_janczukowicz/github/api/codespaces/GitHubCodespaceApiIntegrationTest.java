package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.codespacesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCodespaceApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String CODESPACE_NAME = "octocat-hello-world-abc123";
    private static final String USER_CODESPACES_PATH = "/user/codespaces";
    private static final String USER_CODESPACE_PATH = USER_CODESPACES_PATH + "/" + CODESPACE_NAME;

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listInRepository_shouldReturnCodespaces_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/repos/octocat/hello-world/codespaces",
                okJson(jsonBytes(GitHubCodespaceApiIntegrationTest.class, "codespaces-page.json")));

        final GitHubPage<GitHubCodespace> page;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            page = codespaces.api().codespace().listInRepository(OWNER, REPO);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        CodespacesCatalogFixtures.codespacesPage().codespaces());
        HTTP.verifyRequested(GET, "/repos/octocat/hello-world/codespaces");
        HTTP.verifyQueryParam(GET, "/repos/octocat/hello-world/codespaces", "page", "1");
        HTTP.verifyQueryParam(GET, "/repos/octocat/hello-world/codespaces", "per_page", "100");
    }

    @Test
    void createWithRepo_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                "/repos/octocat/hello-world/codespaces",
                createdJson(jsonBytes(GitHubCodespaceApiIntegrationTest.class, "codespace.json")));
        final GitHubCreateWithRepoForAuthenticatedUserPayload payload =
                CodespacesCatalogFixtures.createWithRepoForAuthenticatedUserPayload();

        final GitHubCodespace codespace;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespace = codespaces.api().codespace().createWithRepo(OWNER, REPO, payload);
        }

        assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
        HTTP.verifyRequested(POST, "/repos/octocat/hello-world/codespaces");
        HTTP.verifyHeader(POST, "/repos/octocat/hello-world/codespaces", CONTENT_TYPE, "application/json");
        HTTP.verifyBody(
                POST,
                "/repos/octocat/hello-world/codespaces",
                new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, USER_CODESPACE_PATH, notFound());

        final Optional<GitHubCodespace> codespace;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespace = codespaces.api().codespace().getForAuthenticatedUser(CODESPACE_NAME);
        }

        assertThat(codespace).isEmpty();
    }

    @Test
    void getForAuthenticatedUser_shouldReturnCodespace_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, USER_CODESPACE_PATH, okJson(jsonBytes(GitHubCodespaceApiIntegrationTest.class, "codespace.json")));

        final Optional<GitHubCodespace> codespace;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespace = codespaces.api().codespace().getForAuthenticatedUser(CODESPACE_NAME);
        }

        assertThat(codespace).contains(CodespacesCatalogFixtures.codespace());
        HTTP.verifyRequested(GET, USER_CODESPACE_PATH);
    }

    @Test
    void updateForAuthenticatedUser_shouldPatchPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                USER_CODESPACE_PATH,
                okJson(jsonBytes(GitHubCodespaceApiIntegrationTest.class, "codespace.json")));

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces
                    .api()
                    .codespace()
                    .updateForAuthenticatedUser(
                            CODESPACE_NAME, CodespacesCatalogFixtures.updateForAuthenticatedUserPayload());
        }

        HTTP.verifyRequested(PATCH, USER_CODESPACE_PATH);
    }

    @Test
    void deleteForAuthenticatedUser_shouldDelete_whenResponseIsAccepted() throws Exception {
        HTTP.stub(DELETE, USER_CODESPACE_PATH, accepted());

        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            codespaces.api().codespace().deleteForAuthenticatedUser(CODESPACE_NAME);
        }

        HTTP.verifyRequested(DELETE, USER_CODESPACE_PATH);
    }

    @Test
    void exportForAuthenticatedUser_shouldReturnExportDetails_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                POST,
                USER_CODESPACE_PATH + "/exports",
                createdJson(jsonBytes(GitHubCodespaceApiIntegrationTest.class, "codespace-export-details.json")));

        final GitHubCodespaceExportDetails details;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            details = codespaces.api().codespace().exportForAuthenticatedUser(CODESPACE_NAME);
        }

        assertThat(details).isEqualTo(CodespacesCatalogFixtures.codespaceExportDetails());
        HTTP.verifyRequested(POST, USER_CODESPACE_PATH + "/exports");
    }

    @Test
    void startAndStopForAuthenticatedUser_shouldPostAction_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                USER_CODESPACE_PATH + "/start",
                okJson(jsonBytes(GitHubCodespaceApiIntegrationTest.class, "codespace.json")));
        HTTP.stub(
                POST,
                USER_CODESPACE_PATH + "/stop",
                okJson(jsonBytes(GitHubCodespaceApiIntegrationTest.class, "codespace.json")));

        final GitHubCodespace started;
        final GitHubCodespace stopped;
        try (final GitHubApiWireMockSupport.CodespacesApiClient codespaces = codespacesApi(HTTP)) {
            started = codespaces.api().codespace().startForAuthenticatedUser(CODESPACE_NAME);
            stopped = codespaces.api().codespace().stopForAuthenticatedUser(CODESPACE_NAME);
        }

        assertThat(started).isEqualTo(CodespacesCatalogFixtures.codespace());
        assertThat(stopped).isEqualTo(CodespacesCatalogFixtures.codespace());
        HTTP.verifyRequested(POST, USER_CODESPACE_PATH + "/start");
        HTTP.verifyRequested(POST, USER_CODESPACE_PATH + "/stop");
    }
}
