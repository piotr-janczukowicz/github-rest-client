package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
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

/** GitHub organization migrations REST resource for API version {@code 2026-03-10}. */
public final class GitHubMigrationsOrgsApi extends GitHubResourceApi {

    private static final PathTemplate MIGRATIONS = PathTemplate.of("/orgs/{org}/migrations");
    private static final PathTemplate MIGRATION = PathTemplate.of("/orgs/{org}/migrations/{migration_id}");
    private static final PathTemplate MIGRATION_ARCHIVE =
            PathTemplate.of("/orgs/{org}/migrations/{migration_id}/archive");
    private static final PathTemplate MIGRATION_REPO_LOCK =
            PathTemplate.of("/orgs/{org}/migrations/{migration_id}/repos/{repo_name}/lock");
    private static final PathTemplate MIGRATION_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/migrations/{migration_id}/repositories");

    private static final JsonType<List<GitHubMigration>> MIGRATIONS_TYPE = JsonTypes.listOf(GitHubMigration.class);
    private static final JsonType<List<GitHubSimpleRepository>> REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubSimpleRepository.class);

    /**
     * Organization migrations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMigrationsOrgsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization migrations ({@code GET /orgs/{org}/migrations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listOrgMigrations(String, GitHubListForOrgFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of migrations
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMigration> listOrgMigrations(final String org) throws IOException {
        return listOrgMigrations(org, GitHubListForOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List organization migrations ({@code GET /orgs/{org}/migrations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listOrgMigrations(String, GitHubListForOrgFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization name
     * @param filter optional query filters
     * @return first HTTP page of migrations
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMigration> listOrgMigrations(final String org, final GitHubListForOrgFilter filter)
            throws IOException {
        return listOrgMigrations(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List organization migrations ({@code GET /orgs/{org}/migrations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of migrations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMigration> listOrgMigrations(final String org, final GitHubPageRequest request)
            throws IOException {
        return listOrgMigrations(org, GitHubListForOrgFilter.empty(), request);
    }

    /**
     * List organization migrations ({@code GET /orgs/{org}/migrations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter optional query filters
     * @param request pagination token
     * @return first HTTP page of migrations
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMigration> listOrgMigrations(
            final String org, final GitHubListForOrgFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(MIGRATIONS, filter.toQueryParameters(), request, MIGRATIONS_TYPE, org);
    }

    /**
     * Start an organization migration ({@code POST /orgs/{org}/migrations}).
     *
     * @param org organization name
     * @param payload migration to start
     * @return created migration
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubMigration startOrgMigration(final String org, final GitHubStartForOrgPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(MIGRATIONS, org), payload), GitHubMigration.class);
    }

    /**
     * Get an organization migration status ({@code GET /orgs/{org}/migrations/{migration_id}}).
     *
     * @param org organization name
     * @param migrationId migration id
     * @return migration status, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubMigration> getOrgMigrationStatus(final String org, final long migrationId)
            throws IOException {
        return getOrgMigrationStatus(org, migrationId, GitHubListForOrgFilter.empty());
    }

    /**
     * Get an organization migration status ({@code GET /orgs/{org}/migrations/{migration_id}}).
     *
     * @param org organization name
     * @param migrationId migration id
     * @param filter optional query filters
     * @return migration status, or empty on HTTP 404
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubMigration> getOrgMigrationStatus(
            final String org, final long migrationId, final GitHubListForOrgFilter filter) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(MIGRATION, org, migrationId).withQuery(filter.toQueryParameters()), GitHubMigration.class);
    }

    /**
     * Download an organization migration archive ({@code GET /orgs/{org}/migrations/{migration_id}/archive}).
     *
     * @param org organization name
     * @param migrationId migration id
     * @return redirect location URI
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     */
    public URI downloadOrgMigrationArchive(final String org, final long migrationId) throws IOException {
        return caller.redirectLocation(get(MIGRATION_ARCHIVE, org, migrationId));
    }

    /**
     * Download an organization migration archive into {@code target} instead of returning the redirect ({@code GET
     * /orgs/{org}/migrations/{migration_id}/archive}). The archive is streamed to a temporary file next to
     * {@code target} and moved onto it only when complete; the redirect to the download host is followed without the
     * {@code Authorization} header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema defines only the request
     * mapped by {@link #downloadOrgMigrationArchive(String, long)}, which returns the redirect location. This method is
     * a helper on top of it that follows the redirect and saves the body, so callers need no HTTP client of their own.
     *
     * @param org organization name
     * @param migrationId migration id
     * @param target destination file; its directory must exist, an existing file is replaced
     * @return {@code target}
     * @throws IllegalArgumentException if a text argument is blank
     * @throws IOException if the transport fails or the file cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Path downloadOrgMigrationArchive(final String org, final long migrationId, final Path target)
            throws IOException {
        requireNonNull(target, "target");

        return caller.download(get(MIGRATION_ARCHIVE, org, migrationId), target);
    }

    /**
     * Download an organization migration archive into {@code out} instead of returning the redirect ({@code GET
     * /orgs/{org}/migrations/{migration_id}/archive}); {@code out} is not closed. The redirect to the download host is
     * followed without the {@code Authorization} header.
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema defines only the request
     * mapped by {@link #downloadOrgMigrationArchive(String, long)}, which returns the redirect location. This method is
     * a helper on top of it that follows the redirect and saves the body, so callers need no HTTP client of their own.
     *
     * @param org organization name
     * @param migrationId migration id
     * @param out destination stream
     * @return number of bytes written
     * @throws IllegalArgumentException if a text argument is blank
     * @throws IOException if the transport fails or {@code out} cannot be written
     * @throws GitHubApiException if the response is unsuccessful
     */
    public long downloadOrgMigrationArchive(final String org, final long migrationId, final OutputStream out)
            throws IOException {
        requireNonNull(out, "out");

        return caller.download(get(MIGRATION_ARCHIVE, org, migrationId), out);
    }

    /**
     * Delete an organization migration archive ({@code DELETE /orgs/{org}/migrations/{migration_id}/archive}).
     *
     * @param org organization name
     * @param migrationId migration id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteOrgMigrationArchive(final String org, final long migrationId) throws IOException {
        caller.send(delete(MIGRATION_ARCHIVE, org, migrationId));
    }

    /**
     * Unlock an organization repository ({@code DELETE /orgs/{org}/migrations/{migration_id}/repos/{repo_name}/lock}).
     *
     * @param org organization name
     * @param migrationId migration id
     * @param repoName repository name
     * @throws IllegalArgumentException if {@code org} or {@code repoName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void unlockOrgRepo(final String org, final long migrationId, final String repoName) throws IOException {
        caller.send(delete(MIGRATION_REPO_LOCK, org, migrationId, repoName));
    }

    /**
     * List repositories in an organization migration ({@code GET /orgs/{org}/migrations/{migration_id}/repositories}).
     *
     * @param org organization name
     * @param migrationId migration id
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleRepository> listOrgMigrationRepositories(final String org, final long migrationId)
            throws IOException {
        return listOrgMigrationRepositories(org, migrationId, GitHubPageRequest.byPage());
    }

    /**
     * List repositories in an organization migration ({@code GET /orgs/{org}/migrations/{migration_id}/repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param migrationId migration id
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSimpleRepository> listOrgMigrationRepositories(
            final String org, final long migrationId, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(MIGRATION_REPOSITORIES, Map.of(), request, REPOSITORIES_TYPE, org, migrationId);
    }
}
