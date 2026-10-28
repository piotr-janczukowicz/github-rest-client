package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueComment;
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
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubIssuesCommentsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1309;
    private static final long COMMENT_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssuesCommentsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listCommentsForRepo_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + commentJson() + "]"));
        final GitHubListCommentsForRepoFilter filter = GitHubListCommentsForRepoFilterBuilder.builder()
                .sort(GitHubListCommentsForRepoSort.UPDATED)
                .direction(GitHubDirection.DESC)
                .since(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        api().listCommentsForRepo(OWNER, REPO, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/comments");
            softly.assertThat(request.uri().getQuery()).contains("sort=updated").contains("direction=desc");
        });
    }

    @Test
    void listCommentsForRepo_shouldReturnComments_whenDefaultOverload() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + commentJson() + "]"));

        final GitHubPage<GitHubIssueComment> page = api().listCommentsForRepo(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueComment());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listCommentsForRepo_shouldReturnComments_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + commentJson() + "]"));

        final GitHubPage<GitHubIssueComment> page = api().listCommentsForRepo(OWNER, REPO, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueComment());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), commentJson()));

        final Optional<GitHubIssueComment> comment = api().getComment(OWNER, REPO, COMMENT_ID);

        assertThat(comment).contains(IssuesCatalogFixtures.issueComment());
    }

    @Test
    void getComment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        assertThat(api().getComment(OWNER, REPO, COMMENT_ID)).isEmpty();
    }

    @Test
    void updateComment_shouldPatchPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), commentJson()));
        final GitHubUpdateCommentPayload payload = GitHubUpdateCommentPayloadBuilder.builder(
                        IssuesCatalogFixtures.updateCommentPayload())
                .body("updated")
                .build();

        api().updateComment(OWNER, REPO, COMMENT_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/comments/1");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("updated");
        });
    }

    @Test
    void deleteComment_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteComment(OWNER, REPO, COMMENT_ID);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void pinComment_shouldPut_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), commentJson()));

        api().pinComment(OWNER, REPO, COMMENT_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/comments/1/pin");
        });
    }

    @Test
    void unpinComment_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().unpinComment(OWNER, REPO, COMMENT_ID);

        assertThat(captureSingleRequest().uri().getPath())
                .isEqualTo("/repos/octocat/Hello-World/issues/comments/1/pin");
    }

    @Test
    void listComments_shouldReturnComments_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + commentJson() + "]"));

        final GitHubPage<GitHubIssueComment> page = api().listComments(OWNER, REPO, ISSUE_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueComment());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listComments_shouldReturnComments_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + commentJson() + "]"));

        final GitHubPage<GitHubIssueComment> page =
                api().listComments(OWNER, REPO, ISSUE_NUMBER, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueComment());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void createComment_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), commentJson()));
        final GitHubCreateCommentPayload payload = IssuesCatalogFixtures.createCommentPayload();

        api().createComment(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/comments");
        });
    }

    private GitHubIssuesCommentsApi api() {
        return new GitHubIssuesCommentsApi(TestClients.caller(transport));
    }

    private static String commentJson() {
        return FileUtils.readString(GitHubIssuesCommentsApiTest.class, "issue-comment.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
