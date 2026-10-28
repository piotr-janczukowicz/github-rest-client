package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubMigrationsSourceImportsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "socm";
    private static final int AUTHOR_ID = 2_268_557;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMigrationsSourceImportsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getImportStatus_shouldReturnImport_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), importJson()));

        final Optional<GitHubImport> importStatus = callApi(api -> api.getImportStatus(OWNER, REPO));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(importStatus).contains(MigrationsCatalogFixtures.githubImport());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/socm/import");
        });
    }

    @Test
    void getImportStatus_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubImport> importStatus = callApi(api -> api.getImportStatus(OWNER, REPO));

        assertThat(importStatus).isEmpty();
    }

    @Test
    void startImport_shouldPutPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), importJson()));
        final GitHubStartImportPayload payload = MigrationsCatalogFixtures.startImportPayload();

        callApi(api -> api.startImport(OWNER, REPO, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/socm/import");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"vcs_url\":\"http://svn.example.com/svn/myproject\"");
        });
    }

    @Test
    void startImport_shouldThrow_whenPayloadNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsSourceImportsApi api =
                    new GitHubMigrationsSourceImportsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.startImport(OWNER, REPO, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void updateImport_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), importJson()));
        final GitHubUpdateImportPayload payload = MigrationsCatalogFixtures.updateImportPayload();

        callApi(api -> api.updateImport(OWNER, REPO, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/socm/import");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"vcs_username\":\"octocat\"");
        });
    }

    @Test
    void updateImport_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), importJson()));

        callApi(api -> api.updateImport(OWNER, REPO, null));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void cancelImport_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.cancelImport(OWNER, REPO));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/socm/import");
        });
    }

    @Test
    void getCommitAuthors_shouldReturnAuthors_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), porterAuthorItemsJson()));

        final List<GitHubPorterAuthor> authors = callApi(api -> api.getCommitAuthors(OWNER, REPO));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(authors).containsExactlyElementsOf(MigrationsCatalogFixtures.porterAuthorItems());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/socm/import/authors");
        });
    }

    @Test
    void getCommitAuthors_shouldSendSinceQuery_whenSinceProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), porterAuthorItemsJson()));

        callApi(api -> api.getCommitAuthors(OWNER, REPO, 1));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/repos/octocat/socm/import/authors");
            softly.assertThat(request.uri()).hasParameter("since", "1");
        });
    }

    @Test
    void getCommitAuthors_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> callApi(api -> api.getCommitAuthors(OWNER, REPO)));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void mapCommitAuthor_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), porterAuthorJson()));
        final GitHubMapCommitAuthorPayload payload = MigrationsCatalogFixtures.mapCommitAuthorPayload();

        callApi(api -> api.mapCommitAuthor(OWNER, REPO, AUTHOR_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/socm/import/authors/2268557");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"email\":\"hubot@github.com\"");
        });
    }

    @Test
    void mapCommitAuthor_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), porterAuthorJson()));

        callApi(api -> api.mapCommitAuthor(OWNER, REPO, AUTHOR_ID, null));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void mapCommitAuthor_shouldPutLongIdInPath_whenIdExceedsIntRange() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), porterAuthorJson()));

        callApi(api ->
                api.mapCommitAuthor(OWNER, REPO, 3_000_000_000L, MigrationsCatalogFixtures.mapCommitAuthorPayload()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/socm/import/authors/3000000000");
    }

    @Test
    void getLargeFiles_shouldReturnFiles_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), porterLargeFileItemsJson()));

        final List<GitHubPorterLargeFile> largeFiles = callApi(api -> api.getLargeFiles(OWNER, REPO));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(largeFiles).containsExactlyElementsOf(MigrationsCatalogFixtures.porterLargeFileItems());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/socm/import/large_files");
        });
    }

    @Test
    void setLfsPreference_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), importJson()));
        final GitHubSetLfsPreferencePayload payload = MigrationsCatalogFixtures.setLfsPreferencePayload();

        callApi(api -> api.setLfsPreference(OWNER, REPO, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/socm/import/lfs");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"use_lfs\":\"opt_in\"");
        });
    }

    @Test
    void setLfsPreference_shouldThrow_whenPayloadNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsSourceImportsApi api =
                    new GitHubMigrationsSourceImportsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.setLfsPreference(OWNER, REPO, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @FunctionalInterface
    private interface SourceImportsApiAction<T> {
        T apply(GitHubMigrationsSourceImportsApi api) throws Exception;
    }

    @FunctionalInterface
    private interface SourceImportsApiVoid {
        void accept(GitHubMigrationsSourceImportsApi api) throws Exception;
    }

    private <T> T callApi(final SourceImportsApiAction<T> action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubMigrationsSourceImportsApi(TestClients.caller(client)));
        }
    }

    private void runApi(final SourceImportsApiVoid action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            action.accept(new GitHubMigrationsSourceImportsApi(TestClients.caller(client)));
        }
    }

    private static String importJson() {
        return FileUtils.readString(GitHubImport.class, "import.json");
    }

    private static String porterAuthorJson() {
        return FileUtils.readString(GitHubPorterAuthor.class, "porter-author.json");
    }

    private static String porterAuthorItemsJson() {
        return FileUtils.readString(GitHubMigrationsSourceImportsApiTest.class, "porter-author-items.json");
    }

    private static String porterLargeFileItemsJson() {
        return FileUtils.readString(GitHubMigrationsSourceImportsApiTest.class, "porter-large-file-items.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
