package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
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
class GitHubPullsReviewRequestsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPullsReviewRequestsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnReviewRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-review-request.json")));

        final GitHubPullRequestReviewRequest reviewRequest = api().list(OWNER, REPO, PULL_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(reviewRequest).isEqualTo(PullrequestsCatalogFixtures.pullRequestReviewRequest());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/pulls/1347/requested_reviewers");
        });
    }

    @Test
    void request_shouldReturnPullRequest_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("pull-request-simple.json")));
        final GitHubRequestReviewersPayload payload = PullrequestsCatalogFixtures.requestReviewersPayload();

        final GitHubPullRequestSimple pullRequest = api().request(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(pullRequest).isEqualTo(RepositoriesCatalogFixtures.pullRequestSimpleSparse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void request_shouldSendPost_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("pull-request-simple.json")));

        api().request(OWNER, REPO, PULL_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void remove_shouldReturnPullRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request-simple.json")));
        final GitHubRemoveReviewersPayload payload = PullrequestsCatalogFixtures.removeReviewersPayload();

        final GitHubPullRequestSimple pullRequest = api().remove(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(pullRequest).isEqualTo(RepositoriesCatalogFixtures.pullRequestSimpleSparse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
        });
    }

    @Test
    void remove_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> api().remove(OWNER, REPO, PULL_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void rerequest_shouldPostToRerequestPath_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("pull-request-simple.json")));
        final GitHubRerequestReviewersPayload payload = PullrequestsCatalogFixtures.rerequestReviewersPayload();

        final GitHubPullRequestSimple pullRequest = api().rerequest(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(pullRequest).isEqualTo(RepositoriesCatalogFixtures.pullRequestSimpleSparse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/pulls/1347/requested_reviewers/rerequest");
        });
    }

    @Test
    void rerequest_shouldSendPost_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("pull-request-simple.json")));

        api().rerequest(OWNER, REPO, PULL_NUMBER, null);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.POST);
    }

    private GitHubPullsReviewRequestsApi api() {
        return new GitHubPullsReviewRequestsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPullsReviewRequestsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
