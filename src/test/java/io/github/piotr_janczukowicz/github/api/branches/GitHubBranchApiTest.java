package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergedUpstream;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubBranchApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BRANCH = "main";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBranchApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listBranches_shouldReturnBranches_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("short-branch.json") + "]"));

        final GitHubPage<GitHubShortBranch> page = api().listBranches(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(BranchesCatalogFixtures.shortBranch());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/branches?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listBranches_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("short-branch.json") + "]"));

        api().listBranches(
                        OWNER,
                        REPO,
                        GitHubListBranchesFilterBuilder.builder()
                                .protectedFilter(true)
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("protected", "true");
    }

    @Test
    void listBranches_shouldReturnBranches_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("short-branch.json") + "]"));

        final GitHubPage<GitHubShortBranch> page = api().listBranches(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(BranchesCatalogFixtures.shortBranch());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri).hasParameter("page", "2");
            softly.assertThat(requestUri).hasParameter("per_page", "10");
        });
    }

    @Test
    void getBranch_shouldReturnBranch_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("branch-with-protection.json")));

        final Optional<GitHubBranchWithProtection> branch = api().getBranch(OWNER, REPO, BRANCH);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(branch).contains(BranchesCatalogFixtures.branchWithProtectionFromStub());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/branches/main");
        });
    }

    @Test
    void getBranch_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("branch-with-protection.json")));

        api().getBranch("a/b", REPO, BRANCH);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getRawPath()).isEqualTo("/repos/a%2Fb/Hello-World/branches/main");
    }

    @Test
    void getBranch_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubBranchWithProtection> branch = api().getBranch(OWNER, REPO, BRANCH);

        assertThat(branch).isEmpty();
    }

    @Test
    void merge_shouldReturnCommit_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("commit.json")));
        final GitHubMergeBranchPayload payload = BranchesCatalogFixtures.mergeBranchPayload();

        final Optional<GitHubCommit> commit = api().merge(OWNER, REPO, payload);

        assertThat(commit).contains(RepositoriesCatalogFixtures.commitFromStub());
    }

    @Test
    void merge_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubMergeBranchPayload payload = BranchesCatalogFixtures.mergeBranchPayload();

        final Optional<GitHubCommit> commit = api().merge(OWNER, REPO, payload);

        assertThat(commit).isEmpty();
    }

    @Test
    void mergeUpstream_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("merged-upstream.json")));
        final GitHubMergeUpstreamPayload payload = BranchesCatalogFixtures.mergeUpstreamPayload();

        final GitHubMergedUpstream merged = api().mergeUpstream(OWNER, REPO, payload);

        assertThat(merged).isEqualTo(RepositoriesCatalogFixtures.mergedUpstream());
    }

    @Test
    void renameBranch_shouldReturnBranch_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("branch-with-protection.json")));

        final GitHubBranchWithProtection branch =
                api().renameBranch(OWNER, REPO, BRANCH, BranchesCatalogFixtures.renameBranchPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(branch).isEqualTo(BranchesCatalogFixtures.branchWithProtectionFromStub());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/branches/main/rename");
        });
    }

    private GitHubBranchApi api() {
        return new GitHubBranchApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubBranchApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
