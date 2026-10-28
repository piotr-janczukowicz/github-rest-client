package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubDiffEntry;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimple;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubPullRequestApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;
    private static final String MERGE_UUID = "630b9d5e-3f2a-4f7e-8b0c-2d5f9a8c1e42";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPullRequestApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnPullRequests_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-simple-items.json")));

        final GitHubPage<GitHubPullRequestSimple> page = api().list(OWNER, REPO);

        assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.pullRequestSimpleSparse());
    }

    @Test
    void list_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-simple-items.json")));
        final GitHubListPullRequestsFilter filter = GitHubListPullRequestsFilterBuilder.builder()
                .state(GitHubListPullRequestsState.OPEN)
                .head("octocat:new-topic")
                .base("master")
                .sort(GitHubListPullRequestsSort.CREATED)
                .direction(GitHubListPullRequestsDirection.DESC)
                .build();

        api().list(OWNER, REPO, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery())
                .contains("state=open")
                .contains("sort=created")
                .contains("direction=desc");
    }

    @Test
    void create_shouldReturnPullRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request.json")));
        final GitHubCreatePullRequestPayload payload = PullrequestsCatalogFixtures.createPullRequestPayload();

        final GitHubPullRequest pullRequest = api().create(OWNER, REPO, payload);

        assertThat(pullRequest).isEqualTo(PullrequestsCatalogFixtures.pullRequest());
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPullRequest> pullRequest = api().get(OWNER, REPO, PULL_NUMBER);

        assertThat(pullRequest).isEmpty();
    }

    @Test
    void update_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request.json")));

        api().update(OWNER, REPO, PULL_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void listCommits_shouldReturnCommits_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("commit.json") + "]"));

        final GitHubPage<GitHubCommit> page = api().listCommits(OWNER, REPO, PULL_NUMBER, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.commitFromStub());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listCommits_shouldReturnCommits_whenDefaultPage() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("commit.json") + "]"));

        final GitHubPage<GitHubCommit> page = api().listCommits(OWNER, REPO, PULL_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.commitFromStub());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listFiles_shouldReturnDiffEntries_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("diff-entry.json") + "]"));

        final GitHubPage<GitHubDiffEntry> page = api().listFiles(OWNER, REPO, PULL_NUMBER, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(PullrequestsCatalogFixtures.diffEntryFromStub());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listFiles_shouldReturnDiffEntries_whenDefaultPage() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("diff-entry.json") + "]"));

        final GitHubPage<GitHubDiffEntry> page = api().listFiles(OWNER, REPO, PULL_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(PullrequestsCatalogFixtures.diffEntryFromStub());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void isMerged_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean merged = api().isMerged(OWNER, REPO, PULL_NUMBER);

        assertThat(merged).isTrue();
    }

    @Test
    void isMerged_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean merged = api().isMerged(OWNER, REPO, PULL_NUMBER);

        assertThat(merged).isFalse();
    }

    @Test
    void merge_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-merge-result.json")));

        final GitHubPullRequestMergeResult result = api().merge(OWNER, REPO, PULL_NUMBER, null);

        assertThat(result).isEqualTo(PullrequestsCatalogFixtures.pullRequestMergeResult());
    }

    @Test
    void merge_shouldPutBody_whenPayloadPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-merge-result.json")));
        final GitHubMergePullRequestPayload payload = PullrequestsCatalogFixtures.mergePullRequestPayload();

        api().merge(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void mergeAsync_shouldReturnResult_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("pull-request-merge-async-result-pending.json")));

        final GitHubPullRequestMergeAsyncResult result = api().mergeAsync(OWNER, REPO, PULL_NUMBER, null);

        assertThat(result).isEqualTo(PullrequestsCatalogFixtures.pullRequestMergeAsyncResultPending());
    }

    @Test
    void mergeAsync_shouldPutBody_whenPayloadPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("pull-request-merge-async-result-pending.json")));
        final GitHubMergePullRequestAsyncPayload payload = PullrequestsCatalogFixtures.mergePullRequestAsyncPayload();

        api().mergeAsync(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void mergeAsync_shouldThrow_whenResponseIsBadRequest() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.BAD_REQUEST.code(), json("pull-request-merge-async-result-pending.json")));

        final Throwable thrown = catchThrowable(() -> api().mergeAsync(OWNER, REPO, PULL_NUMBER, null));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void getMergeAsyncResult_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPullRequestMergeAsyncResult> result =
                api().getMergeAsyncResult(OWNER, REPO, PULL_NUMBER, MERGE_UUID);

        assertThat(result).isEmpty();
    }

    @Test
    void updateBranch_shouldReturnResult_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("pull-request-branch-update-result.json")));

        final GitHubPullRequestBranchUpdateResult result = api().updateBranch(OWNER, REPO, PULL_NUMBER, null);

        assertThat(result).isEqualTo(PullrequestsCatalogFixtures.pullRequestBranchUpdateResult());
    }

    @Test
    void update_shouldPatchWithBody_whenPayloadPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pull-request.json")));
        final GitHubUpdatePullRequestPayload payload = PullrequestsCatalogFixtures.updatePullRequestPayload();

        api().update(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void updateBranch_shouldPutBody_whenPayloadPresent() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("pull-request-branch-update-result.json")));
        final GitHubUpdatePullRequestBranchPayload payload =
                PullrequestsCatalogFixtures.updatePullRequestBranchPayload();

        api().updateBranch(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    private GitHubPullRequestApi api() {
        return new GitHubPullRequestApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPullRequestApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
