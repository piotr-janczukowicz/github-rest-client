package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
class GitHubCodeScanningAlertDismissalRequestsApiTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ALERT_NUMBER = 1;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeScanningAlertDismissalRequestsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listDismissalRequestsForOrg_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("code-scanning-alert-dismissal-request.json") + "]"));

        final GitHubPage<GitHubCodeScanningAlertDismissalRequest> page = api().listDismissalRequestsForOrg(
                        ORG,
                        CodeScanningCatalogFixtures.listOrgDismissalRequestsFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(CodeScanningCatalogFixtures.codeScanningAlertDismissalRequest());
            softly.assertThat(requestUri)
                    .hasParameter("repository_name", "octo-repo")
                    .hasParameter("time_period", "week")
                    .hasParameter("page", "2")
                    .hasParameter("per_page", "50");
        });
    }

    @Test
    void listDismissalRequestsForOrg_shouldUseDefaultPage_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listDismissalRequestsForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/orgs/octo-org/dismissal-requests/code-scanning");
    }

    @Test
    void listDismissalRequestsForOrg_shouldThrow_whenFilterNull() {
        final GitHubCodeScanningAlertDismissalRequestsApi api = api();
        final GitHubPageRequest page = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.listDismissalRequestsForOrg(ORG, null, page));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void listDismissalRequestsForRepo_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("code-scanning-alert-dismissal-request.json") + "]"));

        final GitHubPage<GitHubCodeScanningAlertDismissalRequest> page = api().listDismissalRequestsForRepo(
                        OWNER,
                        REPO,
                        CodeScanningCatalogFixtures.listDismissalRequestsForRepoFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(CodeScanningCatalogFixtures.codeScanningAlertDismissalRequest());
            softly.assertThat(requestUri)
                    .hasParameter("time_period", "day")
                    .hasParameter("request_status", "approved")
                    .hasParameter("page", "2")
                    .hasParameter("per_page", "50");
        });
    }

    @Test
    void listDismissalRequestsForRepo_shouldUseDefaultPage_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listDismissalRequestsForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/dismissal-requests/code-scanning");
    }

    @Test
    void listDismissalRequestsForRepo_shouldThrow_whenRequestNull() {
        final GitHubCodeScanningAlertDismissalRequestsApi api = api();
        final GitHubListDismissalRequestsForRepoFilter filter =
                CodeScanningCatalogFixtures.listDismissalRequestsForRepoFilter();

        final Throwable thrown = catchThrowable(() -> api.listDismissalRequestsForRepo(OWNER, REPO, filter, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void getDismissalRequest_shouldReturnRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-alert-dismissal-request.json")));

        final Optional<GitHubCodeScanningAlertDismissalRequest> request =
                api().getDismissalRequest(OWNER, REPO, ALERT_NUMBER);

        assertThat(request).contains(CodeScanningCatalogFixtures.codeScanningAlertDismissalRequest());
    }

    @Test
    void getDismissalRequest_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubCodeScanningAlertDismissalRequest> request =
                api().getDismissalRequest(OWNER, REPO, ALERT_NUMBER);

        assertThat(request).isEmpty();
    }

    @Test
    void reviewDismissalRequest_shouldPatchPayload_whenNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().reviewDismissalRequest(
                        OWNER, REPO, ALERT_NUMBER, CodeScanningCatalogFixtures.reviewDismissalRequestPayload());
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(sent.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(sent.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/dismissal-requests/code-scanning/1");
        });
    }

    @Test
    void reviewDismissalRequest_shouldThrow_whenPayloadNull() {
        final GitHubCodeScanningAlertDismissalRequestsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.reviewDismissalRequest(OWNER, REPO, ALERT_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubCodeScanningAlertDismissalRequestsApi api() {
        return new GitHubCodeScanningAlertDismissalRequestsApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodeScanningAlertDismissalRequestsApiTest.class, fileName);
    }
}
