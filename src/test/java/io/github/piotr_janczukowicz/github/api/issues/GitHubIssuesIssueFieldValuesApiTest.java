package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.search.GitHubIssueFieldValue;
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
class GitHubIssuesIssueFieldValuesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1309;
    private static final long FIELD_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesIssueFieldValuesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listIssueFieldValuesForIssue_shouldGetPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + fieldJson() + "]"));

        final GitHubPage<GitHubIssueFieldValue> page = api().listIssueFieldValuesForIssue(OWNER, REPO, ISSUE_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueFieldValue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void addIssueFieldValues_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + fieldJson() + "]"));
        final GitHubAddIssueFieldValuesPayload payload = IssuesCatalogFixtures.addIssueFieldValuesPayload();

        api().addIssueFieldValues(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/issue-field-values");
        });
    }

    @Test
    void setIssueFieldValues_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + fieldJson() + "]"));
        final GitHubSetIssueFieldValuesPayload payload = IssuesCatalogFixtures.setIssueFieldValuesPayload();

        api().setIssueFieldValues(OWNER, REPO, ISSUE_NUMBER, payload);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void deleteIssueFieldValue_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteIssueFieldValue(OWNER, REPO, ISSUE_NUMBER, FIELD_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/issue-field-values/1");
        });
    }

    private GitHubIssuesIssueFieldValuesApi api() {
        return new GitHubIssuesIssueFieldValuesApi(TestClients.caller(transport));
    }

    private static String fieldJson() {
        return FileUtils.readString(GitHubIssuesIssueFieldValuesApiTest.class, "issue-field-value.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
