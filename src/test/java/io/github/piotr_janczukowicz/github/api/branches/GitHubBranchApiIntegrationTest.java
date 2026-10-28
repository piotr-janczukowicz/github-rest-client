package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.branchesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergedUpstream;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubBranchApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BRANCH = "main";

    private static final String BRANCHES_PATH = "/repos/octocat/Hello-World/branches";
    private static final String BRANCH_PATH = "/repos/octocat/Hello-World/branches/main";
    private static final String RENAME_PATH = "/repos/octocat/Hello-World/branches/main/rename";
    private static final String MERGES_PATH = "/repos/octocat/Hello-World/merges";
    private static final String MERGE_UPSTREAM_PATH = "/repos/octocat/Hello-World/merge-upstream";

    private static final JsonType<List<GitHubShortBranch>> BRANCHES_TYPE = JsonTypes.listOf(GitHubShortBranch.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listBranches_shouldReturnBranches_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BRANCHES_PATH, okJson(listJsonBytes(GitHubBranchApiIntegrationTest.class, "short-branch.json")));

        final GitHubPage<GitHubShortBranch> page;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            page = api.api().branch().listBranches(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedBranches());
        HTTP.verifyRequested(GET, BRANCHES_PATH);
        HTTP.verifyQueryParam(GET, BRANCHES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, BRANCHES_PATH, "per_page", "100");
    }

    @Test
    void listBranches_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(GET, BRANCHES_PATH, okJson(listJsonBytes(GitHubBranchApiIntegrationTest.class, "short-branch.json")));

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api()
                    .branch()
                    .listBranches(
                            OWNER,
                            REPO,
                            GitHubListBranchesFilterBuilder.builder()
                                    .protectedFilter(true)
                                    .build());
        }

        HTTP.verifyQueryParam(GET, BRANCHES_PATH, "protected", "true");
        HTTP.verifyQueryParam(GET, BRANCHES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, BRANCHES_PATH, "per_page", "100");
    }

    @Test
    void listBranches_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                BRANCHES_PATH,
                okJson(listJsonBytes(GitHubBranchApiIntegrationTest.class, "short-branch.json"))
                        .header(LINK, linkNext(HTTP.uri(BRANCHES_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubBranchApiIntegrationTest.class, "short-branch.json")));

        final GitHubPage<GitHubShortBranch> first;
        final Optional<GitHubPage<GitHubShortBranch>> second;
        final Optional<GitHubPage<GitHubShortBranch>> third;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            first = api.api().branch().listBranches(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedBranches());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedBranches());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, BRANCHES_PATH, 2);
    }

    @Test
    void listBranches_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, BRANCHES_PATH, okJson(listJsonBytes(GitHubBranchApiIntegrationTest.class, "short-branch.json")));

        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            api.api()
                    .branch()
                    .listBranches(OWNER, REPO, GitHubListBranchesFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, BRANCHES_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, BRANCHES_PATH, "per_page", "10");
    }

    @Test
    void getBranch_shouldReturnBranch_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                BRANCH_PATH,
                okJson(jsonBytes(GitHubBranchApiIntegrationTest.class, "branch-with-protection.json")));

        final Optional<GitHubBranchWithProtection> branch;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            branch = api.api().branch().getBranch(OWNER, REPO, BRANCH);
        }

        assertThat(branch).contains(expectedBranchWithProtection());
        HTTP.verifyRequested(GET, BRANCH_PATH);
    }

    @Test
    void getBranch_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, BRANCH_PATH, notFound());

        final Optional<GitHubBranchWithProtection> branch;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            branch = api.api().branch().getBranch(OWNER, REPO, BRANCH);
        }

        assertThat(branch).isEmpty();
        HTTP.verifyRequested(GET, BRANCH_PATH);
    }

    @Test
    void merge_shouldReturnCommit_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, MERGES_PATH, createdJson(jsonBytes(GitHubBranchApiIntegrationTest.class, "commit.json")));
        final GitHubMergeBranchPayload payload = BranchesCatalogFixtures.mergeBranchPayload();

        final Optional<GitHubCommit> commit;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            commit = api.api().branch().merge(OWNER, REPO, payload);
        }

        assertThat(commit).contains(expectedCommit());
        HTTP.verifyRequested(POST, MERGES_PATH);
        HTTP.verifyHeader(POST, MERGES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, MERGES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void merge_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, MERGES_PATH, noContent());
        final GitHubMergeBranchPayload payload = BranchesCatalogFixtures.mergeBranchPayload();

        final Optional<GitHubCommit> commit;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            commit = api.api().branch().merge(OWNER, REPO, payload);
        }

        assertThat(commit).isEmpty();
        HTTP.verifyRequested(POST, MERGES_PATH);
        HTTP.verifyBody(POST, MERGES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void mergeUpstream_shouldReturnMergedUpstream_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                MERGE_UPSTREAM_PATH,
                okJson(jsonBytes(GitHubBranchApiIntegrationTest.class, "merged-upstream.json")));
        final GitHubMergeUpstreamPayload payload = BranchesCatalogFixtures.mergeUpstreamPayload();

        final GitHubMergedUpstream merged;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            merged = api.api().branch().mergeUpstream(OWNER, REPO, payload);
        }

        assertThat(merged).isEqualTo(expectedMergedUpstream());
        HTTP.verifyRequested(POST, MERGE_UPSTREAM_PATH);
        HTTP.verifyHeader(POST, MERGE_UPSTREAM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, MERGE_UPSTREAM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void renameBranch_shouldReturnBranch_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                RENAME_PATH,
                createdJson(jsonBytes(GitHubBranchApiIntegrationTest.class, "branch-with-protection.json")));
        final GitHubRenameBranchPayload payload = BranchesCatalogFixtures.renameBranchPayload();

        final GitHubBranchWithProtection branch;
        try (final GitHubApiWireMockSupport.BranchesApiClient api = branchesApi(HTTP)) {
            branch = api.api().branch().renameBranch(OWNER, REPO, BRANCH, payload);
        }

        assertThat(branch).isEqualTo(expectedBranchWithProtection());
        HTTP.verifyRequested(POST, RENAME_PATH);
        HTTP.verifyHeader(POST, RENAME_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, RENAME_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    private static List<GitHubShortBranch> expectedBranches() {
        return CODEC.decode(listJsonBytes(GitHubBranchApiIntegrationTest.class, "short-branch.json"), BRANCHES_TYPE);
    }

    private static GitHubBranchWithProtection expectedBranchWithProtection() {
        return CODEC.decode(
                jsonBytes(GitHubBranchApiIntegrationTest.class, "branch-with-protection.json"),
                GitHubBranchWithProtection.class);
    }

    private static GitHubCommit expectedCommit() {
        return CODEC.decode(jsonBytes(GitHubBranchApiIntegrationTest.class, "commit.json"), GitHubCommit.class);
    }

    private static GitHubMergedUpstream expectedMergedUpstream() {
        return CODEC.decode(
                jsonBytes(GitHubBranchApiIntegrationTest.class, "merged-upstream.json"), GitHubMergedUpstream.class);
    }
}
