package io.github.piotr_janczukowicz.github.api.dependabot;

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
class GitHubDependabotAlertDismissalRequestsApiTest {

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
        final Throwable thrown = catchThrowable(() -> new GitHubDependabotAlertDismissalRequestsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listDismissalRequestsForOrg_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-dismissal-request.json") + "]"));

        final GitHubPage<GitHubDependabotDismissalRequest> page = api().listDismissalRequestsForOrg(
                        ORG,
                        DependabotCatalogFixtures.listDismissalRequestsForOrgFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DependabotCatalogFixtures.dependabotDismissalRequest());
            softly.assertThat(requestUri)
                    .hasParameter("repository_name", "octo-repo")
                    .hasParameter("time_period", "day")
                    .hasParameter("page", "2")
                    .hasParameter("per_page", "50");
        });
    }

    @Test
    void listDismissalRequestsForOrg_shouldUseDefaultPage_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listDismissalRequestsForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/orgs/octo-org/dismissal-requests/dependabot");
    }

    @Test
    void listDismissalRequestsForOrg_shouldThrow_whenFilterNull() {
        final GitHubDependabotAlertDismissalRequestsApi api = api();
        final GitHubPageRequest page = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.listDismissalRequestsForOrg(ORG, null, page));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void listDismissalRequestsForRepo_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependabot-dismissal-request.json") + "]"));

        final GitHubPage<GitHubDependabotDismissalRequest> page = api().listDismissalRequestsForRepo(
                        OWNER,
                        REPO,
                        DependabotCatalogFixtures.listDismissalRequestsForRepoFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DependabotCatalogFixtures.dependabotDismissalRequest());
            softly.assertThat(requestUri)
                    .hasParameter("time_period", "month")
                    .hasParameter("request_status", "open")
                    .hasParameter("page", "2")
                    .hasParameter("per_page", "50");
        });
    }

    @Test
    void listDismissalRequestsForRepo_shouldUseDefaultPage_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listDismissalRequestsForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/dismissal-requests/dependabot");
    }

    @Test
    void listDismissalRequestsForRepo_shouldThrow_whenRequestNull() {
        final GitHubDependabotAlertDismissalRequestsApi api = api();
        final GitHubListDismissalRequestsForRepoFilter filter =
                DependabotCatalogFixtures.listDismissalRequestsForRepoFilter();

        final Throwable thrown = catchThrowable(() -> api.listDismissalRequestsForRepo(OWNER, REPO, filter, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void getDismissalRequest_shouldReturnRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-dismissal-request.json")));

        final Optional<GitHubDependabotDismissalRequest> request = api().getDismissalRequest(OWNER, REPO, ALERT_NUMBER);

        assertThat(request).contains(DependabotCatalogFixtures.dependabotDismissalRequest());
    }

    @Test
    void getDismissalRequest_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubDependabotDismissalRequest> request = api().getDismissalRequest(OWNER, REPO, ALERT_NUMBER);

        assertThat(request).isEmpty();
    }

    @Test
    void createDismissalRequest_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("dependabot-dismissal-request.json")));

        final GitHubDependabotDismissalRequest request = api().createDismissalRequest(
                        OWNER, REPO, ALERT_NUMBER, DependabotCatalogFixtures.createDismissalRequestPayload());
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request).isEqualTo(DependabotCatalogFixtures.dependabotDismissalRequest());
            softly.assertThat(sent.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(sent.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/dismissal-requests/dependabot/1");
        });
    }

    @Test
    void createDismissalRequest_shouldThrow_whenPayloadNull() {
        final GitHubDependabotAlertDismissalRequestsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createDismissalRequest(OWNER, REPO, ALERT_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void reviewDismissalRequest_shouldPatchPayload_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("review-dismissal-request-result.json")));

        final GitHubReviewDismissalRequestResult result = api().reviewDismissalRequest(
                        OWNER, REPO, ALERT_NUMBER, DependabotCatalogFixtures.reviewDismissalRequestPayload());
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(DependabotCatalogFixtures.reviewDismissalRequestResult());
            softly.assertThat(sent.method()).isEqualTo(HttpMethod.PATCH);
        });
    }

    @Test
    void reviewDismissalRequest_shouldThrow_whenPayloadNull() {
        final GitHubDependabotAlertDismissalRequestsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.reviewDismissalRequest(OWNER, REPO, ALERT_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void cancelDismissalRequest_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().cancelDismissalRequest(OWNER, REPO, ALERT_NUMBER);
        final HttpRequest sent = captureSingleRequest();

        assertThat(sent.method()).isEqualTo(HttpMethod.DELETE);
    }

    private GitHubDependabotAlertDismissalRequestsApi api() {
        return new GitHubDependabotAlertDismissalRequestsApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDependabotAlertDismissalRequestsApiTest.class, fileName);
    }
}
