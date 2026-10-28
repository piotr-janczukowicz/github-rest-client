package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * GitHub repository source imports REST resource for API version {@code 2026-03-10}.
 *
 * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this endpoint
 *     is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
 *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
 */
@Deprecated(since = "2023-10-12")
public final class GitHubMigrationsSourceImportsApi extends GitHubResourceApi {

    private static final PathTemplate IMPORT = PathTemplate.of("/repos/{owner}/{repo}/import");
    private static final PathTemplate IMPORT_AUTHORS = PathTemplate.of("/repos/{owner}/{repo}/import/authors");
    private static final PathTemplate IMPORT_AUTHOR =
            PathTemplate.of("/repos/{owner}/{repo}/import/authors/{author_id}");
    private static final PathTemplate IMPORT_LARGE_FILES = PathTemplate.of("/repos/{owner}/{repo}/import/large_files");
    private static final PathTemplate IMPORT_LFS = PathTemplate.of("/repos/{owner}/{repo}/import/lfs");

    private static final JsonType<List<GitHubPorterAuthor>> PORTER_AUTHORS_TYPE =
            JsonTypes.listOf(GitHubPorterAuthor.class);
    private static final JsonType<List<GitHubPorterLargeFile>> PORTER_LARGE_FILES_TYPE =
            JsonTypes.listOf(GitHubPorterLargeFile.class);

    /**
     * Repository source imports resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMigrationsSourceImportsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the status of a repository import ({@code GET /repos/{owner}/{repo}/import}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return import status, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubImport> getImportStatus(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(IMPORT, owner, repo), GitHubImport.class);
    }

    /**
     * Start a repository import ({@code PUT /repos/{owner}/{repo}/import}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload import to start
     * @return created import
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubImport startImport(final String owner, final String repo, final GitHubStartImportPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(IMPORT, owner, repo), payload), GitHubImport.class);
    }

    /**
     * Update a repository import ({@code PATCH /repos/{owner}/{repo}/import}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload import updates, or {@code null} to restart the import
     * @return updated import
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubImport updateImport(
            final String owner, final String repo, final @Nullable GitHubUpdateImportPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(caller.jsonIfPresent(patch(IMPORT, owner, repo), payload), GitHubImport.class);
    }

    /**
     * Cancel a repository import ({@code DELETE /repos/{owner}/{repo}/import}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void cancelImport(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(IMPORT, owner, repo));
    }

    /**
     * Get commit authors from a repository import ({@code GET /repos/{owner}/{repo}/import/authors}).
     *
     * <p>Unpaginated: the spec has no {@code per_page}, {@code page}, or Link header.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return commit authors
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubPorterAuthor> getCommitAuthors(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(IMPORT_AUTHORS, owner, repo), PORTER_AUTHORS_TYPE);
    }

    /**
     * Get commit authors from a repository import ({@code GET /repos/{owner}/{repo}/import/authors}).
     *
     * <p>Unpaginated: the spec has no {@code per_page}, {@code page}, or Link header. {@code since} is a user id, not a
     * list page token.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param since only authors with an id greater than this user id
     * @return commit authors
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubPorterAuthor> getCommitAuthors(final String owner, final String repo, final long since)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                get(IMPORT_AUTHORS, owner, repo).withQuery(Map.of("since", Long.toString(since))), PORTER_AUTHORS_TYPE);
    }

    /**
     * Map a commit author from a repository import ({@code PATCH /repos/{owner}/{repo}/import/authors/{author_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param authorId author id
     * @param payload author mapping, or {@code null} to omit the request body
     * @return mapped author
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPorterAuthor mapCommitAuthor(
            final String owner,
            final String repo,
            final long authorId,
            final @Nullable GitHubMapCommitAuthorPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                caller.jsonIfPresent(patch(IMPORT_AUTHOR, owner, repo, authorId), payload), GitHubPorterAuthor.class);
    }

    /**
     * Get large files from a repository import ({@code GET /repos/{owner}/{repo}/import/large_files}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return large files detected during import
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubPorterLargeFile> getLargeFiles(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(IMPORT_LARGE_FILES, owner, repo), PORTER_LARGE_FILES_TYPE);
    }

    /**
     * Set Git LFS preference for a repository import ({@code PATCH /repos/{owner}/{repo}/import/lfs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload LFS preference
     * @return updated import
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Deprecated
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubImport setLfsPreference(
            final String owner, final String repo, final GitHubSetLfsPreferencePayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(IMPORT_LFS, owner, repo), payload), GitHubImport.class);
    }
}
