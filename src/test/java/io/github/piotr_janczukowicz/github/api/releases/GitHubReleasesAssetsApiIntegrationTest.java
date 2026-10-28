package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.AUTHORIZATION;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpStatus.FOUND;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

@IntegrationTest
class GitHubReleasesAssetsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long RELEASE_ID = 1L;
    private static final long ASSET_ID = 1L;

    private static final String RELEASE_ASSETS_PATH = "/repos/octocat/Hello-World/releases/1/assets";
    private static final String ASSET_ITEM_PATH = "/repos/octocat/Hello-World/releases/assets/1";

    private static final JsonType<List<GitHubReleaseAsset>> ASSETS_TYPE = JsonTypes.listOf(GitHubReleaseAsset.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @RegisterExtension
    static final StubHttpServer STORAGE = new StubHttpServer("localhost");

    @TempDir
    private Path tempDir;

    @Test
    void downloadReleaseAsset_shouldWriteFile_whenAssetIsOnStorageHost() throws Exception {
        HTTP.stubRedirect(ASSET_ITEM_PATH, STORAGE.uri("/asset.bin").toString(), FOUND.code());
        STORAGE.stub(GET, "/asset.bin", status(OK).body(new byte[] {1, 2, 3}));
        final Path target = tempDir.resolve("asset.bin");

        final Path result;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            result = api.api().assets().downloadReleaseAsset(OWNER, REPO, ASSET_ID, target);
        }

        final byte[] saved = Files.readAllBytes(target);
        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(target);
            softly.assertThat(saved).containsExactly(1, 2, 3);
        });
        HTTP.verifyHeader(GET, ASSET_ITEM_PATH, ACCEPT, "application/octet-stream");
        STORAGE.verifyNoHeader(GET, "/asset.bin", AUTHORIZATION);
    }

    @Test
    void downloadReleaseAsset_shouldWriteStream_whenAssetIsOnStorageHost() throws Exception {
        HTTP.stubRedirect(ASSET_ITEM_PATH, STORAGE.uri("/asset.bin").toString(), FOUND.code());
        STORAGE.stub(GET, "/asset.bin", status(OK).body(new byte[] {4, 5}));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final long written;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            written = api.api().assets().downloadReleaseAsset(OWNER, REPO, ASSET_ID, out);
        }

        assertSoftly(softly -> {
            softly.assertThat(written).isEqualTo(2);
            softly.assertThat(out.toByteArray()).containsExactly(4, 5);
        });
    }

    @Test
    void listReleaseAssets_shouldReturnAssets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                RELEASE_ASSETS_PATH,
                okJson(listJsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));

        final GitHubPage<GitHubReleaseAsset> page;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            page = api.api().assets().listReleaseAssets(OWNER, REPO, RELEASE_ID);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedAssets());
        HTTP.verifyRequested(GET, RELEASE_ASSETS_PATH);
        HTTP.verifyQueryParam(GET, RELEASE_ASSETS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, RELEASE_ASSETS_PATH, "per_page", "100");
    }

    @Test
    void listReleaseAssets_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                RELEASE_ASSETS_PATH,
                okJson(listJsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json"))
                        .header(
                                LINK,
                                linkNext(HTTP.uri(RELEASE_ASSETS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));

        final GitHubPage<GitHubReleaseAsset> first;
        final Optional<GitHubPage<GitHubReleaseAsset>> second;
        final Optional<GitHubPage<GitHubReleaseAsset>> third;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            first = api.api().assets().listReleaseAssets(OWNER, REPO, RELEASE_ID);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedAssets());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedAssets());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, RELEASE_ASSETS_PATH, 2);
    }

    @Test
    void listReleaseAssets_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                RELEASE_ASSETS_PATH,
                okJson(listJsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));

        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            api.api().assets().listReleaseAssets(OWNER, REPO, RELEASE_ID, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, RELEASE_ASSETS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, RELEASE_ASSETS_PATH, "per_page", "10");
    }

    @Test
    void getReleaseAsset_shouldReturnAsset_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ASSET_ITEM_PATH,
                okJson(jsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));

        final Optional<GitHubReleaseAsset> asset;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            asset = api.api().assets().getReleaseAsset(OWNER, REPO, ASSET_ID);
        }

        assertThat(asset).contains(expectedAsset());
        HTTP.verifyRequested(GET, ASSET_ITEM_PATH);
    }

    @Test
    void getReleaseAsset_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ASSET_ITEM_PATH, notFound());

        final Optional<GitHubReleaseAsset> asset;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            asset = api.api().assets().getReleaseAsset(OWNER, REPO, ASSET_ID);
        }

        assertThat(asset).isEmpty();
        HTTP.verifyRequested(GET, ASSET_ITEM_PATH);
    }

    @Test
    void updateReleaseAsset_shouldReturnAsset_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                ASSET_ITEM_PATH,
                okJson(jsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));
        final GitHubUpdateReleaseAssetPayload payload = ReleasesCatalogFixtures.updateReleaseAssetPayload();

        final GitHubReleaseAsset asset;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            asset = api.api().assets().updateReleaseAsset(OWNER, REPO, ASSET_ID, payload);
        }

        assertThat(asset).isEqualTo(expectedAsset());
        HTTP.verifyRequested(PATCH, ASSET_ITEM_PATH);
        HTTP.verifyHeader(PATCH, ASSET_ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, ASSET_ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateReleaseAsset_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(
                PATCH,
                ASSET_ITEM_PATH,
                okJson(jsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));

        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            api.api().assets().updateReleaseAsset(OWNER, REPO, ASSET_ID, null);
        }

        HTTP.verifyRequested(PATCH, ASSET_ITEM_PATH);
    }

    @Test
    void deleteReleaseAsset_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ASSET_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            api.api().assets().deleteReleaseAsset(OWNER, REPO, ASSET_ID);
        }

        HTTP.verifyRequested(DELETE, ASSET_ITEM_PATH);
    }

    @Test
    void uploadReleaseAsset_shouldReturnAsset_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                RELEASE_ASSETS_PATH,
                createdJson(jsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));
        final String uploadUrl = HTTP.uri(RELEASE_ASSETS_PATH).toString();

        final GitHubReleaseAsset asset;
        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            asset = api.api()
                    .assets()
                    .uploadReleaseAsset(
                            uploadUrl, assetFile(new byte[] {1, 2}), "application/zip", "example.zip", "label");
        }

        assertThat(asset).isEqualTo(expectedAsset());
        HTTP.verifyRequested(POST, RELEASE_ASSETS_PATH);
        HTTP.verifyHeader(POST, RELEASE_ASSETS_PATH, CONTENT_TYPE, "application/zip");
        HTTP.verifyQueryParam(POST, RELEASE_ASSETS_PATH, "name", "example.zip");
        HTTP.verifyQueryParam(POST, RELEASE_ASSETS_PATH, "label", "label");
    }

    @Test
    void uploadReleaseAsset_shouldStripUriTemplate_whenUploadUrlHasExpansion() throws Exception {
        HTTP.stub(
                POST,
                RELEASE_ASSETS_PATH,
                createdJson(jsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));
        final String uploadUrl = HTTP.uri(RELEASE_ASSETS_PATH) + "{?name,label}";

        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            api.api()
                    .assets()
                    .uploadReleaseAsset(
                            uploadUrl, assetFile(new byte[] {1}), "application/zip", "example.zip", "label");
        }

        HTTP.verifyRequested(POST, RELEASE_ASSETS_PATH);
        HTTP.verifyQueryParam(POST, RELEASE_ASSETS_PATH, "name", "example.zip");
        HTTP.verifyQueryParam(POST, RELEASE_ASSETS_PATH, "label", "label");
    }

    @Test
    void uploadReleaseAsset_shouldOmitLabel_whenLabelNull() throws Exception {
        HTTP.stub(
                POST,
                RELEASE_ASSETS_PATH,
                createdJson(jsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json")));
        final String uploadUrl = HTTP.uri(RELEASE_ASSETS_PATH).toString();

        try (final GitHubApiWireMockSupport.ReleasesApiClient api = releasesApi(HTTP)) {
            api.api()
                    .assets()
                    .uploadReleaseAsset(
                            uploadUrl,
                            () -> new ByteArrayInputStream(new byte[] {1}),
                            1L,
                            "application/zip",
                            "example.zip",
                            null);
        }

        HTTP.verifyQueryParam(POST, RELEASE_ASSETS_PATH, "name", "example.zip");
        HTTP.verifyNoQueryParam(POST, RELEASE_ASSETS_PATH, "label");
    }

    private Path assetFile(final byte[] bytes) throws Exception {
        final Path file = tempDir.resolve("asset.bin");
        Files.write(file, bytes);
        return file;
    }

    private static GitHubReleaseAsset expectedAsset() {
        return CODEC.decode(
                jsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json"),
                GitHubReleaseAsset.class);
    }

    private static List<GitHubReleaseAsset> expectedAssets() {
        return CODEC.decode(
                listJsonBytes(GitHubReleasesAssetsApiIntegrationTest.class, "release-asset.json"), ASSETS_TYPE);
    }
}
