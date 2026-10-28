package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LOCATION;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubForbiddenException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubMigrationsUsersApiTest {

    private static final URI ARCHIVE_LOCATION =
            URI.create("https://codeload.github.com/github/Hello-World/legacy.tar.gz/main");
    private static final long MIGRATION_ID = 79L;
    private static final String REPO_NAME = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMigrationsUsersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listUserMigrations_shouldReturnMigrations_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationsJson()));

        final GitHubPage<GitHubMigration> page = callApi(GitHubMigrationsUsersApi::listUserMigrations);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(MigrationsCatalogFixtures.migration());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/migrations?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listUserMigrations_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationsJson()));

        callApi(api -> api.listUserMigrations(GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/migrations");
        });
    }

    @Test
    void listUserMigrations_shouldThrow_whenRequestNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsUsersApi api = new GitHubMigrationsUsersApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.listUserMigrations(null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @Test
    void startUserMigration_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), migrationJson()));
        final GitHubStartForAuthenticatedUserPayload payload =
                MigrationsCatalogFixtures.startForAuthenticatedUserPayload();

        final GitHubMigration expected = MigrationsCatalogFixtures.migration();

        final GitHubMigration created = callApi(api -> api.startUserMigration(payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(created).isEqualTo(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/migrations");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"repositories\":[\"Hello-World\"]");
        });
    }

    @Test
    void startUserMigration_shouldThrow_whenPayloadNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsUsersApi api = new GitHubMigrationsUsersApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.startUserMigration(null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void getUserMigrationStatus_shouldReturnMigration_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationJson()));

        final Optional<GitHubMigration> migration = callApi(api -> api.getUserMigrationStatus(MIGRATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(migration).contains(MigrationsCatalogFixtures.migration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/migrations/79");
        });
    }

    @Test
    void getUserMigrationStatus_shouldSendExcludeQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationJson()));

        callApi(api -> api.getUserMigrationStatus(MIGRATION_ID, repositoriesExcludeFilter()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/user/migrations/79");
            softly.assertThat(request.uri()).hasParameter("exclude", "repositories");
        });
    }

    @Test
    void getUserMigrationStatus_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubMigration> migration = callApi(api -> api.getUserMigrationStatus(MIGRATION_ID));

        assertThat(migration).isEmpty();
    }

    @Test
    void getUserMigrationStatus_shouldThrow_whenFilterNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsUsersApi api = new GitHubMigrationsUsersApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.getUserMigrationStatus(MIGRATION_ID, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
        }
    }

    @Test
    void downloadUserMigrationArchive_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), ARCHIVE_LOCATION.toString()));

        final URI location = callApi(api -> api.downloadUserMigrationArchive(MIGRATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(location).isEqualTo(ARCHIVE_LOCATION);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/migrations/79/archive");
        });
    }

    @Test
    void downloadUserMigrationArchive_shouldThrow_whenResponseIsForbidden() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.FORBIDDEN.code(), null));

        final Throwable thrown;
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsUsersApi api = new GitHubMigrationsUsersApi(TestClients.caller(client));
            thrown = catchThrowable(() -> api.downloadUserMigrationArchive(MIGRATION_ID));
        }

        assertThat(thrown).isInstanceOf(GitHubForbiddenException.class);
    }

    @Test
    void downloadUserMigrationArchive_shouldThrow_whenResponseIsNotModified() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_MODIFIED.code(), null));

        final Throwable thrown;
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsUsersApi api = new GitHubMigrationsUsersApi(TestClients.caller(client));
            thrown = catchThrowable(() -> api.downloadUserMigrationArchive(MIGRATION_ID));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubApiException.class);
    }

    @Test
    void deleteUserMigrationArchive_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.deleteUserMigrationArchive(MIGRATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/migrations/79/archive");
        });
    }

    @Test
    void unlockUserRepo_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.unlockUserRepo(MIGRATION_ID, REPO_NAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/migrations/79/repos/Hello-World/lock");
        });
    }

    @Test
    void unlockUserRepo_shouldThrow_whenRepoNameBlank() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsUsersApi api = new GitHubMigrationsUsersApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.unlockUserRepo(MIGRATION_ID, "  "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{repo_name}");
        }
    }

    @Test
    void listUserMigrationRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), repositoriesJson()));

        final GitHubPage<GitHubSimpleRepository> page = callApi(api -> api.listUserMigrationRepositories(MIGRATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldSimpleRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/migrations/79/repositories?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listUserMigrationRepositories_shouldThrow_whenRequestNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubMigrationsUsersApi api = new GitHubMigrationsUsersApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.listUserMigrationRepositories(MIGRATION_ID, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @FunctionalInterface
    private interface UsersApiAction<T> {
        T apply(GitHubMigrationsUsersApi api) throws Exception;
    }

    @FunctionalInterface
    private interface UsersApiVoid {
        void accept(GitHubMigrationsUsersApi api) throws Exception;
    }

    private <T> T callApi(final UsersApiAction<T> action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubMigrationsUsersApi(TestClients.caller(client)));
        }
    }

    private void runApi(final UsersApiVoid action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            action.accept(new GitHubMigrationsUsersApi(TestClients.caller(client)));
        }
    }

    private static GitHubGetStatusForAuthenticatedUserFilter repositoriesExcludeFilter() {
        return GitHubGetStatusForAuthenticatedUserFilterBuilder.builder()
                .exclude(List.of("repositories"))
                .build();
    }

    private static HttpResponse redirectResponse(final int statusCode, final String location) {
        return new HttpResponse(statusCode, HttpHeaders.of(Map.of(LOCATION, location)), HttpBody.absent());
    }

    private static String migrationJson() {
        return FileUtils.readString(GitHubMigration.class, "migration.json");
    }

    private static String migrationsJson() {
        return "[" + migrationJson() + "]";
    }

    private static String repositoriesJson() {
        return "[" + FileUtils.readString(GitHubSimpleRepository.class, "simple-repository.json") + "]";
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
