package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.AUTHORIZATION;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpStatus.FOUND;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.forbidden;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.migrationsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.client.exception.GitHubForbiddenException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

@IntegrationTest
class GitHubMigrationsUsersApiIntegrationTest {

    private static final JsonType<List<GitHubMigration>> MIGRATIONS_TYPE = JsonTypes.listOf(GitHubMigration.class);
    private static final JsonType<List<GitHubSimpleRepository>> REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubSimpleRepository.class);

    private static final URI ARCHIVE_LOCATION =
            URI.create("https://codeload.github.com/github/Hello-World/legacy.tar.gz/main");
    private static final long MIGRATION_ID = 79L;
    private static final String REPO_NAME = "Hello-World";
    private static final String MIGRATIONS_PATH = "/user/migrations";
    private static final String MIGRATION_PATH = "/user/migrations/79";
    private static final String ARCHIVE_PATH = "/user/migrations/79/archive";
    private static final String LOCK_PATH = "/user/migrations/79/repos/Hello-World/lock";
    private static final String REPOSITORIES_PATH = "/user/migrations/79/repositories";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @RegisterExtension
    static final StubHttpServer ARCHIVE_HOST = new StubHttpServer("localhost");

    @TempDir
    Path tempDir;

    @Test
    void listUserMigrations_shouldReturnMigrations_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MIGRATIONS_PATH, okJson(listJsonBytes(GitHubMigration.class, "migration.json")));

        final GitHubPage<GitHubMigration> page;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            page = migrations.api().users().listUserMigrations();
        }

        final List<GitHubMigration> expected =
                CODEC.decode(listJsonBytes(GitHubMigration.class, "migration.json"), MIGRATIONS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, MIGRATIONS_PATH);
    }

    @Test
    void startUserMigration_shouldReturnMigration_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, MIGRATIONS_PATH, createdJson(jsonBytes(GitHubMigration.class, "migration.json")));
        final GitHubStartForAuthenticatedUserPayload payload =
                MigrationsCatalogFixtures.startForAuthenticatedUserPayload();

        final GitHubMigration migration;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            migration = migrations.api().users().startUserMigration(payload);
        }

        final GitHubMigration expected =
                CODEC.decode(jsonBytes(GitHubMigration.class, "migration.json"), GitHubMigration.class);

        assertThat(migration).isEqualTo(expected);
        HTTP.verifyRequested(POST, MIGRATIONS_PATH);
    }

    @Test
    void getUserMigrationStatus_shouldReturnMigration_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MIGRATION_PATH, okJson(jsonBytes(GitHubMigration.class, "migration.json")));

        final Optional<GitHubMigration> migration;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            migration = migrations.api().users().getUserMigrationStatus(MIGRATION_ID);
        }

        final GitHubMigration expected =
                CODEC.decode(jsonBytes(GitHubMigration.class, "migration.json"), GitHubMigration.class);

        assertThat(migration).contains(expected);
        HTTP.verifyRequested(GET, MIGRATION_PATH);
    }

    @Test
    void getUserMigrationStatus_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, MIGRATION_PATH, notFound());

        final Optional<GitHubMigration> migration;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            migration = migrations.api().users().getUserMigrationStatus(MIGRATION_ID);
        }

        assertThat(migration).isEmpty();
        HTTP.verifyRequested(GET, MIGRATION_PATH);
    }

    @Test
    void downloadUserMigrationArchive_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(ARCHIVE_PATH, ARCHIVE_LOCATION.toString(), FOUND.code());

        final URI location;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            location = migrations.api().users().downloadUserMigrationArchive(MIGRATION_ID);
        }

        assertThat(location).isEqualTo(ARCHIVE_LOCATION);
        HTTP.verifyRequested(GET, ARCHIVE_PATH);
    }

    @Test
    void downloadUserMigrationArchive_shouldWriteFileWithoutAuthorization_whenArchiveIsOnAnotherOrigin()
            throws Exception {
        HTTP.stubRedirect(ARCHIVE_PATH, ARCHIVE_HOST.uri("/archive.tar.gz").toString(), FOUND.code());
        ARCHIVE_HOST.stub(GET, "/archive.tar.gz", status(OK).body(new byte[] {31, -117, 8}));
        final Path target = tempDir.resolve("migration.tar.gz");

        final Path result;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            result = migrations.api().users().downloadUserMigrationArchive(MIGRATION_ID, target);
        }

        assertThat(result).isEqualTo(target);
        assertThat(Files.readAllBytes(target)).containsExactly(31, -117, 8);
        HTTP.verifyHeader(GET, ARCHIVE_PATH, AUTHORIZATION, "Bearer gh-token");
        ARCHIVE_HOST.verifyNoHeader(GET, "/archive.tar.gz", AUTHORIZATION);
    }

    @Test
    void downloadUserMigrationArchive_shouldWriteStream_whenArchiveIsOnAnotherOrigin() throws Exception {
        HTTP.stubRedirect(ARCHIVE_PATH, ARCHIVE_HOST.uri("/archive.tar.gz").toString(), FOUND.code());
        ARCHIVE_HOST.stub(GET, "/archive.tar.gz", status(OK).body(new byte[] {31, -117}));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final long written;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            written = migrations.api().users().downloadUserMigrationArchive(MIGRATION_ID, out);
        }

        assertThat(written).isEqualTo(2);
        assertThat(out.toByteArray()).containsExactly(31, -117);
    }

    @Test
    void downloadUserMigrationArchive_shouldThrow_whenResponseIsForbidden() throws Exception {
        HTTP.stub(GET, ARCHIVE_PATH, forbidden());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            final GitHubMigrationsUsersApi users = migrations.api().users();

            thrown = catchThrowable(() -> users.downloadUserMigrationArchive(MIGRATION_ID));
        }

        assertThat(thrown).isInstanceOf(GitHubForbiddenException.class);
        HTTP.verifyRequested(GET, ARCHIVE_PATH);
    }

    @Test
    void deleteUserMigrationArchive_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ARCHIVE_PATH, noContent());

        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            migrations.api().users().deleteUserMigrationArchive(MIGRATION_ID);
        }

        HTTP.verifyRequested(DELETE, ARCHIVE_PATH);
    }

    @Test
    void unlockUserRepo_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, LOCK_PATH, noContent());

        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            migrations.api().users().unlockUserRepo(MIGRATION_ID, REPO_NAME);
        }

        HTTP.verifyRequested(DELETE, LOCK_PATH);
    }

    @Test
    void listUserMigrationRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, REPOSITORIES_PATH, okJson(listJsonBytes(GitHubSimpleRepository.class, "simple-repository.json")));

        final GitHubPage<GitHubSimpleRepository> page;
        try (final GitHubApiWireMockSupport.MigrationsApiClient migrations = migrationsApi(HTTP)) {
            page = migrations.api().users().listUserMigrationRepositories(MIGRATION_ID);
        }

        final List<GitHubSimpleRepository> expected =
                CODEC.decode(listJsonBytes(GitHubSimpleRepository.class, "simple-repository.json"), REPOSITORIES_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, REPOSITORIES_PATH);
    }
}
