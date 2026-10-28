package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.releasesApi;
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
class GitHubReleaseApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long RELEASE_ID = 1L;
    private static final String TAG = "v1.0.0";

    private static final String RELEASES_PATH = "/repos/octocat/Hello-World/releases";
    private static final String RELEASE_ITEM_PATH = "/repos/octocat/Hello-World/releases/1";
    private static final String LATEST_PATH = "/repos/octocat/Hello-World/releases/latest";
    private static final String TAG_PATH = "/repos/octocat/Hello-World/releases/tags/v1.0.0";
    private static final String GENERATE_NOTES_PATH = "/repos/octocat/Hello-World/releases/generate-notes";

    private static final JsonType<List<GitHubRelease>> RELEASES_TYPE = JsonTypes.listOf(GitHubRelease.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listReleases_shouldReturnReleases_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, RELEASES_PATH, okJson(listJsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));

        final GitHubPage<GitHubRelease> page;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            page = api.api().release().listReleases(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedReleases());
        HTTP.verifyRequested(GET, RELEASES_PATH);
        HTTP.verifyQueryParam(GET, RELEASES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, RELEASES_PATH, "per_page", "100");
    }

    @Test
    void listReleases_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                RELEASES_PATH,
                okJson(listJsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json"))
                        .header(LINK, linkNext(HTTP.uri(RELEASES_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));

        final GitHubPage<GitHubRelease> first;
        final Optional<GitHubPage<GitHubRelease>> second;
        final Optional<GitHubPage<GitHubRelease>> third;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            first = api.api().release().listReleases(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedReleases());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedReleases());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, RELEASES_PATH, 2);
    }

    @Test
    void listReleases_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, RELEASES_PATH, okJson(listJsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));

        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            api.api().release().listReleases(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, RELEASES_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, RELEASES_PATH, "per_page", "10");
    }

    @Test
    void getRelease_shouldReturnRelease_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, RELEASE_ITEM_PATH, okJson(jsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));

        final Optional<GitHubRelease> release;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            release = api.api().release().getRelease(OWNER, REPO, RELEASE_ID);
        }

        assertThat(release).contains(expectedRelease());
        HTTP.verifyRequested(GET, RELEASE_ITEM_PATH);
    }

    @Test
    void getRelease_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, RELEASE_ITEM_PATH, notFound());

        final Optional<GitHubRelease> release;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            release = api.api().release().getRelease(OWNER, REPO, RELEASE_ID);
        }

        assertThat(release).isEmpty();
        HTTP.verifyRequested(GET, RELEASE_ITEM_PATH);
    }

    @Test
    void getLatestRelease_shouldReturnRelease_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LATEST_PATH, okJson(jsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));

        final Optional<GitHubRelease> release;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            release = api.api().release().getLatestRelease(OWNER, REPO);
        }

        assertThat(release).contains(expectedRelease());
        HTTP.verifyRequested(GET, LATEST_PATH);
    }

    @Test
    void getLatestRelease_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, LATEST_PATH, notFound());

        final Optional<GitHubRelease> release;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            release = api.api().release().getLatestRelease(OWNER, REPO);
        }

        assertThat(release).isEmpty();
        HTTP.verifyRequested(GET, LATEST_PATH);
    }

    @Test
    void getReleaseByTag_shouldReturnRelease_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TAG_PATH, okJson(jsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));

        final Optional<GitHubRelease> release;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            release = api.api().release().getReleaseByTag(OWNER, REPO, TAG);
        }

        assertThat(release).contains(expectedRelease());
        HTTP.verifyRequested(GET, TAG_PATH);
    }

    @Test
    void getReleaseByTag_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, TAG_PATH, notFound());

        final Optional<GitHubRelease> release;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            release = api.api().release().getReleaseByTag(OWNER, REPO, TAG);
        }

        assertThat(release).isEmpty();
        HTTP.verifyRequested(GET, TAG_PATH);
    }

    @Test
    void createRelease_shouldReturnRelease_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, RELEASES_PATH, createdJson(jsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));
        final GitHubCreateReleasePayload payload = ReleasesCatalogFixtures.createReleasePayload();

        final GitHubRelease release;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            release = api.api().release().createRelease(OWNER, REPO, payload);
        }

        assertThat(release).isEqualTo(expectedRelease());
        HTTP.verifyRequested(POST, RELEASES_PATH);
        HTTP.verifyHeader(POST, RELEASES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, RELEASES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateRelease_shouldReturnRelease_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, RELEASE_ITEM_PATH, okJson(jsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));
        final GitHubUpdateReleasePayload payload = ReleasesCatalogFixtures.updateReleasePayload();

        final GitHubRelease release;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            release = api.api().release().updateRelease(OWNER, REPO, RELEASE_ID, payload);
        }

        assertThat(release).isEqualTo(expectedRelease());
        HTTP.verifyRequested(PATCH, RELEASE_ITEM_PATH);
        HTTP.verifyHeader(PATCH, RELEASE_ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, RELEASE_ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateRelease_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(PATCH, RELEASE_ITEM_PATH, okJson(jsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json")));

        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            api.api().release().updateRelease(OWNER, REPO, RELEASE_ID, null);
        }

        HTTP.verifyRequested(PATCH, RELEASE_ITEM_PATH);
    }

    @Test
    void deleteRelease_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, RELEASE_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            api.api().release().deleteRelease(OWNER, REPO, RELEASE_ID);
        }

        HTTP.verifyRequested(DELETE, RELEASE_ITEM_PATH);
    }

    @Test
    void generateReleaseNotes_shouldReturnNotes_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                GENERATE_NOTES_PATH,
                okJson(jsonBytes(GitHubReleaseApiIntegrationTest.class, "release-notes-content.json")));
        final GitHubGenerateReleaseNotesPayload payload = ReleasesCatalogFixtures.generateReleaseNotesPayload();

        final GitHubReleaseNotesContent notes;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            notes = api.api().release().generateReleaseNotes(OWNER, REPO, payload);
        }

        final GitHubReleaseNotesContent expected = CODEC.decode(
                jsonBytes(GitHubReleaseApiIntegrationTest.class, "release-notes-content.json"),
                GitHubReleaseNotesContent.class);

        assertThat(notes).isEqualTo(expected);
        HTTP.verifyRequested(POST, GENERATE_NOTES_PATH);
        HTTP.verifyHeader(POST, GENERATE_NOTES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, GENERATE_NOTES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    private static GitHubRelease expectedRelease() {
        return CODEC.decode(jsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json"), GitHubRelease.class);
    }

    private static List<GitHubRelease> expectedReleases() {
        return CODEC.decode(listJsonBytes(GitHubReleaseApiIntegrationTest.class, "release.json"), RELEASES_TYPE);
    }
}
