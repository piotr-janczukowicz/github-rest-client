package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubPullRequestApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;
    private static final String MERGE_UUID = "630b9d5e-3f2a-4f7e-8b0c-2d5f9a8c1e42";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void get_shouldReturnPullRequest_whenResponseIsOk() throws Exception {
        final String path = "/repos/octocat/Hello-World/pulls/1347";
        HTTP.stub(GET, path, okJson(jsonBytes(GitHubPullRequestApiIntegrationTest.class, "pull-request.json")));

        final Optional<GitHubPullRequest> pullRequest;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullRequestApi api = new GitHubPullRequestApi(client.caller());
            pullRequest = api.get(OWNER, REPO, PULL_NUMBER);
        }

        final GitHubPullRequest expected = CODEC.decode(
                jsonBytes(GitHubPullRequestApiIntegrationTest.class, "pull-request.json"), GitHubPullRequest.class);

        assertThat(pullRequest).contains(expected);
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        final String path = "/repos/octocat/Hello-World/pulls/1347";
        HTTP.stub(GET, path, notFound());

        final Optional<GitHubPullRequest> pullRequest;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullRequestApi api = new GitHubPullRequestApi(client.caller());
            pullRequest = api.get(OWNER, REPO, PULL_NUMBER);
        }

        assertThat(pullRequest).isEmpty();
    }

    @Test
    void isMerged_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        final String path = "/repos/octocat/Hello-World/pulls/1347/merge";
        HTTP.stub(GET, path, noContent());

        final boolean merged;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullRequestApi api = new GitHubPullRequestApi(client.caller());
            merged = api.isMerged(OWNER, REPO, PULL_NUMBER);
        }

        assertThat(merged).isTrue();
    }

    @Test
    void mergeAsync_shouldReturnResult_whenResponseIsAccepted() throws Exception {
        final String path = "/repos/octocat/Hello-World/pulls/1347/merge-async";
        HTTP.stub(
                PUT,
                path,
                acceptedJson(jsonBytes(
                        GitHubPullRequestApiIntegrationTest.class, "pull-request-merge-async-result-pending.json")));

        final GitHubPullRequestMergeAsyncResult result;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullRequestApi api = new GitHubPullRequestApi(client.caller());
            result = api.mergeAsync(OWNER, REPO, PULL_NUMBER, null);
        }

        final GitHubPullRequestMergeAsyncResult expected = CODEC.decode(
                jsonBytes(GitHubPullRequestApiIntegrationTest.class, "pull-request-merge-async-result-pending.json"),
                GitHubPullRequestMergeAsyncResult.class);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getMergeAsyncResult_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        final String path = "/repos/octocat/Hello-World/pulls/1347/merge-async/" + MERGE_UUID;
        HTTP.stub(GET, path, notFound());

        final Optional<GitHubPullRequestMergeAsyncResult> result;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullRequestApi api = new GitHubPullRequestApi(client.caller());
            result = api.getMergeAsyncResult(OWNER, REPO, PULL_NUMBER, MERGE_UUID);
        }

        assertThat(result).isEmpty();
    }

    @Test
    void getDiff_shouldReturnDiffText_whenResponseIsOk() throws Exception {
        final String path = "/repos/octocat/Hello-World/pulls/1347";
        HTTP.stub(GET, path, status(OK).body("diff --git a/f b/f\n".getBytes(StandardCharsets.UTF_8)));

        final Optional<String> diff;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            diff = new GitHubPullRequestApi(client.caller()).getDiff(OWNER, REPO, PULL_NUMBER);
        }

        assertThat(diff).contains("diff --git a/f b/f\n");
        HTTP.verifyHeader(GET, path, ACCEPT, "application/vnd.github.diff");
    }

    @Test
    void getPatch_shouldReturnPatchText_whenResponseIsOk() throws Exception {
        final String path = "/repos/octocat/Hello-World/pulls/1347";
        HTTP.stub(GET, path, status(OK).body("From abc Mon Sep 17\n".getBytes(StandardCharsets.UTF_8)));

        final Optional<String> patch;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            patch = new GitHubPullRequestApi(client.caller()).getPatch(OWNER, REPO, PULL_NUMBER);
        }

        assertThat(patch).contains("From abc Mon Sep 17\n");
        HTTP.verifyHeader(GET, path, ACCEPT, "application/vnd.github.patch");
    }

    @Test
    void getDiff_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, "/repos/octocat/Hello-World/pulls/1347", notFound());

        final Optional<String> diff;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            diff = new GitHubPullRequestApi(client.caller()).getDiff(OWNER, REPO, PULL_NUMBER);
        }

        assertThat(diff).isEmpty();
    }

    @Test
    void getDiff_shouldReturnEmptyText_whenResponseIsOkWithEmptyBody() throws Exception {
        HTTP.stub(GET, "/repos/octocat/Hello-World/pulls/1347", status(OK));

        final Optional<String> diff;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            diff = new GitHubPullRequestApi(client.caller()).getDiff(OWNER, REPO, PULL_NUMBER);
        }

        assertThat(diff).contains("");
    }
}
