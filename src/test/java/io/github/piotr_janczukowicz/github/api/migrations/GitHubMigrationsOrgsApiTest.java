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
class GitHubMigrationsOrgsApiTest {

    private static final URI ARCHIVE_LOCATION =
            URI.create("https://codeload.github.com/github/Hello-World/legacy.tar.gz/main");
    private static final String ORG = "github";
    private static final long MIGRATION_ID = 79L;
    private static final String REPO_NAME = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMigrationsOrgsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgMigrations_shouldReturnMigrations_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationsJson()));

        final GitHubPage<GitHubMigration> page = callApi(api -> api.listOrgMigrations(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(MigrationsCatalogFixtures.migration());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/migrations?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listOrgMigrations_shouldSendExcludeQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationsJson()));

        callApi(api -> api.listOrgMigrations(ORG, repositoriesExcludeFilter()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/github/migrations");
            softly.assertThat(request.uri()).hasParameter("exclude", "repositories");
        });
    }

    @Test
    void listOrgMigrations_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationsJson()));

        callApi(api -> api.listOrgMigrations(ORG, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/migrations");
        });
    }

    @Test
    void startOrgMigration_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), migrationJson()));
        final GitHubStartForOrgPayload payload = MigrationsCatalogFixtures.startForOrgPayload();

        callApi(api -> api.startOrgMigration(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/migrations");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"repositories\":[\"Hello-World\"]");
        });
    }

    @Test
    void getOrgMigrationStatus_shouldReturnMigration_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationJson()));

        final Optional<GitHubMigration> migration = callApi(api -> api.getOrgMigrationStatus(ORG, MIGRATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(migration).contains(MigrationsCatalogFixtures.migration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/migrations/79");
        });
    }

    @Test
    void getOrgMigrationStatus_shouldSendExcludeQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), migrationJson()));

        callApi(api -> api.getOrgMigrationStatus(ORG, MIGRATION_ID, repositoriesExcludeFilter()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/github/migrations/79");
            softly.assertThat(request.uri()).hasParameter("exclude", "repositories");
        });
    }

    @Test
    void getOrgMigrationStatus_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubMigration> migration = callApi(api -> api.getOrgMigrationStatus(ORG, MIGRATION_ID));

        assertThat(migration).isEmpty();
    }

    @Test
    void downloadOrgMigrationArchive_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), ARCHIVE_LOCATION.toString()));

        final URI location = callApi(api -> api.downloadOrgMigrationArchive(ORG, MIGRATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(location).isEqualTo(ARCHIVE_LOCATION);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/migrations/79/archive");
        });
    }

    @Test
    void deleteOrgMigrationArchive_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.deleteOrgMigrationArchive(ORG, MIGRATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/migrations/79/archive");
        });
    }

    @Test
    void unlockOrgRepo_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.unlockOrgRepo(ORG, MIGRATION_ID, REPO_NAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/migrations/79/repos/Hello-World/lock");
        });
    }

    @Test
    void listOrgMigrationRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), repositoriesJson()));

        final GitHubPage<GitHubSimpleRepository> page =
                callApi(api -> api.listOrgMigrationRepositories(ORG, MIGRATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldSimpleRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/migrations/79/repositories?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @FunctionalInterface
    private interface OrgsApiAction<T> {
        T apply(GitHubMigrationsOrgsApi api) throws Exception;
    }

    @FunctionalInterface
    private interface OrgsApiVoid {
        void accept(GitHubMigrationsOrgsApi api) throws Exception;
    }

    private <T> T callApi(final OrgsApiAction<T> action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubMigrationsOrgsApi(TestClients.caller(client)));
        }
    }

    private void runApi(final OrgsApiVoid action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            action.accept(new GitHubMigrationsOrgsApi(TestClients.caller(client)));
        }
    }

    private static GitHubListForOrgFilter repositoriesExcludeFilter() {
        return GitHubListForOrgFilterBuilder.builder()
                .exclude(List.of(GitHubMigrationExclude.REPOSITORIES))
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
