package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.commitsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCommitApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SHA = "6dcb09b5b57875f334f61aebed695e2e4193db5e";
    private static final String BASEHEAD = "master...topic";

    private static final String COMMITS_PATH = "/repos/octocat/Hello-World/commits";
    private static final String COMMIT_ITEM_PATH = "/repos/octocat/Hello-World/commits/" + SHA;
    private static final String COMPARE_PATH = "/repos/octocat/Hello-World/compare/" + BASEHEAD;
    private static final String BRANCHES_PATH = COMMIT_ITEM_PATH + "/branches-where-head";
    private static final String PULLS_PATH = COMMIT_ITEM_PATH + "/pulls";

    private static final JsonType<List<GitHubCommit>> COMMITS_TYPE = JsonTypes.listOf(GitHubCommit.class);
    private static final JsonType<List<GitHubBranchShort>> BRANCHES_TYPE = JsonTypes.listOf(GitHubBranchShort.class);
    private static final JsonType<List<GitHubPullRequestSimple>> PULLS_TYPE =
            JsonTypes.listOf(GitHubPullRequestSimple.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listCommits_shouldReturnCommits_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMMITS_PATH, okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json")));

        final GitHubPage<GitHubCommit> page;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            page = api.api().commit().listCommits(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedCommits());
        HTTP.verifyRequested(GET, COMMITS_PATH);
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "per_page", "100");
    }

    @Test
    void listCommits_shouldSendFilters_whenFilterProvided() throws Exception {
        HTTP.stub(GET, COMMITS_PATH, okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json")));
        final GitHubListCommitsFilter filter = GitHubListCommitsFilterBuilder.builder()
                .sha("main")
                .path("README.md")
                .author("octocat")
                .committer("octocat@github.com")
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .until(Instant.parse("2020-12-31T23:59:59Z"))
                .build();

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api().commit().listCommits(OWNER, REPO, filter, GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, COMMITS_PATH, "sha", "main");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "path", "README.md");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "author", "octocat");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "committer", "octocat@github.com");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "since", "2020-01-01T00:00:00Z");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "until", "2020-12-31T23:59:59Z");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "per_page", "100");
    }

    @Test
    void listCommits_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                COMMITS_PATH,
                okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json"))
                        .header(LINK, linkNext(HTTP.uri(COMMITS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json")));

        final GitHubPage<GitHubCommit> first;
        final Optional<GitHubPage<GitHubCommit>> second;
        final Optional<GitHubPage<GitHubCommit>> third;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            first = api.api().commit().listCommits(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedCommits());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedCommits());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, COMMITS_PATH, 2);
    }

    @Test
    void listCommits_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, COMMITS_PATH, okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json")));

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api()
                    .commit()
                    .listCommits(OWNER, REPO, GitHubListCommitsFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COMMITS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "per_page", "10");
    }

    @Test
    void getCommit_shouldReturnCommit_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMMIT_ITEM_PATH, okJson(jsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json")));

        final Optional<GitHubCommit> commit;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            commit = api.api().commit().getCommit(OWNER, REPO, SHA);
        }

        final GitHubCommit expected =
                CODEC.decode(jsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json"), GitHubCommit.class);

        assertThat(commit).contains(expected);
        HTTP.verifyRequested(GET, COMMIT_ITEM_PATH);
        HTTP.verifyNoQueryParam(GET, COMMIT_ITEM_PATH, "page");
        HTTP.verifyNoQueryParam(GET, COMMIT_ITEM_PATH, "per_page");
    }

    @Test
    void getCommit_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMMIT_ITEM_PATH, notFound());

        final Optional<GitHubCommit> commit;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            commit = api.api().commit().getCommit(OWNER, REPO, SHA);
        }

        assertThat(commit).isEmpty();
        HTTP.verifyRequested(GET, COMMIT_ITEM_PATH);
    }

    @Test
    void getCommitDiff_shouldReturnDiffText_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMMIT_ITEM_PATH, status(OK).body("diff --git a/f b/f\n".getBytes(StandardCharsets.UTF_8)));

        final Optional<String> diff;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            diff = api.api().commit().getCommitDiff(OWNER, REPO, SHA);
        }

        assertThat(diff).contains("diff --git a/f b/f\n");
        HTTP.verifyHeader(GET, COMMIT_ITEM_PATH, ACCEPT, "application/vnd.github.diff");
    }

    @Test
    void getCommitPatch_shouldReturnPatchText_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMMIT_ITEM_PATH, status(OK).body("From abc Mon Sep 17\n".getBytes(StandardCharsets.UTF_8)));

        final Optional<String> patch;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            patch = api.api().commit().getCommitPatch(OWNER, REPO, SHA);
        }

        assertThat(patch).contains("From abc Mon Sep 17\n");
        HTTP.verifyHeader(GET, COMMIT_ITEM_PATH, ACCEPT, "application/vnd.github.patch");
    }

    @Test
    void getCommitDiff_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMMIT_ITEM_PATH, notFound());

        final Optional<String> diff;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            diff = api.api().commit().getCommitDiff(OWNER, REPO, SHA);
        }

        assertThat(diff).isEmpty();
    }

    @Test
    void getCommitSha_shouldReturnSha_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMMIT_ITEM_PATH, status(OK).body(SHA.getBytes(StandardCharsets.UTF_8)));

        final Optional<String> sha;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            sha = api.api().commit().getCommitSha(OWNER, REPO, SHA);
        }

        assertThat(sha).contains(SHA);
        HTTP.verifyHeader(GET, COMMIT_ITEM_PATH, ACCEPT, "application/vnd.github.sha");
    }

    @Test
    void compareCommitsDiff_shouldReturnDiffText_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMPARE_PATH, status(OK).body("diff --git a/f b/f\n".getBytes(StandardCharsets.UTF_8)));

        final Optional<String> diff;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            diff = api.api().commit().compareCommitsDiff(OWNER, REPO, BASEHEAD);
        }

        assertThat(diff).contains("diff --git a/f b/f\n");
        HTTP.verifyHeader(GET, COMPARE_PATH, ACCEPT, "application/vnd.github.diff");
    }

    @Test
    void compareCommitsPatch_shouldReturnPatchText_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMPARE_PATH, status(OK).body("From abc Mon Sep 17\n".getBytes(StandardCharsets.UTF_8)));

        final Optional<String> patch;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            patch = api.api().commit().compareCommitsPatch(OWNER, REPO, BASEHEAD);
        }

        assertThat(patch).contains("From abc Mon Sep 17\n");
        HTTP.verifyHeader(GET, COMPARE_PATH, ACCEPT, "application/vnd.github.patch");
    }

    @Test
    void compareCommitsDiff_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMPARE_PATH, notFound());

        final Optional<String> diff;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            diff = api.api().commit().compareCommitsDiff(OWNER, REPO, BASEHEAD);
        }

        assertThat(diff).isEmpty();
    }

    @Test
    void getCommit_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, COMMIT_ITEM_PATH, okJson(jsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json")));

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api().commit().getCommit(OWNER, REPO, SHA, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COMMIT_ITEM_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COMMIT_ITEM_PATH, "per_page", "10");
    }

    @Test
    void compareCommits_shouldReturnComparison_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMPARE_PATH, okJson(jsonBytes(GitHubCommitApiIntegrationTest.class, "commit-comparison.json")));

        final Optional<GitHubCommitComparison> comparison;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            comparison = api.api().commit().compareCommits(OWNER, REPO, BASEHEAD);
        }

        final GitHubCommitComparison expected = CODEC.decode(
                jsonBytes(GitHubCommitApiIntegrationTest.class, "commit-comparison.json"),
                GitHubCommitComparison.class);

        assertThat(comparison).contains(expected);
        HTTP.verifyRequested(GET, COMPARE_PATH);
        HTTP.verifyNoQueryParam(GET, COMPARE_PATH, "page");
        HTTP.verifyNoQueryParam(GET, COMPARE_PATH, "per_page");
    }

    @Test
    void compareCommits_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMPARE_PATH, notFound());

        final Optional<GitHubCommitComparison> comparison;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            comparison = api.api().commit().compareCommits(OWNER, REPO, BASEHEAD);
        }

        assertThat(comparison).isEmpty();
        HTTP.verifyRequested(GET, COMPARE_PATH);
    }

    @Test
    void compareCommits_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, COMPARE_PATH, okJson(jsonBytes(GitHubCommitApiIntegrationTest.class, "commit-comparison.json")));

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api().commit().compareCommits(OWNER, REPO, BASEHEAD, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COMPARE_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COMPARE_PATH, "per_page", "10");
    }

    @Test
    void listBranchesForHeadCommit_shouldReturnBranches_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BRANCHES_PATH, okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "branch-short.json")));

        final List<GitHubBranchShort> branches;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            branches = api.api().commit().listBranchesForHeadCommit(OWNER, REPO, SHA);
        }

        final List<GitHubBranchShort> expected =
                CODEC.decode(listJsonBytes(GitHubCommitApiIntegrationTest.class, "branch-short.json"), BRANCHES_TYPE);

        assertThat(branches).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, BRANCHES_PATH);
    }

    @Test
    void listPullRequestsAssociatedWithCommit_shouldReturnPullRequests_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PULLS_PATH,
                okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "pull-request-simple.json")));

        final GitHubPage<GitHubPullRequestSimple> page;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            page = api.api().commit().listPullRequestsAssociatedWithCommit(OWNER, REPO, SHA);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedPulls());
        HTTP.verifyRequested(GET, PULLS_PATH);
        HTTP.verifyQueryParam(GET, PULLS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, PULLS_PATH, "per_page", "100");
    }

    @Test
    void listPullRequestsAssociatedWithCommit_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                PULLS_PATH,
                okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "pull-request-simple.json"))
                        .header(LINK, linkNext(HTTP.uri(PULLS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "pull-request-simple.json")));

        final GitHubPage<GitHubPullRequestSimple> first;
        final Optional<GitHubPage<GitHubPullRequestSimple>> second;
        final Optional<GitHubPage<GitHubPullRequestSimple>> third;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            first = api.api().commit().listPullRequestsAssociatedWithCommit(OWNER, REPO, SHA);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedPulls());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedPulls());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, PULLS_PATH, 2);
    }

    @Test
    void listPullRequestsAssociatedWithCommit_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest()
            throws Exception {
        HTTP.stub(
                GET,
                PULLS_PATH,
                okJson(listJsonBytes(GitHubCommitApiIntegrationTest.class, "pull-request-simple.json")));

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api()
                    .commit()
                    .listPullRequestsAssociatedWithCommit(OWNER, REPO, SHA, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, PULLS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, PULLS_PATH, "per_page", "10");
    }

    private static List<GitHubCommit> expectedCommits() {
        return CODEC.decode(listJsonBytes(GitHubCommitApiIntegrationTest.class, "commit.json"), COMMITS_TYPE);
    }

    private static List<GitHubPullRequestSimple> expectedPulls() {
        return CODEC.decode(
                listJsonBytes(GitHubCommitApiIntegrationTest.class, "pull-request-simple.json"), PULLS_TYPE);
    }
}
