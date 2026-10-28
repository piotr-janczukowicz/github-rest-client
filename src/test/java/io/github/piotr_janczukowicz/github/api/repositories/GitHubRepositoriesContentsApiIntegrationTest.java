package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.AUTHORIZATION;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.http.HttpStatus.FOUND;
import static io.github.piotr_janczukowicz.http.HttpStatus.NOT_FOUND;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposContentsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

@IntegrationTest
class GitHubRepositoriesContentsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String PATH = "README.md";
    private static final String DIR = "docs";
    private static final String REF = "main";
    private static final URI ARCHIVE_LOCATION =
            URI.create("https://codeload.github.com/octocat/Hello-World/legacy.tar.gz/main");
    private static final URI CONTENT_LOCATION =
            URI.create("https://raw.githubusercontent.com/octocat/Hello-World/main/README.md");

    private static final String ITEM_PATH = "/repos/octocat/Hello-World/contents/README.md";
    private static final String README_PATH = "/repos/octocat/Hello-World/readme";
    private static final String README_DIR_PATH = "/repos/octocat/Hello-World/readme/docs";
    private static final String TARBALL_PATH = "/repos/octocat/Hello-World/tarball/main";
    private static final String ZIPBALL_PATH = "/repos/octocat/Hello-World/zipball/main";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @RegisterExtension
    static final StubHttpServer CODELOAD = new StubHttpServer("localhost");

    @TempDir
    Path tempDir;

    @Test
    void getContent_shouldReturnFile_whenPathResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ITEM_PATH,
                okJson(jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "content-file.json")));

        final Optional<GitHubContentResult> content;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            content = api.api().getContent(OWNER, REPO, PATH);
        }

        final GitHubContentResult expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "content-file.json"),
                GitHubContentResult.class);

        assertThat(content).contains(expected);
        HTTP.verifyRequested(GET, ITEM_PATH);
        HTTP.verifyNoQueryParam(GET, ITEM_PATH, "ref");
    }

    @Test
    void getContent_shouldReturnEmpty_whenPathResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubContentResult> content;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            content = api.api().getContent(OWNER, REPO, PATH);
        }

        assertThat(content).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getContent_shouldSendRefFilter_whenPathFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ITEM_PATH,
                okJson(jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "content-file.json")));

        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            api.api()
                    .getContent(
                            OWNER,
                            REPO,
                            PATH,
                            GitHubGetContentFilterBuilder.builder().ref(REF).build());
        }

        HTTP.verifyQueryParam(GET, ITEM_PATH, "ref", REF);
    }

    @Test
    void getContentRedirectLocation_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(ITEM_PATH, CONTENT_LOCATION.toString(), FOUND.code());

        final Optional<URI> location;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            location = api.api().getContentRedirectLocation(OWNER, REPO, PATH);
        }

        assertThat(location).contains(CONTENT_LOCATION);
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getContentRedirectLocation_shouldSendRefFilter_whenFilterProvided() throws Exception {
        HTTP.stubRedirect(ITEM_PATH, CONTENT_LOCATION.toString(), FOUND.code());

        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            api.api()
                    .getContentRedirectLocation(
                            OWNER,
                            REPO,
                            PATH,
                            GitHubGetContentFilterBuilder.builder().ref(REF).build());
        }

        HTTP.verifyQueryParam(GET, ITEM_PATH, "ref", REF);
    }

    @Test
    void createOrUpdateFileContents_shouldReturnFileCommit_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                ITEM_PATH,
                okJson(jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "file-commit.json")));
        final GitHubCreateOrUpdateFileContentsPayload payload =
                RepositoriesCatalogFixtures.createOrUpdateFileContentsPayload();

        final GitHubFileCommit fileCommit;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            fileCommit = api.api().createOrUpdateFileContents(OWNER, REPO, PATH, payload);
        }

        final GitHubFileCommit expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "file-commit.json"),
                GitHubFileCommit.class);

        assertThat(fileCommit).isEqualTo(expected);
        HTTP.verifyRequested(PUT, ITEM_PATH);
        HTTP.verifyHeader(PUT, ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteFile_shouldReturnFileCommit_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                ITEM_PATH,
                okJson(jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "file-commit.json")));
        final GitHubDeleteFilePayload payload = RepositoriesCatalogFixtures.deleteFilePayload();

        final GitHubFileCommit fileCommit;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            fileCommit = api.api().deleteFile(OWNER, REPO, PATH, payload);
        }

        final GitHubFileCommit expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "file-commit.json"),
                GitHubFileCommit.class);

        assertThat(fileCommit).isEqualTo(expected);
        HTTP.verifyRequested(DELETE, ITEM_PATH);
        HTTP.verifyHeader(DELETE, ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(DELETE, ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getReadme_shouldReturnFile_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                README_PATH,
                okJson(jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "content-file.json")));

        final Optional<GitHubContentFile> readme;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            readme = api.api().getReadme(OWNER, REPO);
        }

        final GitHubContentFile expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "content-file.json"),
                GitHubContentFile.class);

        assertThat(readme).contains(expected);
        HTTP.verifyRequested(GET, README_PATH);
        HTTP.verifyNoQueryParam(GET, README_PATH, "ref");
    }

    @Test
    void getReadme_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, README_PATH, notFound());

        final Optional<GitHubContentFile> readme;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            readme = api.api().getReadme(OWNER, REPO);
        }

        assertThat(readme).isEmpty();
        HTTP.verifyRequested(GET, README_PATH);
    }

    @Test
    void getReadme_shouldSendRefFilter_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                README_PATH,
                okJson(jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "content-file.json")));

        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            api.api()
                    .getReadme(
                            OWNER,
                            REPO,
                            GitHubGetContentFilterBuilder.builder().ref(REF).build());
        }

        HTTP.verifyQueryParam(GET, README_PATH, "ref", REF);
    }

    @Test
    void getReadmeInDirectory_shouldReturnFile_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                README_DIR_PATH,
                okJson(jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "content-file.json")));

        final Optional<GitHubContentFile> readme;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            readme = api.api().getReadmeInDirectory(OWNER, REPO, DIR);
        }

        final GitHubContentFile expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesContentsApiIntegrationTest.class, "content-file.json"),
                GitHubContentFile.class);

        assertThat(readme).contains(expected);
        HTTP.verifyRequested(GET, README_DIR_PATH);
    }

    @Test
    void getReadmeInDirectory_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, README_DIR_PATH, notFound());

        final Optional<GitHubContentFile> readme;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            readme = api.api().getReadmeInDirectory(OWNER, REPO, DIR);
        }

        assertThat(readme).isEmpty();
        HTTP.verifyRequested(GET, README_DIR_PATH);
    }

    @Test
    void downloadTarballArchive_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(TARBALL_PATH, ARCHIVE_LOCATION.toString(), FOUND.code());

        final URI location;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            location = api.api().downloadTarballArchive(OWNER, REPO, REF);
        }

        assertThat(location).isEqualTo(ARCHIVE_LOCATION);
        HTTP.verifyRequested(GET, TARBALL_PATH);
    }

    @Test
    void downloadZipballArchive_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(ZIPBALL_PATH, ARCHIVE_LOCATION.toString(), FOUND.code());

        final URI location;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            location = api.api().downloadZipballArchive(OWNER, REPO, REF);
        }

        assertThat(location).isEqualTo(ARCHIVE_LOCATION);
        HTTP.verifyRequested(GET, ZIPBALL_PATH);
    }

    @Test
    void downloadZipballArchive_shouldWriteFileWithoutAuthorization_whenArchiveIsOnAnotherOrigin() throws Exception {
        HTTP.stubRedirect(ZIPBALL_PATH, CODELOAD.uri("/zip/main").toString(), FOUND.code());
        CODELOAD.stub(GET, "/zip/main", status(OK).body(new byte[] {80, 75, 3, 4}));
        final Path target = tempDir.resolve("main.zip");

        final Path result;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            result = api.api().downloadZipballArchive(OWNER, REPO, REF, target);
        }

        assertThat(result).isEqualTo(target);
        assertThat(Files.readAllBytes(target)).containsExactly(80, 75, 3, 4);
        HTTP.verifyHeader(GET, ZIPBALL_PATH, AUTHORIZATION, "Bearer gh-token");
        CODELOAD.verifyRequested(GET, "/zip/main");
        CODELOAD.verifyNoHeader(GET, "/zip/main", AUTHORIZATION);
    }

    @Test
    void downloadTarballArchive_shouldWriteStream_whenArchiveIsOnAnotherOrigin() throws Exception {
        HTTP.stubRedirect(TARBALL_PATH, CODELOAD.uri("/tar/main").toString(), FOUND.code());
        CODELOAD.stub(GET, "/tar/main", status(OK).body(new byte[] {1, 2, 3}));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final long written;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            written = api.api().downloadTarballArchive(OWNER, REPO, REF, out);
        }

        assertThat(written).isEqualTo(3);
        assertThat(out.toByteArray()).containsExactly(1, 2, 3);
        CODELOAD.verifyNoHeader(GET, "/tar/main", AUTHORIZATION);
    }

    @Test
    void downloadZipballArchive_shouldThrowAndCreateNoFile_whenRepositoryIsMissing() throws Exception {
        HTTP.stub(
                GET,
                ZIPBALL_PATH,
                status(NOT_FOUND).body("{\"message\":\"Not Found\"}".getBytes(StandardCharsets.UTF_8)));
        final Path target = tempDir.resolve("main.zip");

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            thrown = catchThrowable(() -> api.api().downloadZipballArchive(OWNER, REPO, REF, target));
        }

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        assertThat(target).doesNotExist();
    }

    @Test
    void downloadTarballArchive_shouldWriteFile_whenArchiveIsOnAnotherOrigin() throws Exception {
        HTTP.stubRedirect(TARBALL_PATH, CODELOAD.uri("/tar/main").toString(), FOUND.code());
        CODELOAD.stub(GET, "/tar/main", status(OK).body(new byte[] {1, 2, 3}));
        final Path target = tempDir.resolve("main.tar.gz");

        final Path result;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            result = api.api().downloadTarballArchive(OWNER, REPO, REF, target);
        }

        assertThat(result).isEqualTo(target);
        assertThat(Files.readAllBytes(target)).containsExactly(1, 2, 3);
    }

    @Test
    void downloadZipballArchive_shouldWriteStream_whenArchiveIsOnAnotherOrigin() throws Exception {
        HTTP.stubRedirect(ZIPBALL_PATH, CODELOAD.uri("/zip/main").toString(), FOUND.code());
        CODELOAD.stub(GET, "/zip/main", status(OK).body(new byte[] {80, 75}));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final long written;
        try (final GitHubApiWireMockSupport.ReposContentsApiClient api = reposContentsApi(HTTP)) {
            written = api.api().downloadZipballArchive(OWNER, REPO, REF, out);
        }

        assertThat(written).isEqualTo(2);
        assertThat(out.toByteArray()).containsExactly(80, 75);
    }
}
