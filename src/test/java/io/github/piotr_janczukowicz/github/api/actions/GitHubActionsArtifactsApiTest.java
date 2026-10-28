package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LOCATION;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsArtifactsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final URI ARCHIVE_LOCATION = URI.create("https://example.com/archive.zip");

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsArtifactsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listArtifactsForRepo_shouldReturnArtifacts_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("artifacts.json")));

        final GitHubPage<GitHubArtifact> page = api().listArtifactsForRepo(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.artifacts().artifacts());
    }

    @Test
    void listArtifactsForRepo_shouldSendPageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("artifacts.json")));

        api().listArtifactsForRepo(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listArtifactsForRepo_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("artifacts.json")));

        api().listArtifactsForRepo(
                        OWNER,
                        REPO,
                        GitHubListArtifactsForRepoFilterBuilder.builder()
                                .name("build")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("name", "build");
    }

    @Test
    void getArtifact_shouldReturnArtifact_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("artifact.json")));

        final GitHubArtifact artifact = api().getArtifact(OWNER, REPO, 1L).orElseThrow();

        assertThat(artifact).isEqualTo(ActionsCatalogFixtures.artifact());
    }

    @Test
    void getArtifact_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubArtifact> result = api().getArtifact(OWNER, REPO, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteArtifact_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteArtifact(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/repos/octocat/Hello-World/actions/artifacts/1");
    }

    @Test
    void downloadArtifact_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), ARCHIVE_LOCATION.toString()));

        final URI location = api().downloadArtifact(OWNER, REPO, 1L, "zip");

        assertThat(location).isEqualTo(ARCHIVE_LOCATION);
    }

    @Test
    void downloadArtifact_shouldAskForZip_whenFormatOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), ARCHIVE_LOCATION.toString()));

        final URI location = api().downloadArtifact(OWNER, REPO, 1L);
        final ArgumentCaptor<HttpRequest> request = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(request.capture());

        assertThat(location).isEqualTo(ARCHIVE_LOCATION);
        assertThat(request.getValue().uri().getPath()).endsWith("/actions/artifacts/1/zip");
    }

    @Test
    void listWorkflowRunArtifacts_shouldReturnArtifacts_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("artifacts.json")));

        final GitHubPage<GitHubArtifact> page = api().listWorkflowRunArtifacts(OWNER, REPO, 1L);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.artifacts().artifacts());
    }

    @Test
    void listWorkflowRunArtifacts_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("artifacts.json")));

        api().listWorkflowRunArtifacts(OWNER, REPO, 1L, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listWorkflowRunArtifacts_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("artifacts.json")));

        api().listWorkflowRunArtifacts(
                        OWNER,
                        REPO,
                        1L,
                        GitHubListWorkflowRunArtifactsFilterBuilder.builder()
                                .name("build")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("name", "build");
    }

    private GitHubActionsArtifactsApi api() {
        return new GitHubActionsArtifactsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsArtifactsApiTest.class, fileName);
    }

    private static HttpResponse redirectResponse(final int statusCode, final String location) {
        return new HttpResponse(statusCode, HttpHeaders.of(Map.of(LOCATION, location)), HttpBody.absent());
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
