package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCommitApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SHA = "6dcb09b5b57875f334f61aebed695e2e4193db5e";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommitApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listCommits_shouldReturnCommits_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("commit.json") + "]"));

        final GitHubPage<GitHubCommit> page = api().listCommits(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.commitFromStub());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/commits?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listCommits_shouldSendFilters_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));
        final GitHubListCommitsFilter filter = GitHubListCommitsFilterBuilder.builder()
                .sha("main")
                .path("README.md")
                .author("octocat")
                .committer("octocat@github.com")
                .since(Instant.parse("2020-01-01T00:00:00Z"))
                .until(Instant.parse("2020-12-31T23:59:59Z"))
                .build();

        api().listCommits(OWNER, REPO, filter, GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString())
                .contains("sha=main")
                .contains("path=README.md")
                .contains("author=octocat")
                .contains("committer=octocat%40github.com")
                .contains("since=2020-01-01T00")
                .contains("until=2020-12-31T23")
                .contains("page=1")
                .contains(TestClients.perPageQuery());
    }

    @Test
    void getCommit_shouldReturnCommit_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("commit.json")));

        final Optional<GitHubCommit> commit = api().getCommit(OWNER, REPO, SHA);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(commit).contains(RepositoriesCatalogFixtures.commitFromStub());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/commits/" + SHA);
        });
    }

    @Test
    void getCommit_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("commit.json")));

        api().getCommit("a/b", REPO, SHA);

        assertThat(captureSingleRequest().uri().getRawPath()).isEqualTo("/repos/a%2Fb/Hello-World/commits/" + SHA);
    }

    @Test
    void getCommit_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubCommit> commit = api().getCommit(OWNER, REPO, SHA);

        assertThat(commit).isEmpty();
    }

    @Test
    void getCommit_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("commit.json")));

        api().getCommit(OWNER, REPO, SHA, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString())
                .contains("/repos/octocat/Hello-World/commits/" + SHA)
                .contains("page=2")
                .contains("per_page=10");
    }

    @Test
    void compareCommits_shouldReturnComparison_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("commit-comparison.json")));

        final Optional<GitHubCommitComparison> comparison = api().compareCommits(OWNER, REPO, "master...topic");
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(comparison).contains(CommitsCatalogFixtures.commitComparisonFromStub());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/compare/master...topic");
        });
    }

    @Test
    void getCommitSha_shouldAskForShaMediaType_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), SHA));

        final Optional<String> sha = api().getCommitSha(OWNER, REPO, SHA);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(sha).contains(SHA);
            softly.assertThat(request.headers().get("Accept")).contains("application/vnd.github.sha");
        });
    }

    @Test
    void compareCommitsPatch_shouldAskForPatchMediaType_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "From abc"));

        final Optional<String> patch = api().compareCommitsPatch(OWNER, REPO, "master...topic");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(patch).contains("From abc");
            softly.assertThat(request.headers().get("Accept")).contains("application/vnd.github.patch");
        });
    }

    @Test
    void listBranchesForHeadCommit_shouldReturnBranches_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("branch-short.json") + "]"));

        final List<GitHubBranchShort> branches = api().listBranchesForHeadCommit(OWNER, REPO, SHA);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(branches).containsExactly(CommitsCatalogFixtures.branchShort());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/repos/octocat/Hello-World/commits/" + SHA + "/branches-where-head");
        });
    }

    @Test
    void listPullRequestsAssociatedWithCommit_shouldReturnPullRequests_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("pull-request-simple.json") + "]"));

        final GitHubPage<GitHubPullRequestSimple> page = api().listPullRequestsAssociatedWithCommit(OWNER, REPO, SHA);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.pullRequestSimpleSparse());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/commits/" + SHA + "/pulls?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    private GitHubCommitApi api() {
        return new GitHubCommitApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCommitApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
