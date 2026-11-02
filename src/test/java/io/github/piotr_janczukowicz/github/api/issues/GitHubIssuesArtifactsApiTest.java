package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubIssuesArtifactsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1347;
    private static final int ARTIFACT_NUMBER = 3;
    private static final String ARTIFACTS_URI =
            "https://api.github.com/repos/octocat/Hello-World/issues/1347/artifacts";
    private static final String ARTIFACT_URI = ARTIFACTS_URI + "/3";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesArtifactsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listIssueArtifacts_shouldReturnArtifacts_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("issue-artifact.json") + "]"));

        final GitHubPage<GitHubIssueArtifact> page = api().listIssueArtifacts(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueArtifact());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).startsWith(ARTIFACTS_URI + "?page=1");
        });
    }

    @Test
    void listIssueArtifacts_shouldSendTypeQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("issue-artifact.json") + "]"));

        api().listIssueArtifacts(
                        OWNER,
                        REPO,
                        ISSUE_NUMBER,
                        IssuesCatalogFixtures.listIssueArtifactsFilter(),
                        GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery()).contains("type=generic");
    }

    @Test
    void createIssueArtifact_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("issue-artifact.json")));
        final GitHubCreateIssueArtifactPayload payload = IssuesCatalogFixtures.createIssueArtifactPayload();

        final GitHubIssueArtifact artifact = api().createIssueArtifact(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(artifact).isEqualTo(IssuesCatalogFixtures.issueArtifact());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(ARTIFACTS_URI);
        });
    }

    @Test
    void createIssueArtifact_shouldThrow_whenPayloadNull() {
        final GitHubIssuesArtifactsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createIssueArtifact(OWNER, REPO, ISSUE_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getIssueArtifact_shouldReturnArtifact_whenResponseIsTheArtifact() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue-artifact.json")));

        final Optional<GitHubGetIssueArtifactResponse> artifact =
                api().getIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(artifact).contains(IssuesCatalogFixtures.issueArtifact());
            softly.assertThat(request.uri()).hasToString(ARTIFACT_URI);
        });
    }

    @Test
    void getIssueArtifact_shouldReturnVersions_whenResponseHasVersionHistory() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("get-issue-artifact-response-with-versions.json")));

        final Optional<GitHubGetIssueArtifactResponse> artifact =
                api().getIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER);

        assertThat(artifact).contains(IssuesCatalogFixtures.getIssueArtifactResponseWithVersions());
    }

    @Test
    void getIssueArtifact_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGetIssueArtifactResponse> artifact =
                api().getIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER);

        assertThat(artifact).isEmpty();
    }

    @Test
    void updateIssueArtifact_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue-artifact.json")));
        final GitHubUpdateIssueArtifactPayload payload = IssuesCatalogFixtures.updateIssueArtifactPayload();

        final GitHubIssueArtifact artifact =
                api().updateIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(artifact).isEqualTo(IssuesCatalogFixtures.issueArtifact());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(ARTIFACT_URI);
        });
    }

    @Test
    void updateIssueArtifact_shouldThrow_whenPayloadNull() {
        final GitHubIssuesArtifactsApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.updateIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteIssueArtifact_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(ARTIFACT_URI);
        });
    }

    private GitHubIssuesArtifactsApi api() {
        return new GitHubIssuesArtifactsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubIssuesArtifactsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
