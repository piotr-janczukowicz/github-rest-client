package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCodespaceApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String CODESPACE_NAME = "octocat-hello-world-abc123";
    private static final String EXPORT_ID = "export-1";
    private static final int PULL_NUMBER = 1;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodespaceApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listInRepository_shouldReturnCodespaces_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-page.json")));

        final GitHubPage<GitHubCodespace> page = api().listInRepository(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CodespacesCatalogFixtures.codespacesPage().codespaces());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/codespaces?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listDevcontainersInRepository_shouldReturnDevcontainers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("devcontainers-page.json")));

        final GitHubPage<GitHubDevcontainer> page = api().listDevcontainersInRepository(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CodespacesCatalogFixtures.devcontainersPage().devcontainers());
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/hello-world/codespaces/devcontainers");
        });
    }

    @Test
    void createWithRepo_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("codespace.json")));

        final GitHubCodespace codespace = api().createWithRepo(
                        OWNER, REPO, CodespacesCatalogFixtures.createWithRepoForAuthenticatedUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/hello-world/codespaces");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void createWithRepo_shouldThrow_whenPayloadNull() {
        final GitHubCodespaceApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createWithRepo(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void preFlightWithRepo_shouldReturnDefaults_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), json("pre-flight-with-repo-for-authenticated-user-response.json")));

        final GitHubPreFlightWithRepoForAuthenticatedUserResponse response = api().preFlightWithRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(response)
                    .isEqualTo(CodespacesCatalogFixtures.preFlightWithRepoForAuthenticatedUserResponse());
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/hello-world/codespaces/new");
        });
    }

    @Test
    void preFlightWithRepo_shouldSendFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), json("pre-flight-with-repo-for-authenticated-user-response.json")));

        api().preFlightWithRepo(OWNER, REPO, CodespacesCatalogFixtures.preFlightWithRepoForAuthenticatedUserFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("ref", "main").hasParameter("client_ip", "1.2.3.4");
    }

    @Test
    void checkPermissionsForDevcontainer_shouldReturnAccepted_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("codespaces-permissions-check-for-devcontainer.json")));

        final GitHubCodespacesPermissionsCheckForDevcontainer result = api().checkPermissionsForDevcontainer(
                        OWNER, REPO, CodespacesCatalogFixtures.checkPermissionsForDevcontainerFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CodespacesCatalogFixtures.codespacesPermissionsCheckForDevcontainer());
            softly.assertThat(requestUri.getPath())
                    .isEqualTo("/repos/octocat/hello-world/codespaces/permissions_check");
            softly.assertThat(requestUri).hasParameter("devcontainer_path", ".devcontainer/devcontainer.json");
        });
    }

    @Test
    void createWithPullRequest_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("codespace.json")));

        final GitHubCodespace codespace = api().createWithPullRequest(
                        OWNER, REPO, PULL_NUMBER, CodespacesCatalogFixtures.createWithPrForAuthenticatedUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/hello-world/pulls/1/codespaces");
        });
    }

    @Test
    void listForAuthenticatedUser_shouldReturnCodespaces_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-page.json")));

        final GitHubPage<GitHubCodespace> page = api().listForAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CodespacesCatalogFixtures.codespacesPage().codespaces());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/user/codespaces?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listForAuthenticatedUser_shouldSendRepositoryId_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-page.json")));

        api().listForAuthenticatedUser(CodespacesCatalogFixtures.listForAuthenticatedUserFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("repository_id", "1296269");
    }

    @Test
    void createForAuthenticatedUser_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("codespace.json")));

        final GitHubCodespace codespace =
                api().createForAuthenticatedUser(CodespacesCatalogFixtures.createForAuthenticatedUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces");
        });
    }

    @Test
    void getForAuthenticatedUser_shouldReturnCodespace_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespace.json")));

        final Optional<GitHubCodespace> codespace = api().getForAuthenticatedUser(CODESPACE_NAME);

        assertThat(codespace).contains(CodespacesCatalogFixtures.codespace());
    }

    @Test
    void getForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodespace> codespace = api().getForAuthenticatedUser(CODESPACE_NAME);

        assertThat(codespace).isEmpty();
    }

    @Test
    void updateForAuthenticatedUser_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespace.json")));

        final GitHubCodespace codespace = api().updateForAuthenticatedUser(
                        CODESPACE_NAME, CodespacesCatalogFixtures.updateForAuthenticatedUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/octocat-hello-world-abc123");
        });
    }

    @Test
    void deleteForAuthenticatedUser_shouldDelete_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().deleteForAuthenticatedUser(CODESPACE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/octocat-hello-world-abc123");
        });
    }

    @Test
    void exportForAuthenticatedUser_shouldReturnExportDetails_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("codespace-export-details.json")));

        final GitHubCodespaceExportDetails details = api().exportForAuthenticatedUser(CODESPACE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(details).isEqualTo(CodespacesCatalogFixtures.codespaceExportDetails());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/octocat-hello-world-abc123/exports");
        });
    }

    @Test
    void getExportDetailsForAuthenticatedUser_shouldReturnDetails_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("codespace-export-details.json")));

        final Optional<GitHubCodespaceExportDetails> details =
                api().getExportDetailsForAuthenticatedUser(CODESPACE_NAME, EXPORT_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(details).contains(CodespacesCatalogFixtures.codespaceExportDetails());
            softly.assertThat(requestUri.getPath())
                    .isEqualTo("/user/codespaces/octocat-hello-world-abc123/exports/export-1");
        });
    }

    @Test
    void getExportDetailsForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodespaceExportDetails> details =
                api().getExportDetailsForAuthenticatedUser(CODESPACE_NAME, EXPORT_ID);

        assertThat(details).isEmpty();
    }

    @Test
    void publishForAuthenticatedUser_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("codespace-with-full-repository.json")));

        final GitHubCodespaceWithFullRepository published = api().publishForAuthenticatedUser(
                        CODESPACE_NAME, CodespacesCatalogFixtures.publishForAuthenticatedUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(published).isEqualTo(CodespacesCatalogFixtures.codespaceWithFullRepository());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/octocat-hello-world-abc123/publish");
        });
    }

    @Test
    void startForAuthenticatedUser_shouldPostStart_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespace.json")));

        final GitHubCodespace codespace = api().startForAuthenticatedUser(CODESPACE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/octocat-hello-world-abc123/start");
        });
    }

    @Test
    void stopForAuthenticatedUser_shouldPostStop_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespace.json")));

        final GitHubCodespace codespace = api().stopForAuthenticatedUser(CODESPACE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/codespaces/octocat-hello-world-abc123/stop");
        });
    }

    private GitHubCodespaceApi api() {
        return new GitHubCodespaceApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodespaceApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
