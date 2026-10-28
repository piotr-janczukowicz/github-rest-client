package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub authenticated-user migrations REST resource for API version {@code 2026-03-10}. */
public final class GitHubMigrationsUsersApi extends GitHubResourceApi {

    private static final PathTemplate MIGRATIONS = PathTemplate.of("/user/migrations");
    private static final PathTemplate MIGRATION = PathTemplate.of("/user/migrations/{migration_id}");
    private static final PathTemplate MIGRATION_ARCHIVE = PathTemplate.of("/user/migrations/{migration_id}/archive");
    private static final PathTemplate MIGRATION_REPO_LOCK =
            PathTemplate.of("/user/migrations/{migration_id}/repos/{repo_name}/lock");
    private static final PathTemplate MIGRATION_REPOSITORIES =
            PathTemplate.of("/user/migrations/{migration_id}/repositories");

    private static final JsonType<List<GitHubMigration>> MIGRATIONS_TYPE = JsonTypes.listOf(GitHubMigration.class);
    private static final JsonType<List<GitHubSimpleRepository>> REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubSimpleRepository.class);

    /**
     * Authenticated-user migrations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMigrationsUsersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List authenticated-user migrations ({@code GET /user/migrations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listUserMigrations(GitHubPageRequest)}.
     *
     * @return first HTTP page of migrations
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMigration> listUserMigrations() throws IOException {
        return listUserMigrations(GitHubPageRequest.byPage());
    }

    /**
     * List authenticated-user migrations ({@code GET /user/migrations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of migrations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMigration> listUserMigrations(final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(MIGRATIONS, Map.of(), request, MIGRATIONS_TYPE);
    }

    /**
     * Start an authenticated-user migration ({@code POST /user/migrations}).
     *
     * @param payload migration to start
     * @return created migration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubMigration startUserMigration(final GitHubStartForAuthenticatedUserPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(MIGRATIONS), payload), GitHubMigration.class);
    }

    /**
     * Get an authenticated-user migration status ({@code GET /user/migrations/{migration_id}}).
     *
     * @param migrationId migration id
     * @return migration status, or empty on HTTP 404
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubMigration> getUserMigrationStatus(final long migrationId) throws IOException {
        return getUserMigrationStatus(migrationId, GitHubGetStatusForAuthenticatedUserFilter.empty());
    }

    /**
     * Get an authenticated-user migration status ({@code GET /user/migrations/{migration_id}}).
     *
     * @param migrationId migration id
     * @param filter optional query filters
     * @return migration status, or empty on HTTP 404
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubMigration> getUserMigrationStatus(
            final long migrationId, final GitHubGetStatusForAuthenticatedUserFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(MIGRATION, migrationId).withQuery(filter.toQueryParameters()), GitHubMigration.class);
    }

    /**
     * Download an authenticated-user migration archive ({@code GET /user/migrations/{migration_id}/archive}).
     *
     * <p>Available on every flavor, unlike deleting the archive, which is limited to {@code CLOUD} or {@code GHEC}.
     *
     * @param migrationId migration id
     * @return redirect location URI
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadUserMigrationArchive(final long migrationId) throws IOException {
        return caller.redirectLocation(get(MIGRATION_ARCHIVE, migrationId));
    }

    /**
     * Download an authenticated-user migration archive into {@code target} instead of returning the redirect
     * ({@code GET /user/migrations/{migration_id}/archive}). The archive is streamed to a temporary file next to
     * {@code target} and moved onto it only when complete; the redirect to the download host is followed without the
     * {@code Authorization} header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema defines only the request
     * mapped by {@link #downloadUserMigrationArchive(long)}, which returns the redirect location. This method is a
     * helper on top of it that follows the redirect and saves the body, so callers need no HTTP client of their own.
     *
     * <p>Available on every flavor, unlike deleting the archive, which is limited to {@code CLOUD} or {@code GHEC}.
     *
     * @param migrationId migration id
     * @param target destination file; its directory must exist, an existing file is replaced
     * @return {@code target}
     * @throws NullPointerException if {@code target} is {@code null}
     * @throws IOException if the transport fails or the file cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Path downloadUserMigrationArchive(final long migrationId, final Path target) throws IOException {
        requireNonNull(target, "target");

        return caller.download(get(MIGRATION_ARCHIVE, migrationId), target);
    }

    /**
     * Download an authenticated-user migration archive into {@code out} instead of returning the redirect ({@code GET
     * /user/migrations/{migration_id}/archive}); {@code out} is not closed. The redirect to the download host is
     * followed without the {@code Authorization} header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema defines only the request
     * mapped by {@link #downloadUserMigrationArchive(long)}, which returns the redirect location. This method is a
     * helper on top of it that follows the redirect and saves the body, so callers need no HTTP client of their own.
     *
     * <p>Available on every flavor, unlike deleting the archive, which is limited to {@code CLOUD} or {@code GHEC}.
     *
     * @param migrationId migration id
     * @param out destination stream
     * @return number of bytes written
     * @throws NullPointerException if {@code out} is {@code null}
     * @throws IOException if the transport fails or {@code out} cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public long downloadUserMigrationArchive(final long migrationId, final OutputStream out) throws IOException {
        requireNonNull(out, "out");

        return caller.download(get(MIGRATION_ARCHIVE, migrationId), out);
    }

    /**
     * Delete an authenticated-user migration archive ({@code DELETE /user/migrations/{migration_id}/archive}).
     *
     * @param migrationId migration id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteUserMigrationArchive(final long migrationId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(MIGRATION_ARCHIVE, migrationId));
    }

    /**
     * Unlock an authenticated-user repository ({@code DELETE /user/migrations/{migration_id}/repos/{repo_name}/lock}).
     *
     * @param migrationId migration id
     * @param repoName repository name
     * @throws IllegalArgumentException if {@code repoName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void unlockUserRepo(final long migrationId, final String repoName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(MIGRATION_REPO_LOCK, migrationId, repoName));
    }

    /**
     * List repositories in an authenticated-user migration ({@code GET /user/migrations/{migration_id}/repositories}).
     *
     * @param migrationId migration id
     * @return first HTTP page of repositories
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleRepository> listUserMigrationRepositories(final long migrationId) throws IOException {
        return listUserMigrationRepositories(migrationId, GitHubPageRequest.byPage());
    }

    /**
     * List repositories in an authenticated-user migration ({@code GET /user/migrations/{migration_id}/repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param migrationId migration id
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleRepository> listUserMigrationRepositories(
            final long migrationId, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(MIGRATION_REPOSITORIES, Map.of(), request, REPOSITORIES_TYPE, migrationId);
    }
}
