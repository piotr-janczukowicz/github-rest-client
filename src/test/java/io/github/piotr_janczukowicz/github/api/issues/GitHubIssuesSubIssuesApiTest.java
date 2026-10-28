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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubIssuesSubIssuesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1309;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesSubIssuesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getParent_shouldReturnIssue_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueJson()));

        final Optional<GitHubIssue> parent = api().getParent(OWNER, REPO, ISSUE_NUMBER);

        assertThat(parent).contains(IssuesCatalogFixtures.issue());
    }

    @Test
    void removeSubIssue_shouldDeletePayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueJson()));
        final GitHubRemoveSubIssuePayload payload = IssuesCatalogFixtures.removeSubIssuePayload();

        api().removeSubIssue(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/sub_issue");
        });
    }

    @Test
    void listSubIssues_shouldGetPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + issueJson() + "]"));

        final GitHubPage<GitHubIssue> page = api().listSubIssues(OWNER, REPO, ISSUE_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void addSubIssue_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), issueJson()));
        final GitHubAddSubIssuePayload payload = IssuesCatalogFixtures.addSubIssuePayload();

        api().addSubIssue(OWNER, REPO, ISSUE_NUMBER, payload);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void reprioritizeSubIssue_shouldPatchPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueJson()));
        final GitHubReprioritizeSubIssuePayload payload = IssuesCatalogFixtures.reprioritizeSubIssuePayload();

        api().reprioritizeSubIssue(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/sub_issues/priority");
        });
    }

    private GitHubIssuesSubIssuesApi api() {
        return new GitHubIssuesSubIssuesApi(TestClients.caller(transport));
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
