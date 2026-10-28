package io.github.piotr_janczukowicz.github.api.pullrequests;

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
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubPullsCommentsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;
    private static final long COMMENT_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPullsCommentsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listForRepo_shouldReturnComments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-comment-items.json")));

        final GitHubPage<GitHubPullRequestReviewComment> page = api().listForRepo(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(PullrequestsCatalogFixtures.pullRequestReviewCommentFromListStub());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listForRepo_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-comment-items.json")));
        final GitHubListRepoReviewCommentsFilter filter = GitHubListRepoReviewCommentsFilterBuilder.builder()
                .sort(GitHubListRepoReviewCommentsSort.CREATED)
                .direction(GitHubListPullRequestsDirection.ASC)
                .since(Instant.parse("2017-01-01T00:00:00Z"))
                .build();

        api().listForRepo(OWNER, REPO, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery()).contains("sort=created").contains("direction=asc");
    }

    @Test
    void get_shouldReturnComment_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-comment.json")));

        final Optional<GitHubPullRequestReviewComment> comment = api().get(OWNER, REPO, COMMENT_ID);

        assertThat(comment).contains(PullrequestsCatalogFixtures.pullRequestReviewComment());
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPullRequestReviewComment> comment = api().get(OWNER, REPO, COMMENT_ID);

        assertThat(comment).isEmpty();
    }

    @Test
    void update_shouldReturnComment_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-comment.json")));
        final GitHubUpdateReviewCommentPayload payload = PullrequestsCatalogFixtures.updateReviewCommentPayload();

        final GitHubPullRequestReviewComment comment = api().update(OWNER, REPO, COMMENT_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(PullrequestsCatalogFixtures.pullRequestReviewComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
        });
    }

    @Test
    void delete_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().delete(OWNER, REPO, COMMENT_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/pulls/comments/1");
        });
    }

    @Test
    void list_shouldReturnComments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-comment-items.json")));

        final GitHubPage<GitHubPullRequestReviewComment> page =
                api().list(OWNER, REPO, PULL_NUMBER, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(PullrequestsCatalogFixtures.pullRequestReviewCommentFromListStub());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void list_shouldReturnComments_whenDefaultPage() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-comment-items.json")));

        final GitHubPage<GitHubPullRequestReviewComment> page = api().list(OWNER, REPO, PULL_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(PullrequestsCatalogFixtures.pullRequestReviewCommentFromListStub());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void list_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-comment-items.json")));
        final GitHubListPullReviewCommentsFilter filter = GitHubListPullReviewCommentsFilterBuilder.builder()
                .sort(GitHubListPullReviewCommentsSort.UPDATED)
                .direction(GitHubListPullRequestsDirection.DESC)
                .since(Instant.parse("2017-01-01T00:00:00Z"))
                .build();

        api().list(OWNER, REPO, PULL_NUMBER, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery()).contains("sort=updated").contains("direction=desc");
    }

    @Test
    void create_shouldReturnComment_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("pull-request-review-comment-multi-line.json")));
        final GitHubCreateReviewCommentPayload payload = PullrequestsCatalogFixtures.createReviewCommentPayload();

        final GitHubPullRequestReviewComment comment = api().create(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(PullrequestsCatalogFixtures.pullRequestReviewCommentMultiLine());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void createReply_shouldReturnComment_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("pull-request-review-comment.json")));
        final GitHubCreateReviewCommentReplyPayload payload =
                PullrequestsCatalogFixtures.createReviewCommentReplyPayload();

        final GitHubPullRequestReviewComment comment = api().createReply(OWNER, REPO, PULL_NUMBER, COMMENT_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(PullrequestsCatalogFixtures.pullRequestReviewComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/pulls/1347/comments/1/replies");
        });
    }

    private GitHubPullsCommentsApi api() {
        return new GitHubPullsCommentsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPullsCommentsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
