package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.gistsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
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
class GitHubGistApiIntegrationTest {

    private static final String GIST_ID = "aa5a315d61ae9438b18d";
    private static final String SHA = "57a7f021a713b1c5a6a199b54cc514735d2d462f";
    private static final String USERNAME = "octocat";

    private static final String GISTS_PATH = "/gists";
    private static final String PUBLIC_PATH = "/gists/public";
    private static final String STARRED_PATH = "/gists/starred";
    private static final String GIST_PATH = "/gists/aa5a315d61ae9438b18d";
    private static final String COMMITS_PATH = "/gists/aa5a315d61ae9438b18d/commits";
    private static final String FORKS_PATH = "/gists/aa5a315d61ae9438b18d/forks";
    private static final String STAR_PATH = "/gists/aa5a315d61ae9438b18d/star";
    private static final String REVISION_PATH = "/gists/aa5a315d61ae9438b18d/" + SHA;
    private static final String USER_GISTS_PATH = "/users/octocat/gists";

    private static final JsonType<List<GitHubBaseGist>> BASE_GISTS_TYPE = JsonTypes.listOf(GitHubBaseGist.class);
    private static final JsonType<List<GitHubGistCommit>> COMMITS_TYPE = JsonTypes.listOf(GitHubGistCommit.class);
    private static final JsonType<List<GitHubGistSimple>> SIMPLES_TYPE = JsonTypes.listOf(GitHubGistSimple.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listGists_shouldReturnGists_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, GISTS_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        final GitHubPage<GitHubBaseGist> page;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            page = api.api().gist().listGists();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedBaseGists());
        HTTP.verifyRequested(GET, GISTS_PATH);
        HTTP.verifyQueryParam(GET, GISTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, GISTS_PATH, "per_page", "100");
    }

    @Test
    void listGists_shouldSendSinceAndPaging_whenFilterAndPageRequestProvided() throws Exception {
        HTTP.stub(GET, GISTS_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api().gist().listGists(GistsCatalogFixtures.listFilter(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, GISTS_PATH, "since", "2022-09-20T12:11:58Z");
        HTTP.verifyQueryParam(GET, GISTS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, GISTS_PATH, "per_page", "10");
    }

    @Test
    void listGists_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                GISTS_PATH,
                okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json"))
                        .header(LINK, linkNext(HTTP.uri(GISTS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        final GitHubPage<GitHubBaseGist> first;
        final Optional<GitHubPage<GitHubBaseGist>> second;
        final Optional<GitHubPage<GitHubBaseGist>> third;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            first = api.api().gist().listGists();
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedBaseGists());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedBaseGists());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, GISTS_PATH, 2);
    }

    @Test
    void createGist_shouldReturnGist_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, GISTS_PATH, createdJson(jsonBytes(GitHubGistApiIntegrationTest.class, "gist-simple.json")));
        final GitHubCreatePayload payload = GistsCatalogFixtures.createPayload();

        final GitHubGistSimple gist;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            gist = api.api().gist().createGist(payload);
        }

        assertThat(gist).isEqualTo(expectedGistSimple());
        HTTP.verifyRequested(POST, GISTS_PATH);
        HTTP.verifyHeader(POST, GISTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, GISTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listPublicGists_shouldReturnGists_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PUBLIC_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        final GitHubPage<GitHubBaseGist> page;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            page = api.api().gist().listPublicGists();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedBaseGists());
        HTTP.verifyRequested(GET, PUBLIC_PATH);
        HTTP.verifyQueryParam(GET, PUBLIC_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, PUBLIC_PATH, "per_page", "100");
    }

    @Test
    void listPublicGists_shouldSendSinceAndPaging_whenFilterAndPageRequestProvided() throws Exception {
        HTTP.stub(GET, PUBLIC_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api()
                    .gist()
                    .listPublicGists(GistsCatalogFixtures.listPublicFilter(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, PUBLIC_PATH, "since", "2022-09-20T12:11:58Z");
        HTTP.verifyQueryParam(GET, PUBLIC_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, PUBLIC_PATH, "per_page", "10");
    }

    @Test
    void listStarredGists_shouldReturnGists_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, STARRED_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        final GitHubPage<GitHubBaseGist> page;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            page = api.api().gist().listStarredGists();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedBaseGists());
        HTTP.verifyRequested(GET, STARRED_PATH);
        HTTP.verifyQueryParam(GET, STARRED_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, STARRED_PATH, "per_page", "100");
    }

    @Test
    void listStarredGists_shouldSendSinceAndPaging_whenFilterAndPageRequestProvided() throws Exception {
        HTTP.stub(GET, STARRED_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api()
                    .gist()
                    .listStarredGists(GistsCatalogFixtures.listStarredFilter(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, STARRED_PATH, "since", "2022-09-20T12:11:58Z");
        HTTP.verifyQueryParam(GET, STARRED_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, STARRED_PATH, "per_page", "10");
    }

    @Test
    void getGist_shouldReturnGist_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, GIST_PATH, okJson(jsonBytes(GitHubGistApiIntegrationTest.class, "gist-simple.json")));

        final Optional<GitHubGistSimple> gist;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            gist = api.api().gist().getGist(GIST_ID);
        }

        assertThat(gist).contains(expectedGistSimple());
        HTTP.verifyRequested(GET, GIST_PATH);
    }

    @Test
    void getGist_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, GIST_PATH, notFound());

        final Optional<GitHubGistSimple> gist;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            gist = api.api().gist().getGist(GIST_ID);
        }

        assertThat(gist).isEmpty();
        HTTP.verifyRequested(GET, GIST_PATH);
    }

    @Test
    void updateGist_shouldReturnGist_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, GIST_PATH, okJson(jsonBytes(GitHubGistApiIntegrationTest.class, "gist-simple.json")));
        final GitHubUpdatePayload payload = GistsCatalogFixtures.updatePayload();

        final GitHubGistSimple gist;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            gist = api.api().gist().updateGist(GIST_ID, payload);
        }

        assertThat(gist).isEqualTo(expectedGistSimple());
        HTTP.verifyRequested(PATCH, GIST_PATH);
        HTTP.verifyHeader(PATCH, GIST_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, GIST_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteGist_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, GIST_PATH, noContent());

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api().gist().deleteGist(GIST_ID);
        }

        HTTP.verifyRequested(DELETE, GIST_PATH);
    }

    @Test
    void listGistCommits_shouldReturnCommits_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, COMMITS_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "gist-commit.json")));

        final GitHubPage<GitHubGistCommit> page;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            page = api.api().gist().listGistCommits(GIST_ID);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(CODEC.decode(
                        listJsonBytes(GitHubGistApiIntegrationTest.class, "gist-commit.json"), COMMITS_TYPE));
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COMMITS_PATH, "per_page", "100");
    }

    @Test
    void listGistForks_shouldSendPageAndPerPage_whenCustomPageRequest() throws Exception {
        HTTP.stub(GET, FORKS_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "gist-simple.json")));

        final GitHubPage<GitHubGistSimple> page;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            page = api.api().gist().listGistForks(GIST_ID, GitHubPageNumberRequest.of(2, 10));
        }

        assertThat(page.items())
                .containsExactlyElementsOf(CODEC.decode(
                        listJsonBytes(GitHubGistApiIntegrationTest.class, "gist-simple.json"), SIMPLES_TYPE));
        HTTP.verifyQueryParam(GET, FORKS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, FORKS_PATH, "per_page", "10");
    }

    @Test
    void forkGist_shouldReturnFork_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, FORKS_PATH, createdJson(jsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        final GitHubBaseGist fork;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            fork = api.api().gist().forkGist(GIST_ID);
        }

        assertThat(fork)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json"), GitHubBaseGist.class));
        HTTP.verifyRequested(POST, FORKS_PATH);
    }

    @Test
    void isGistStarred_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, STAR_PATH, noContent());

        final boolean starred;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            starred = api.api().gist().isGistStarred(GIST_ID);
        }

        assertThat(starred).isTrue();
        HTTP.verifyRequested(GET, STAR_PATH);
    }

    @Test
    void isGistStarred_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, STAR_PATH, notFound());

        final boolean starred;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            starred = api.api().gist().isGistStarred(GIST_ID);
        }

        assertThat(starred).isFalse();
    }

    @Test
    void starGist_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, STAR_PATH, noContent());

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api().gist().starGist(GIST_ID);
        }

        HTTP.verifyRequested(PUT, STAR_PATH);
        HTTP.verifyAbsentBody(PUT, STAR_PATH);
    }

    @Test
    void unstarGist_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, STAR_PATH, noContent());

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api().gist().unstarGist(GIST_ID);
        }

        HTTP.verifyRequested(DELETE, STAR_PATH);
    }

    @Test
    void getGistRevision_shouldReturnGist_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, REVISION_PATH, okJson(jsonBytes(GitHubGistApiIntegrationTest.class, "gist-simple.json")));

        final Optional<GitHubGistSimple> gist;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            gist = api.api().gist().getGistRevision(GIST_ID, SHA);
        }

        assertThat(gist).contains(expectedGistSimple());
        HTTP.verifyRequested(GET, REVISION_PATH);
    }

    @Test
    void listGistsForUser_shouldReturnGists_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_GISTS_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        final GitHubPage<GitHubBaseGist> page;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            page = api.api().gist().listGistsForUser(USERNAME);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedBaseGists());
        HTTP.verifyRequested(GET, USER_GISTS_PATH);
        HTTP.verifyQueryParam(GET, USER_GISTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, USER_GISTS_PATH, "per_page", "100");
    }

    @Test
    void listGistsForUser_shouldSendSinceAndPaging_whenFilterAndPageRequestProvided() throws Exception {
        HTTP.stub(GET, USER_GISTS_PATH, okJson(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json")));

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api()
                    .gist()
                    .listGistsForUser(
                            USERNAME, GistsCatalogFixtures.listForUserFilter(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, USER_GISTS_PATH, "since", "2022-09-20T12:11:58Z");
        HTTP.verifyQueryParam(GET, USER_GISTS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, USER_GISTS_PATH, "per_page", "10");
    }

    private static List<GitHubBaseGist> expectedBaseGists() {
        return CODEC.decode(listJsonBytes(GitHubGistApiIntegrationTest.class, "base-gist.json"), BASE_GISTS_TYPE);
    }

    private static GitHubGistSimple expectedGistSimple() {
        return CODEC.decode(jsonBytes(GitHubGistApiIntegrationTest.class, "gist-simple.json"), GitHubGistSimple.class);
    }
}
