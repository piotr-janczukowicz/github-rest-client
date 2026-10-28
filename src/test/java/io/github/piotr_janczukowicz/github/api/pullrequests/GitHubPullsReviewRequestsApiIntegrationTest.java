package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubPullsReviewRequestsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;

    private static final String REVIEW_REQUESTS_PATH = "/repos/octocat/Hello-World/pulls/1347/requested_reviewers";
    private static final String REREQUEST_PATH = REVIEW_REQUESTS_PATH + "/rerequest";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnReviewRequest_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REVIEW_REQUESTS_PATH,
                okJson(jsonBytes(
                        GitHubPullsReviewRequestsApiIntegrationTest.class, "pull-request-review-request.json")));

        final GitHubPullRequestReviewRequest reviewRequest;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewRequestsApi api = new GitHubPullsReviewRequestsApi(client.caller());
            reviewRequest = api.list(OWNER, REPO, PULL_NUMBER);
        }

        final GitHubPullRequestReviewRequest expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewRequestsApiIntegrationTest.class, "pull-request-review-request.json"),
                GitHubPullRequestReviewRequest.class);

        assertSoftly(softly -> {
            softly.assertThat(reviewRequest).isEqualTo(expected);
            HTTP.verifyRequested(GET, REVIEW_REQUESTS_PATH);
        });
    }

    @Test
    void request_shouldReturnPullRequest_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REVIEW_REQUESTS_PATH,
                createdJson(jsonBytes(GitHubPullsReviewRequestsApiIntegrationTest.class, "pull-request-simple.json")));
        final GitHubRequestReviewersPayload payload = PullrequestsCatalogFixtures.requestReviewersPayload();

        final GitHubPullRequestSimple pullRequest;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewRequestsApi api = new GitHubPullsReviewRequestsApi(client.caller());
            pullRequest = api.request(OWNER, REPO, PULL_NUMBER, payload);
        }

        final GitHubPullRequestSimple expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewRequestsApiIntegrationTest.class, "pull-request-simple.json"),
                GitHubPullRequestSimple.class);

        assertSoftly(softly -> {
            softly.assertThat(pullRequest).isEqualTo(expected);
            HTTP.verifyRequested(POST, REVIEW_REQUESTS_PATH);
            HTTP.verifyBody(POST, REVIEW_REQUESTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void rerequest_shouldReturnPullRequest_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REREQUEST_PATH,
                createdJson(jsonBytes(GitHubPullsReviewRequestsApiIntegrationTest.class, "pull-request-simple.json")));
        final GitHubRerequestReviewersPayload payload = PullrequestsCatalogFixtures.rerequestReviewersPayload();

        final GitHubPullRequestSimple pullRequest;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewRequestsApi api = new GitHubPullsReviewRequestsApi(client.caller());
            pullRequest = api.rerequest(OWNER, REPO, PULL_NUMBER, payload);
        }

        final GitHubPullRequestSimple expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewRequestsApiIntegrationTest.class, "pull-request-simple.json"),
                GitHubPullRequestSimple.class);

        assertSoftly(softly -> {
            softly.assertThat(pullRequest).isEqualTo(expected);
            HTTP.verifyRequested(POST, REREQUEST_PATH);
            HTTP.verifyBody(POST, REREQUEST_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void remove_shouldReturnPullRequest_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                REVIEW_REQUESTS_PATH,
                okJson(jsonBytes(GitHubPullsReviewRequestsApiIntegrationTest.class, "pull-request-simple.json")));
        final GitHubRemoveReviewersPayload payload = PullrequestsCatalogFixtures.removeReviewersPayload();

        final GitHubPullRequestSimple pullRequest;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewRequestsApi api = new GitHubPullsReviewRequestsApi(client.caller());
            pullRequest = api.remove(OWNER, REPO, PULL_NUMBER, payload);
        }

        final GitHubPullRequestSimple expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewRequestsApiIntegrationTest.class, "pull-request-simple.json"),
                GitHubPullRequestSimple.class);

        assertSoftly(softly -> {
            softly.assertThat(pullRequest).isEqualTo(expected);
            HTTP.verifyRequested(DELETE, REVIEW_REQUESTS_PATH);
            HTTP.verifyBody(DELETE, REVIEW_REQUESTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }
}
