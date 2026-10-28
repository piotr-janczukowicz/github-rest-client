package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
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
class GitHubCodeQualityApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int FINDING_NUMBER = 1;
    private static final String REPO_PATH = "https://api.github.com/repos/octocat/Hello-World/code-quality";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeQualityApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listFindingsForRepo_shouldSendFilterAndCursor_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("code-quality-finding.json") + "]"));

        final GitHubPage<GitHubCodeQualityFinding> page = api().listFindingsForRepo(
                        OWNER,
                        REPO,
                        CodeQualityCatalogFixtures.listFindingsForRepoFilter(),
                        GitHubCursorRequest.after("cursor-after", 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CodeQualityCatalogFixtures.codeQualityFinding());
            softly.assertThat(requestUri).hasParameter("after", "cursor-after").hasParameter("per_page", "50");
        });
    }

    @Test
    void listFindingsForRepo_shouldUseDefaultCursor_whenNoFilterOrRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listFindingsForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/code-quality/findings");
    }

    @Test
    void getFinding_shouldReturnFinding_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("code-quality-finding.json")));

        final GitHubCodeQualityFinding finding =
                api().getFinding(OWNER, REPO, FINDING_NUMBER).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(finding).isEqualTo(CodeQualityCatalogFixtures.codeQualityFinding());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/findings/1");
        });
    }

    @Test
    void getFinding_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodeQualityFinding> result = api().getFinding(OWNER, REPO, FINDING_NUMBER);

        assertThat(result).isEmpty();
    }

    @Test
    void getSetup_shouldReturnSetup_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("code-quality-setup.json")));

        final GitHubCodeQualitySetup setup = api().getSetup(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(setup).isEqualTo(CodeQualityCatalogFixtures.codeQualitySetup());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/setup");
        });
    }

    @Test
    void updateSetup_shouldReturnResponse_whenAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("code-quality-setup-update-response.json")));

        final Optional<GitHubCodeQualitySetupUpdateResponse> response =
                api().updateSetup(OWNER, REPO, CodeQualityCatalogFixtures.codeQualitySetupUpdate());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).contains(CodeQualityCatalogFixtures.codeQualitySetupUpdateResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/setup");
        });
    }

    @Test
    void updateSetup_shouldReturnEmpty_whenResponseIsOkWithoutBody() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "{}"));

        final Optional<GitHubCodeQualitySetupUpdateResponse> response =
                api().updateSetup(OWNER, REPO, CodeQualityCatalogFixtures.codeQualitySetupUpdate());

        assertThat(response).isEmpty();
    }

    private GitHubCodeQualityApi api() {
        return new GitHubCodeQualityApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodeQualityApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
