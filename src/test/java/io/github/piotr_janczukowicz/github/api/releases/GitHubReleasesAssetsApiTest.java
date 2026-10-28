package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubReleasesAssetsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long RELEASE_ID = 1L;
    private static final long ASSET_ID = 1L;
    private static final String UPLOAD_URL = "https://uploads.github.com/repos/octocat/Hello-World/releases/1/assets";
    private static final String UPLOAD_URL_TEMPLATE =
            "https://uploads.github.com/repos/octocat/Hello-World/releases/1/assets{?name,label}";

    @TempDir
    private Path tempDir;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReleasesAssetsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listReleaseAssets_shouldReturnAssets_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("release-asset.json") + "]"));

        final GitHubPage<GitHubReleaseAsset> page =
                api().listReleaseAssets(OWNER, REPO, RELEASE_ID, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReleasesCatalogFixtures.releaseAsset());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getReleaseAsset_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubReleaseAsset> asset = api().getReleaseAsset(OWNER, REPO, ASSET_ID);

        assertThat(asset).isEmpty();
    }

    @Test
    void updateReleaseAsset_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release-asset.json")));
        final GitHubUpdateReleaseAssetPayload payload = ReleasesCatalogFixtures.updateReleaseAssetPayload();

        final GitHubReleaseAsset asset = api().updateReleaseAsset(OWNER, REPO, ASSET_ID, payload);

        assertThat(asset).isEqualTo(ReleasesCatalogFixtures.releaseAsset());
    }

    @Test
    void deleteReleaseAsset_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteReleaseAsset(OWNER, REPO, ASSET_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/releases/assets/1");
    }

    @Test
    void uploadReleaseAsset_shouldPostUriWithQuery_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("release-asset.json")));

        final GitHubReleaseAsset asset = api().uploadReleaseAsset(
                        UPLOAD_URL, assetFile(new byte[] {1, 2}), "application/zip", "example.zip", "label");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(asset).isEqualTo(ReleasesCatalogFixtures.releaseAsset());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://uploads.github.com/repos/octocat/Hello-World/releases/1/assets?name=example.zip&label=label");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/zip");
        });
    }

    @Test
    void listReleaseAssets_shouldReturnAssets_whenDefaultPagination() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("release-asset.json") + "]"));

        final GitHubPage<GitHubReleaseAsset> page = api().listReleaseAssets(OWNER, REPO, RELEASE_ID);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReleasesCatalogFixtures.releaseAsset());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void updateReleaseAsset_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release-asset.json")));

        api().updateReleaseAsset(OWNER, REPO, ASSET_ID, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void uploadReleaseAsset_shouldStripUriTemplate_whenUploadUrlHasExpansion() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("release-asset.json")));

        api().uploadReleaseAsset(
                        UPLOAD_URL_TEMPLATE, assetFile(new byte[] {1}), "application/zip", "example.zip", "label");
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString(
                        "https://uploads.github.com/repos/octocat/Hello-World/releases/1/assets?name=example.zip&label=label");
    }

    @Test
    void uploadReleaseAsset_shouldPostUriWithoutLabel_whenLabelNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("release-asset.json")));

        api().uploadReleaseAsset(
                        UPLOAD_URL,
                        () -> new ByteArrayInputStream(new byte[] {1}),
                        1L,
                        "application/zip",
                        "example.zip",
                        null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString("https://uploads.github.com/repos/octocat/Hello-World/releases/1/assets?name=example.zip");
    }

    private Path assetFile(final byte[] bytes) throws Exception {
        final Path file = tempDir.resolve("asset.bin");
        Files.write(file, bytes);
        return file;
    }

    private GitHubReleasesAssetsApi api() {
        return new GitHubReleasesAssetsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubReleasesAssetsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
