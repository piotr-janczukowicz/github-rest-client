package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.issuesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubIssueApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ORG = "github";
    private static final int ISSUE_NUMBER = 1309;
    private static final long SUGGESTION_ID = 12L;

    private static final String ISSUES_PATH = "/issues";
    private static final String ORG_ISSUES_PATH = "/orgs/github/issues";
    private static final String REPO_ISSUES_PATH = "/repos/octocat/Hello-World/issues";
    private static final String USER_ISSUES_PATH = "/user/issues";
    private static final String ISSUE_ITEM_PATH = "/repos/octocat/Hello-World/issues/1309";
    private static final String LOCK_PATH = "/repos/octocat/Hello-World/issues/1309/lock";
    private static final String SUGGESTIONS_PATH = "/repos/octocat/Hello-World/issues/1309/suggestions";
    private static final String APPROVE_PATH = "/repos/octocat/Hello-World/issues/1309/suggestions/12/approve";
    private static final String DISMISS_PATH = "/repos/octocat/Hello-World/issues/1309/suggestions/12/dismiss";
    private static final String RELATES_TO_PATH = "/repos/octocat/Hello-World/issues/1309/relates_to";
    private static final String RELATES_TO_ISSUE_PATH = "/repos/octocat/Hello-World/issues/1309/relates_to/99";

    private static final JsonType<List<GitHubIssue>> ISSUES_TYPE = JsonTypes.listOf(GitHubIssue.class);
    private static final JsonType<List<GitHubIssueSuggestion>> SUGGESTIONS_TYPE =
            JsonTypes.listOf(GitHubIssueSuggestion.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnIssues_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ISSUES_PATH, okJson(listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        final GitHubPage<GitHubIssue> page;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            page = issues.api().issue().list();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedIssues());
        HTTP.verifyRequested(GET, ISSUES_PATH);
    }

    @Test
    void listForOrg_shouldReturnIssues_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_ISSUES_PATH, okJson(listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        final GitHubPage<GitHubIssue> page;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            page = issues.api().issue().listForOrg(ORG);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedIssues());
        HTTP.verifyRequested(GET, ORG_ISSUES_PATH);
    }

    @Test
    void listForRepo_shouldReturnIssues_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, REPO_ISSUES_PATH, okJson(listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        final GitHubPage<GitHubIssue> page;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            page = issues.api().issue().listForRepo(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedIssues());
        HTTP.verifyRequested(GET, REPO_ISSUES_PATH);
    }

    @Test
    void listForAuthenticatedUser_shouldReturnIssues_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_ISSUES_PATH, okJson(listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        final GitHubPage<GitHubIssue> page;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            page = issues.api().issue().listForAuthenticatedUser();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedIssues());
        HTTP.verifyRequested(GET, USER_ISSUES_PATH);
    }

    @Test
    void create_shouldReturnIssue_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, REPO_ISSUES_PATH, createdJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));
        final GitHubCreatePayload payload = IssuesCatalogFixtures.createPayload();

        final GitHubIssue issue;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issue = issues.api().issue().create(OWNER, REPO, payload);
        }

        assertThat(issue).isEqualTo(expectedIssue());
        HTTP.verifyRequested(POST, REPO_ISSUES_PATH);
        HTTP.verifyHeader(POST, REPO_ISSUES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, REPO_ISSUES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void get_shouldReturnIssue_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ISSUE_ITEM_PATH, okJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        final Optional<GitHubIssue> issue;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issue = issues.api().issue().get(OWNER, REPO, ISSUE_NUMBER);
        }

        assertThat(issue).contains(expectedIssue());
        HTTP.verifyRequested(GET, ISSUE_ITEM_PATH);
    }

    @Test
    void update_shouldReturnIssue_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, ISSUE_ITEM_PATH, okJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue-update.json")));
        final GitHubUpdatePayload payload = IssuesCatalogFixtures.updatePayload();

        final GitHubIssueUpdate issue;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issue = issues.api().issue().update(OWNER, REPO, ISSUE_NUMBER, payload);
        }

        assertThat(issue).isEqualTo(expectedIssueUpdate());
        HTTP.verifyRequested(PATCH, ISSUE_ITEM_PATH);
        HTTP.verifyHeader(PATCH, ISSUE_ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, ISSUE_ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void lock_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, LOCK_PATH, noContent());

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issues.api().issue().lock(OWNER, REPO, ISSUE_NUMBER);
        }

        HTTP.verifyRequested(PUT, LOCK_PATH);
    }

    @Test
    void lock_shouldSendPayload_whenLockReasonPresent() throws Exception {
        HTTP.stub(PUT, LOCK_PATH, noContent());
        final GitHubLockPayload payload = IssuesCatalogFixtures.lockPayload();

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issues.api().issue().lock(OWNER, REPO, ISSUE_NUMBER, payload);
        }

        HTTP.verifyRequested(PUT, LOCK_PATH);
        HTTP.verifyHeader(PUT, LOCK_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, LOCK_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void unlock_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, LOCK_PATH, noContent());

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issues.api().issue().unlock(OWNER, REPO, ISSUE_NUMBER);
        }

        HTTP.verifyRequested(DELETE, LOCK_PATH);
    }

    @Test
    void listSuggestions_shouldReturnSuggestions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                SUGGESTIONS_PATH,
                okJson(listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue-suggestion.json")));

        final GitHubPage<GitHubIssueSuggestion> page;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            page = issues.api().issue().listSuggestions(OWNER, REPO, ISSUE_NUMBER);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedSuggestions());
        HTTP.verifyRequested(GET, SUGGESTIONS_PATH);
    }

    @Test
    void approveSuggestion_shouldReturnSuggestion_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, APPROVE_PATH, okJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue-suggestion.json")));

        final GitHubIssueSuggestion suggestion;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            suggestion = issues.api().issue().approveSuggestion(OWNER, REPO, ISSUE_NUMBER, SUGGESTION_ID);
        }

        assertThat(suggestion).isEqualTo(expectedSuggestion());
        HTTP.verifyRequested(POST, APPROVE_PATH);
    }

    @Test
    void dismissSuggestion_shouldReturnSuggestion_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, DISMISS_PATH, okJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue-suggestion.json")));

        final GitHubIssueSuggestion suggestion;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            suggestion = issues.api().issue().dismissSuggestion(OWNER, REPO, ISSUE_NUMBER, SUGGESTION_ID);
        }

        assertThat(suggestion).isEqualTo(expectedSuggestion());
        HTTP.verifyRequested(POST, DISMISS_PATH);
    }

    @Test
    void listRelatesTo_shouldReturnIssues_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, RELATES_TO_PATH, okJson(listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        final GitHubPage<GitHubIssue> page;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            page = issues.api().issue().listRelatesTo(OWNER, REPO, ISSUE_NUMBER);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedIssues());
        HTTP.verifyRequested(GET, RELATES_TO_PATH);
    }

    @Test
    void addRelatesTo_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, RELATES_TO_PATH, createdJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));
        final GitHubAddRelatesToPayload payload = IssuesCatalogFixtures.addRelatesToPayload();

        final GitHubIssue issue;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issue = issues.api().issue().addRelatesTo(OWNER, REPO, ISSUE_NUMBER, payload);
        }

        assertThat(issue).isEqualTo(expectedIssue());
        HTTP.verifyHeader(POST, RELATES_TO_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, RELATES_TO_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removeRelatesTo_shouldReturnIssue_whenResponseIsOk() throws Exception {
        HTTP.stub(DELETE, RELATES_TO_ISSUE_PATH, okJson(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json")));

        final GitHubIssue issue;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issue = issues.api().issue().removeRelatesTo(OWNER, REPO, ISSUE_NUMBER, 99L);
        }

        assertThat(issue).isEqualTo(expectedIssue());
        HTTP.verifyRequested(DELETE, RELATES_TO_ISSUE_PATH);
    }

    private static GitHubIssue expectedIssue() {
        return CODEC.decode(jsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json"), GitHubIssue.class);
    }

    private static GitHubIssueUpdate expectedIssueUpdate() {
        return CODEC.decode(
                jsonBytes(GitHubIssueApiIntegrationTest.class, "issue-update.json"), GitHubIssueUpdate.class);
    }

    private static List<GitHubIssue> expectedIssues() {
        return CODEC.decode(listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue.json"), ISSUES_TYPE);
    }

    private static GitHubIssueSuggestion expectedSuggestion() {
        return CODEC.decode(
                jsonBytes(GitHubIssueApiIntegrationTest.class, "issue-suggestion.json"), GitHubIssueSuggestion.class);
    }

    private static List<GitHubIssueSuggestion> expectedSuggestions() {
        return CODEC.decode(
                listJsonBytes(GitHubIssueApiIntegrationTest.class, "issue-suggestion.json"), SUGGESTIONS_TYPE);
    }
}
