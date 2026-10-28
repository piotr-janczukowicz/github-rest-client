package io.github.piotr_janczukowicz.github.api.pullrequests;

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
class GitHubPullsReviewsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;
    private static final long REVIEW_ID = 80L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPullsReviewsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnReviews_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-items.json")));

        final GitHubPage<GitHubPullRequestReview> page = api().list(OWNER, REPO, PULL_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(PullrequestsCatalogFixtures.pullRequestReviewFromListStub());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .startsWith("https://api.github.com/repos/octocat/Hello-World/pulls/1347/reviews");
        });
    }

    @Test
    void create_shouldReturnReview_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request-review.json")));
        final GitHubCreateReviewPayload payload = PullrequestsCatalogFixtures.createReviewPayload();

        final GitHubPullRequestReview review = api().create(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(PullrequestsCatalogFixtures.pullRequestReview());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void create_shouldSendPost_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request-review.json")));

        api().create(OWNER, REPO, PULL_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void get_shouldReturnReview_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request-review.json")));

        final Optional<GitHubPullRequestReview> review = api().get(OWNER, REPO, PULL_NUMBER, REVIEW_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(review).contains(PullrequestsCatalogFixtures.pullRequestReview());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPullRequestReview> review = api().get(OWNER, REPO, PULL_NUMBER, REVIEW_ID);

        assertThat(review).isEmpty();
    }

    @Test
    void update_shouldReturnReview_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request-review.json")));
        final GitHubUpdateReviewPayload payload = PullrequestsCatalogFixtures.updateReviewPayload();

        final GitHubPullRequestReview review = api().update(OWNER, REPO, PULL_NUMBER, REVIEW_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(PullrequestsCatalogFixtures.pullRequestReview());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
        });
    }

    @Test
    void update_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> api().update(OWNER, REPO, PULL_NUMBER, REVIEW_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deletePending_shouldReturnReview_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request-review.json")));

        final GitHubPullRequestReview review = api().deletePending(OWNER, REPO, PULL_NUMBER, REVIEW_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(PullrequestsCatalogFixtures.pullRequestReview());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
        });
    }

    @Test
    void listComments_shouldReturnReviewComments_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("review-comment-items.json")));

        final GitHubPage<GitHubReviewComment> page = api().listComments(OWNER, REPO, PULL_NUMBER, REVIEW_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(PullrequestsCatalogFixtures.reviewComment());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .startsWith("https://api.github.com/repos/octocat/Hello-World/pulls/1347/reviews/80/comments");
        });
    }

    @Test
    void dismiss_shouldReturnReview_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request-review.json")));
        final GitHubDismissReviewPayload payload = PullrequestsCatalogFixtures.dismissReviewPayload();

        final GitHubPullRequestReview review = api().dismiss(OWNER, REPO, PULL_NUMBER, REVIEW_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(PullrequestsCatalogFixtures.pullRequestReview());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/pulls/1347/reviews/80/dismissals");
        });
    }

    @Test
    void submit_shouldReturnReview_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request-review.json")));
        final GitHubSubmitReviewPayload payload = PullrequestsCatalogFixtures.submitReviewPayload();

        final GitHubPullRequestReview review = api().submit(OWNER, REPO, PULL_NUMBER, REVIEW_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(PullrequestsCatalogFixtures.pullRequestReview());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/pulls/1347/reviews/80/events");
        });
    }

    @Test
    void submit_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> api().submit(OWNER, REPO, PULL_NUMBER, REVIEW_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubPullsReviewsApi api() {
        return new GitHubPullsReviewsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPullsReviewsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
