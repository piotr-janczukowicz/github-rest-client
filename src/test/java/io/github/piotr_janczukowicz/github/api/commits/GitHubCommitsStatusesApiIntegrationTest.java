package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.commitsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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
class GitHubCommitsStatusesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String REF = "6dcb09b5b57875f334f61aebed695e2e4193db5e";

    private static final String CREATE_PATH = "/repos/octocat/Hello-World/statuses/" + REF;
    private static final String LIST_PATH = "/repos/octocat/Hello-World/commits/" + REF + "/statuses";
    private static final String COMBINED_PATH = "/repos/octocat/Hello-World/commits/" + REF + "/status";

    private static final JsonType<List<GitHubCommitStatus>> STATUSES_TYPE = JsonTypes.listOf(GitHubCommitStatus.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createCommitStatus_shouldReturnStatus_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                CREATE_PATH,
                createdJson(jsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "commit-status.json")));
        final GitHubCreateCommitStatusPayload payload = CommitsCatalogFixtures.createCommitStatusPayload();

        final GitHubCommitStatus status;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            status = api.api().statuses().createCommitStatus(OWNER, REPO, REF, payload);
        }

        final GitHubCommitStatus expected = CODEC.decode(
                jsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "commit-status.json"),
                GitHubCommitStatus.class);

        assertThat(status).isEqualTo(expected);
        HTTP.verifyRequested(POST, CREATE_PATH);
        HTTP.verifyHeader(POST, CREATE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, CREATE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listCommitStatusesForRef_shouldReturnStatuses_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(listJsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "commit-status.json")));

        final GitHubPage<GitHubCommitStatus> page;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            page = api.api().statuses().listCommitStatusesForRef(OWNER, REPO, REF);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedStatuses());
        HTTP.verifyRequested(GET, LIST_PATH);
        HTTP.verifyQueryParam(GET, LIST_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, LIST_PATH, "per_page", "100");
    }

    @Test
    void listCommitStatusesForRef_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(listJsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "commit-status.json"))
                        .header(LINK, linkNext(HTTP.uri(LIST_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "commit-status.json")));

        final GitHubPage<GitHubCommitStatus> first;
        final Optional<GitHubPage<GitHubCommitStatus>> second;
        final Optional<GitHubPage<GitHubCommitStatus>> third;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            first = api.api().statuses().listCommitStatusesForRef(OWNER, REPO, REF);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedStatuses());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedStatuses());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, LIST_PATH, 2);
    }

    @Test
    void listCommitStatusesForRef_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(listJsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "commit-status.json")));

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api().statuses().listCommitStatusesForRef(OWNER, REPO, REF, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, LIST_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, LIST_PATH, "per_page", "10");
    }

    @Test
    void getCombinedStatusForRef_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COMBINED_PATH,
                okJson(jsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "combined-commit-status.json")));

        final Optional<GitHubCombinedCommitStatus> status;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            status = api.api().statuses().getCombinedStatusForRef(OWNER, REPO, REF);
        }

        final GitHubCombinedCommitStatus expected = CODEC.decode(
                jsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "combined-commit-status.json"),
                GitHubCombinedCommitStatus.class);

        assertThat(status).contains(expected);
        HTTP.verifyRequested(GET, COMBINED_PATH);
        HTTP.verifyQueryParam(GET, COMBINED_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COMBINED_PATH, "per_page", "100");
    }

    @Test
    void getCombinedStatusForRef_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMBINED_PATH, notFound());

        final Optional<GitHubCombinedCommitStatus> status;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            status = api.api().statuses().getCombinedStatusForRef(OWNER, REPO, REF);
        }

        assertThat(status).isEmpty();
        HTTP.verifyRequested(GET, COMBINED_PATH);
    }

    @Test
    void getCombinedStatusForRef_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                COMBINED_PATH,
                okJson(jsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "combined-commit-status.json")));

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api().statuses().getCombinedStatusForRef(OWNER, REPO, REF, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COMBINED_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COMBINED_PATH, "per_page", "10");
    }

    private static List<GitHubCommitStatus> expectedStatuses() {
        return CODEC.decode(
                listJsonBytes(GitHubCommitsStatusesApiIntegrationTest.class, "commit-status.json"), STATUSES_TYPE);
    }
}
