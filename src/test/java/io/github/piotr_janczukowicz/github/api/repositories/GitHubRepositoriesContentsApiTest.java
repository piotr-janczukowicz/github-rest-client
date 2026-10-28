package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LOCATION;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubRepositoriesContentsApiTest {

    private static final URI ARCHIVE_LOCATION =
            URI.create("https://codeload.github.com/octocat/Hello-World/legacy.tar.gz/main");
    private static final URI CONTENT_LOCATION =
            URI.create("https://raw.githubusercontent.com/octocat/Hello-World/main/README.md");
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String PATH = "README.md";
    private static final String REF = "main";
    private static final String DIR = "docs";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesContentsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getContent_shouldReturnFile_whenResponseIsFile() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("content-file.json")));

        final Optional<GitHubContentResult> content = api().getContent(OWNER, REPO, PATH);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(content).contains(RepositoriesCatalogFixtures.contentFile());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/contents/README.md");
        });
    }

    @Test
    void getRootContent_shouldReturnDirectory_whenResponseIsArray() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("content-directory.json")));

        final Optional<GitHubContentResult> content = api().getRootContent(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(content).contains(RepositoriesCatalogFixtures.contentDirectory());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/contents");
        });
    }

    @Test
    void getRootContent_shouldSendRefFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("content-directory.json")));
        final GitHubGetContentFilter filter =
                GitHubGetContentFilterBuilder.builder().ref(REF).build();

        api().getRootContent(OWNER, REPO, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/contents?ref=main");
    }

    @Test
    void getRootContent_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubContentResult> content = api().getRootContent(OWNER, REPO);

        assertThat(content).isEmpty();
    }

    @Test
    void getContent_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("content-file.json")));

        api().getContent("a/b", REPO, PATH);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getRawPath()).isEqualTo("/repos/a%2Fb/Hello-World/contents/README.md");
    }

    @Test
    void getContent_shouldReturnDirectory_whenResponseIsArray() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("content-directory.json")));

        final Optional<GitHubContentResult> content = api().getContent(OWNER, REPO, PATH);

        assertThat(content).contains(RepositoriesCatalogFixtures.contentDirectory());
    }

    @Test
    void getContent_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubContentResult> content = api().getContent(OWNER, REPO, PATH);

        assertThat(content).isEmpty();
    }

    @Test
    void getContent_shouldSendRefFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("content-file.json")));
        final GitHubGetContentFilter filter =
                GitHubGetContentFilterBuilder.builder().ref(REF).build();

        api().getContent(OWNER, REPO, PATH, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/repos/octocat/Hello-World/contents/README.md?ref=main");
    }

    @Test
    void getContentRedirectLocation_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), CONTENT_LOCATION.toString()));

        final Optional<URI> location = api().getContentRedirectLocation(OWNER, REPO, PATH);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(location).contains(CONTENT_LOCATION);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/contents/README.md");
        });
    }

    @Test
    void getContentRedirectLocation_shouldReturnEmpty_whenContentIsSentDirectly() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "{}"));

        final Optional<URI> location = api().getContentRedirectLocation(OWNER, REPO, PATH);

        assertThat(location).isEmpty();
    }

    @Test
    void getContentRedirectLocation_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubRepositoriesContentsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getContentRedirectLocation(OWNER, REPO, PATH));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getContentRedirectLocation_shouldSendRefFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), CONTENT_LOCATION.toString()));
        final GitHubGetContentFilter filter =
                GitHubGetContentFilterBuilder.builder().ref(REF).build();

        api().getContentRedirectLocation(OWNER, REPO, PATH, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/repos/octocat/Hello-World/contents/README.md?ref=main");
    }

    @Test
    void createOrUpdateFileContents_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("file-commit.json")));
        final GitHubCreateOrUpdateFileContentsPayload payload =
                RepositoriesCatalogFixtures.createOrUpdateFileContentsPayload();

        final GitHubFileCommit fileCommit = api().createOrUpdateFileContents(OWNER, REPO, PATH, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(fileCommit).isEqualTo(RepositoriesCatalogFixtures.fileCommit());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/contents/README.md");
        });
    }

    @Test
    void deleteFile_shouldDeleteWithPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("file-commit.json")));
        final GitHubDeleteFilePayload payload = RepositoriesCatalogFixtures.deleteFilePayload();

        final GitHubFileCommit fileCommit = api().deleteFile(OWNER, REPO, PATH, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(fileCommit).isEqualTo(RepositoriesCatalogFixtures.fileCommit());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/contents/README.md");
        });
    }

    @Test
    void getReadme_shouldReturnFile_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("content-file.json")));

        final Optional<GitHubContentFile> readme = api().getReadme(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(readme).contains(RepositoriesCatalogFixtures.contentFile());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/readme");
        });
    }

    @Test
    void getReadmeInDirectory_shouldReturnFile_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("content-file.json")));

        final Optional<GitHubContentFile> readme = api().getReadmeInDirectory(OWNER, REPO, DIR);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(readme).contains(RepositoriesCatalogFixtures.contentFile());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/readme/docs");
        });
    }

    @Test
    void downloadTarballArchive_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), ARCHIVE_LOCATION.toString()));

        final URI location = api().downloadTarballArchive(OWNER, REPO, REF);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(location).isEqualTo(ARCHIVE_LOCATION);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/tarball/main");
        });
    }

    @Test
    void downloadZipballArchive_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), ARCHIVE_LOCATION.toString()));

        final URI location = api().downloadZipballArchive(OWNER, REPO, REF);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(location).isEqualTo(ARCHIVE_LOCATION);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/zipball/main");
        });
    }

    private GitHubRepositoriesContentsApi api() {
        return new GitHubRepositoriesContentsApi(TestClients.caller(transport));
    }

    private static HttpResponse redirectResponse(final int statusCode, final String location) {
        return new HttpResponse(statusCode, HttpHeaders.of(Map.of(LOCATION, location)), HttpBody.absent());
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesContentsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
