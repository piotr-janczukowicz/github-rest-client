package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubIssueApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ORG = "github";
    private static final int ISSUE_NUMBER = 1309;
    private static final long SUGGESTION_ID = 12L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnIssues_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().list();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void list_shouldReturnIssues_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().list(GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void list_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));
        final GitHubListFilter filter = GitHubListFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.ASSIGNED)
                .state(GitHubListIssuesState.OPEN)
                .sort(GitHubListIssuesSort.CREATED)
                .direction(GitHubDirection.DESC)
                .build();

        api().list(filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/issues");
            softly.assertThat(request.uri().getQuery())
                    .contains("filter=assigned")
                    .contains("state=open")
                    .contains("sort=created")
                    .contains("direction=desc");
        });
    }

    @Test
    void listForOrg_shouldReturnIssues_whenOrgProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().listForOrg(ORG);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listForOrg_shouldReturnIssues_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().listForOrg(ORG, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listForOrg_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));
        final GitHubListForOrgFilter filter = GitHubListForOrgFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.CREATED)
                .state(GitHubListIssuesState.OPEN)
                .type("bug")
                .sort(GitHubListIssuesSort.UPDATED)
                .direction(GitHubDirection.ASC)
                .build();

        api().listForOrg(ORG, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/github/issues");
            softly.assertThat(request.uri().getQuery())
                    .contains("filter=created")
                    .contains("state=open")
                    .contains("type=bug")
                    .contains("sort=updated")
                    .contains("direction=asc");
        });
    }

    @Test
    void listForRepo_shouldReturnIssues_whenOwnerAndRepoProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().listForRepo(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listForRepo_shouldReturnIssues_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().listForRepo(OWNER, REPO, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listForRepo_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));
        final GitHubListForRepoFilter filter = GitHubListForRepoFilterBuilder.builder()
                .state(GitHubListIssuesState.OPEN)
                .assignee("octocat")
                .sort(GitHubListIssuesSort.CREATED)
                .direction(GitHubDirection.DESC)
                .build();

        api().listForRepo(OWNER, REPO, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues");
            softly.assertThat(request.uri().getQuery())
                    .contains("state=open")
                    .contains("assignee=octocat")
                    .contains("sort=created")
                    .contains("direction=desc");
        });
    }

    @Test
    void listForAuthenticatedUser_shouldReturnIssues_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().listForAuthenticatedUser();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listForAuthenticatedUser_shouldReturnIssues_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().listForAuthenticatedUser(GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listForAuthenticatedUser_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));
        final GitHubListForAuthenticatedUserFilter filter = GitHubListForAuthenticatedUserFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.MENTIONED)
                .state(GitHubListIssuesState.CLOSED)
                .sort(GitHubListIssuesSort.COMMENTS)
                .direction(GitHubDirection.DESC)
                .build();

        api().listForAuthenticatedUser(filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/issues");
            softly.assertThat(request.uri().getQuery())
                    .contains("filter=mentioned")
                    .contains("state=closed")
                    .contains("sort=comments")
                    .contains("direction=desc");
        });
    }

    @Test
    void create_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue.json")));
        final GitHubCreatePayload payload = GitHubCreatePayloadBuilder.builder(IssuesCatalogFixtures.createPayload())
                .title("Found a bug")
                .build();

        final GitHubIssue issue = api().create(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(issue).isEqualTo(IssuesCatalogFixtures.issue());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"title\":\"Found a bug\"");
        });
    }

    @Test
    void get_shouldReturnIssue_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue.json")));

        final Optional<GitHubIssue> issue = api().get(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(issue).contains(IssuesCatalogFixtures.issue());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309");
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubIssue> issue = api().get(OWNER, REPO, ISSUE_NUMBER);

        assertThat(issue).isEmpty();
    }

    @Test
    void update_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue-update.json")));

        api().update(OWNER, REPO, ISSUE_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309");
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void update_shouldPatchWithBody_whenPayloadPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue-update.json")));
        final GitHubUpdatePayload payload = GitHubUpdatePayloadBuilder.builder(IssuesCatalogFixtures.updatePayload())
                .title(OptionalNull.of("new title"))
                .build();

        final GitHubIssueUpdate updated = api().update(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(updated).isEqualTo(IssuesCatalogFixtures.issueUpdate());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"title\":\"new title\"");
        });
    }

    @Test
    void lock_shouldPutWithoutBody_whenNoPayloadOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().lock(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/lock");
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void lock_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().lock(OWNER, REPO, ISSUE_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/lock");
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void lock_shouldPutWithBody_whenPayloadPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubLockPayload payload = GitHubLockPayloadBuilder.builder(IssuesCatalogFixtures.lockPayload())
                .lockReason(GitHubIssueLockReason.SPAM)
                .build();

        api().lock(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/lock");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"lock_reason\":\"spam\"");
        });
    }

    @Test
    void unlock_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().unlock(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/lock");
        });
    }

    @Test
    void listSuggestions_shouldReturnSuggestions_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), suggestionItems()));

        final GitHubPage<GitHubIssueSuggestion> page = api().listSuggestions(OWNER, REPO, ISSUE_NUMBER);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueSuggestion());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listSuggestions_shouldReturnSuggestions_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), suggestionItems()));

        final GitHubPage<GitHubIssueSuggestion> page =
                api().listSuggestions(OWNER, REPO, ISSUE_NUMBER, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueSuggestion());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listSuggestions_shouldIncludeFilters_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), suggestionItems()));
        final GitHubListSuggestionsFilter filter = GitHubListSuggestionsFilterBuilder.builder()
                .state(GitHubListIssueSuggestionsState.PENDING)
                .action(GitHubIssueSuggestionAction.ADD_LABEL)
                .build();

        final GitHubPage<GitHubIssueSuggestion> page =
                api().listSuggestions(OWNER, REPO, ISSUE_NUMBER, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueSuggestion());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/suggestions");
            softly.assertThat(request.uri().getQuery())
                    .contains("state=pending")
                    .contains("action=add_label");
        });
    }

    @Test
    void approveSuggestion_shouldPost_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue-suggestion.json")));

        final GitHubIssueSuggestion suggestion = api().approveSuggestion(OWNER, REPO, ISSUE_NUMBER, SUGGESTION_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(suggestion).isEqualTo(IssuesCatalogFixtures.issueSuggestion());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/suggestions/12/approve");
        });
    }

    @Test
    void dismissSuggestion_shouldPost_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue-suggestion.json")));

        final GitHubIssueSuggestion suggestion = api().dismissSuggestion(OWNER, REPO, ISSUE_NUMBER, SUGGESTION_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(suggestion).isEqualTo(IssuesCatalogFixtures.issueSuggestion());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/suggestions/12/dismiss");
        });
    }

    @Test
    void listRelatesTo_shouldReturnIssues_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueItems()));

        final GitHubPage<GitHubIssue> page = api().listRelatesTo(OWNER, REPO, ISSUE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issue());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/relates_to");
        });
    }

    @Test
    void listRelatesTo_shouldThrow_whenRequestNull() {
        final Throwable thrown = catchThrowable(() -> api().listRelatesTo(OWNER, REPO, ISSUE_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void addRelatesTo_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("issue.json")));

        final GitHubIssue issue =
                api().addRelatesTo(OWNER, REPO, ISSUE_NUMBER, IssuesCatalogFixtures.addRelatesToPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(issue).isEqualTo(IssuesCatalogFixtures.issue());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/Hello-World/issues/1309/relates_to");
        });
    }

    @Test
    void addRelatesTo_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> api().addRelatesTo(OWNER, REPO, ISSUE_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void removeRelatesTo_shouldDelete_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("issue.json")));

        final GitHubIssue issue = api().removeRelatesTo(OWNER, REPO, ISSUE_NUMBER, 99L);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(issue).isEqualTo(IssuesCatalogFixtures.issue());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/repos/octocat/Hello-World/issues/1309/relates_to/99");
        });
    }

    private GitHubIssueApi api() {
        return new GitHubIssueApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubIssueApiTest.class, fileName);
    }

    private static String issueItems() {
        return "[" + json("issue.json") + "]";
    }

    private static String suggestionItems() {
        return "[" + json("issue-suggestion.json") + "]";
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
