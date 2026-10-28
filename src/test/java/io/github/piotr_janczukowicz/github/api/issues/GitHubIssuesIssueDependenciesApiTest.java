package io.github.piotr_janczukowicz.github.api.issues;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubIssuesIssueDependenciesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1309;
    private static final long ISSUE_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesIssueDependenciesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listDependenciesBlockedBy_shouldGetPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + issueJson() + "]"));

        final GitHubPage<GitHubIssue> page = api().listDependenciesBlockedBy(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/dependencies/blocked_by");
        });
    }

    @Test
    void addBlockedByDependency_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), issueJson()));
        final GitHubAddBlockedByDependencyPayload payload = IssuesCatalogFixtures.addBlockedByDependencyPayload();

        api().addBlockedByDependency(OWNER, REPO, ISSUE_NUMBER, payload);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void removeDependencyBlockedBy_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueJson()));

        api().removeDependencyBlockedBy(OWNER, REPO, ISSUE_NUMBER, ISSUE_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/dependencies/blocked_by/1");
        });
    }

    @Test
    void listDependenciesBlocking_shouldGetPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + issueJson() + "]"));

        api().listDependenciesBlocking(OWNER, REPO, ISSUE_NUMBER);

        assertThat(captureSingleRequest().uri().getPath())
                .isEqualTo("/repos/octocat/Hello-World/issues/1309/dependencies/blocking");
    }

    private GitHubIssuesIssueDependenciesApi api() {
        return new GitHubIssuesIssueDependenciesApi(TestClients.caller(transport));
    }

    private static String issueJson() {
        return FileUtils.readString(GitHubIssueApiTest.class, "issue.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
