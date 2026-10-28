package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.migrationsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubMigrationsSourceImportsApiIntegrationTest {

    private static final JsonType<List<GitHubPorterAuthor>> AUTHORS_TYPE = JsonTypes.listOf(GitHubPorterAuthor.class);
    private static final JsonType<List<GitHubPorterLargeFile>> LARGE_FILES_TYPE =
            JsonTypes.listOf(GitHubPorterLargeFile.class);

    private static final String OWNER = "octocat";
    private static final String REPO = "socm";
    private static final int AUTHOR_ID = 2_268_557;
    private static final String IMPORT_PATH = "/repos/octocat/socm/import";
    private static final String AUTHORS_PATH = "/repos/octocat/socm/import/authors";
    private static final String AUTHOR_PATH = "/repos/octocat/socm/import/authors/2268557";
    private static final String LARGE_FILES_PATH = "/repos/octocat/socm/import/large_files";
    private static final String LFS_PATH = "/repos/octocat/socm/import/lfs";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getImportStatus_shouldReturnImport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, IMPORT_PATH, okJson(jsonBytes(GitHubImport.class, "import.json")));

        final Optional<GitHubImport> importStatus;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            importStatus = migrations.api().sourceImports().getImportStatus(OWNER, REPO);
        }

        final GitHubImport expected = CODEC.decode(jsonBytes(GitHubImport.class, "import.json"), GitHubImport.class);

        assertThat(importStatus).contains(expected);
        HTTP.verifyRequested(GET, IMPORT_PATH);
    }

    @Test
    void getImportStatus_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, IMPORT_PATH, notFound());

        final Optional<GitHubImport> importStatus;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            importStatus = migrations.api().sourceImports().getImportStatus(OWNER, REPO);
        }

        assertThat(importStatus).isEmpty();
        HTTP.verifyRequested(GET, IMPORT_PATH);
    }

    @Test
    void startImport_shouldReturnImport_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, IMPORT_PATH, createdJson(jsonBytes(GitHubImport.class, "import.json")));
        final GitHubStartImportPayload payload = MigrationsCatalogFixtures.startImportPayload();

        final GitHubImport importStatus;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            importStatus = migrations.api().sourceImports().startImport(OWNER, REPO, payload);
        }

        final GitHubImport expected = CODEC.decode(jsonBytes(GitHubImport.class, "import.json"), GitHubImport.class);

        assertThat(importStatus).isEqualTo(expected);
        HTTP.verifyRequested(PUT, IMPORT_PATH);
    }

    @Test
    void updateImport_shouldReturnImport_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, IMPORT_PATH, okJson(jsonBytes(GitHubImport.class, "import.json")));
        final GitHubUpdateImportPayload payload = MigrationsCatalogFixtures.updateImportPayload();

        final GitHubImport importStatus;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            importStatus = migrations.api().sourceImports().updateImport(OWNER, REPO, payload);
        }

        final GitHubImport expected = CODEC.decode(jsonBytes(GitHubImport.class, "import.json"), GitHubImport.class);

        assertThat(importStatus).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, IMPORT_PATH);
    }

    @Test
    void updateImport_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(PATCH, IMPORT_PATH, okJson(jsonBytes(GitHubImport.class, "import.json")));

        final GitHubImport importStatus;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            importStatus = migrations.api().sourceImports().updateImport(OWNER, REPO, null);
        }

        final GitHubImport expected = CODEC.decode(jsonBytes(GitHubImport.class, "import.json"), GitHubImport.class);

        assertThat(importStatus).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, IMPORT_PATH);
        HTTP.verifyAbsentBody(PATCH, IMPORT_PATH);
    }

    @Test
    void cancelImport_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, IMPORT_PATH, noContent());

        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            migrations.api().sourceImports().cancelImport(OWNER, REPO);
        }

        HTTP.verifyRequested(DELETE, IMPORT_PATH);
    }

    @Test
    void getCommitAuthors_shouldReturnAuthors_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                AUTHORS_PATH,
                okJson(jsonBytes(GitHubMigrationsSourceImportsApiIntegrationTest.class, "porter-author-items.json")));

        final List<GitHubPorterAuthor> authors;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            authors = migrations.api().sourceImports().getCommitAuthors(OWNER, REPO);
        }

        final List<GitHubPorterAuthor> expected = CODEC.decode(
                jsonBytes(GitHubMigrationsSourceImportsApiIntegrationTest.class, "porter-author-items.json"),
                AUTHORS_TYPE);

        assertThat(authors).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, AUTHORS_PATH);
    }

    @Test
    void getCommitAuthors_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, AUTHORS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            final GitHubMigrationsSourceImportsApi sourceImports =
                    migrations.api().sourceImports();

            thrown = catchThrowable(() -> sourceImports.getCommitAuthors(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, AUTHORS_PATH);
    }

    @Test
    void mapCommitAuthor_shouldReturnAuthor_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, AUTHOR_PATH, okJson(jsonBytes(GitHubPorterAuthor.class, "porter-author.json")));
        final GitHubMapCommitAuthorPayload payload = MigrationsCatalogFixtures.mapCommitAuthorPayload();

        final GitHubPorterAuthor author;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            author = migrations.api().sourceImports().mapCommitAuthor(OWNER, REPO, AUTHOR_ID, payload);
        }

        final GitHubPorterAuthor expected =
                CODEC.decode(jsonBytes(GitHubPorterAuthor.class, "porter-author.json"), GitHubPorterAuthor.class);

        assertThat(author).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, AUTHOR_PATH);
    }

    @Test
    void mapCommitAuthor_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(PATCH, AUTHOR_PATH, okJson(jsonBytes(GitHubPorterAuthor.class, "porter-author.json")));

        final GitHubPorterAuthor author;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            author = migrations.api().sourceImports().mapCommitAuthor(OWNER, REPO, AUTHOR_ID, null);
        }

        final GitHubPorterAuthor expected =
                CODEC.decode(jsonBytes(GitHubPorterAuthor.class, "porter-author.json"), GitHubPorterAuthor.class);

        assertThat(author).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, AUTHOR_PATH);
        HTTP.verifyAbsentBody(PATCH, AUTHOR_PATH);
    }

    @Test
    void getLargeFiles_shouldReturnFiles_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LARGE_FILES_PATH,
                okJson(jsonBytes(
                        GitHubMigrationsSourceImportsApiIntegrationTest.class, "porter-large-file-items.json")));

        final List<GitHubPorterLargeFile> largeFiles;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            largeFiles = migrations.api().sourceImports().getLargeFiles(OWNER, REPO);
        }

        final List<GitHubPorterLargeFile> expected = CODEC.decode(
                jsonBytes(GitHubMigrationsSourceImportsApiIntegrationTest.class, "porter-large-file-items.json"),
                LARGE_FILES_TYPE);

        assertThat(largeFiles).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, LARGE_FILES_PATH);
    }

    @Test
    void setLfsPreference_shouldReturnImport_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, LFS_PATH, okJson(jsonBytes(GitHubImport.class, "import.json")));
        final GitHubSetLfsPreferencePayload payload = MigrationsCatalogFixtures.setLfsPreferencePayload();

        final GitHubImport importStatus;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            importStatus = migrations.api().sourceImports().setLfsPreference(OWNER, REPO, payload);
        }

        final GitHubImport expected = CODEC.decode(jsonBytes(GitHubImport.class, "import.json"), GitHubImport.class);

        assertThat(importStatus).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, LFS_PATH);
    }
}
