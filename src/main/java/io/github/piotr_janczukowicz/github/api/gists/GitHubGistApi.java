package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** GitHub gists REST resource for API version {@code 2026-03-10}. */
public final class GitHubGistApi extends GitHubResourceApi {

    private static final PathTemplate GISTS = PathTemplate.of("/gists");
    private static final PathTemplate PUBLIC_GISTS = PathTemplate.of("/gists/public");
    private static final PathTemplate STARRED_GISTS = PathTemplate.of("/gists/starred");
    private static final PathTemplate GIST = PathTemplate.of("/gists/{gist_id}");
    private static final PathTemplate GIST_COMMITS = PathTemplate.of("/gists/{gist_id}/commits");
    private static final PathTemplate GIST_FORKS = PathTemplate.of("/gists/{gist_id}/forks");
    private static final PathTemplate GIST_STAR = PathTemplate.of("/gists/{gist_id}/star");
    private static final PathTemplate GIST_REVISION = PathTemplate.of("/gists/{gist_id}/{sha}");
    private static final PathTemplate USERS_GISTS = PathTemplate.of("/users/{username}/gists");

    private static final JsonType<List<GitHubBaseGist>> BASE_GISTS_TYPE = JsonTypes.listOf(GitHubBaseGist.class);
    private static final JsonType<List<GitHubGistCommit>> GIST_COMMITS_TYPE = JsonTypes.listOf(GitHubGistCommit.class);
    private static final JsonType<List<GitHubGistSimple>> GIST_SIMPLES_TYPE = JsonTypes.listOf(GitHubGistSimple.class);

    /**
     * Gists resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubGistApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List gists for the authenticated user ({@code GET /gists}).
     *
     * @return first HTTP page of gists
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listGists() throws IOException {
        return listGists(GitHubListFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List gists for the authenticated user ({@code GET /gists}).
     *
     * @param filter query filters
     * @return first HTTP page of gists
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listGists(final GitHubListFilter filter) throws IOException {
        return listGists(filter, GitHubPageRequest.byPage());
    }

    /**
     * List gists for the authenticated user ({@code GET /gists}).
     *
     * @param request pagination token
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listGists(final GitHubPageRequest request) throws IOException {
        return listGists(GitHubListFilter.empty(), request);
    }

    /**
     * List gists for the authenticated user ({@code GET /gists}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listGists(final GitHubListFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(GISTS, filter.toQueryParameters(), request, BASE_GISTS_TYPE);
    }

    /**
     * Create a gist ({@code POST /gists}).
     *
     * @param payload gist to create
     * @return created gist
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGistSimple createGist(final GitHubCreatePayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(GISTS), payload), GitHubGistSimple.class);
    }

    /**
     * List public gists ({@code GET /gists/public}).
     *
     * @return first HTTP page of gists
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listPublicGists() throws IOException {
        return listPublicGists(GitHubListPublicFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List public gists ({@code GET /gists/public}).
     *
     * @param filter query filters
     * @return first HTTP page of gists
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listPublicGists(final GitHubListPublicFilter filter) throws IOException {
        return listPublicGists(filter, GitHubPageRequest.byPage());
    }

    /**
     * List public gists ({@code GET /gists/public}).
     *
     * @param request pagination token
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listPublicGists(final GitHubPageRequest request) throws IOException {
        return listPublicGists(GitHubListPublicFilter.empty(), request);
    }

    /**
     * List public gists ({@code GET /gists/public}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listPublicGists(
            final GitHubListPublicFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(PUBLIC_GISTS, filter.toQueryParameters(), request, BASE_GISTS_TYPE);
    }

    /**
     * List starred gists ({@code GET /gists/starred}).
     *
     * @return first HTTP page of gists
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listStarredGists() throws IOException {
        return listStarredGists(GitHubListStarredFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List starred gists ({@code GET /gists/starred}).
     *
     * @param filter query filters
     * @return first HTTP page of gists
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listStarredGists(final GitHubListStarredFilter filter) throws IOException {
        return listStarredGists(filter, GitHubPageRequest.byPage());
    }

    /**
     * List starred gists ({@code GET /gists/starred}).
     *
     * @param request pagination token
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listStarredGists(final GitHubPageRequest request) throws IOException {
        return listStarredGists(GitHubListStarredFilter.empty(), request);
    }

    /**
     * List starred gists ({@code GET /gists/starred}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listStarredGists(
            final GitHubListStarredFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(STARRED_GISTS, filter.toQueryParameters(), request, BASE_GISTS_TYPE);
    }

    /**
     * Get a gist ({@code GET /gists/{gist_id}}).
     *
     * @param gistId gist id
     * @return gist, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGistSimple> getGist(final String gistId) throws IOException {
        return caller.fetchOrEmpty(get(GIST, gistId), GitHubGistSimple.class);
    }

    /**
     * Update a gist ({@code PATCH /gists/{gist_id}}).
     *
     * @param gistId gist id
     * @param payload gist changes
     * @return updated gist
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubGistSimple updateGist(final String gistId, final GitHubUpdatePayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(GIST, gistId), payload), GitHubGistSimple.class);
    }

    /**
     * Delete a gist ({@code DELETE /gists/{gist_id}}).
     *
     * @param gistId gist id
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteGist(final String gistId) throws IOException {
        caller.send(delete(GIST, gistId));
    }

    /**
     * List gist commits ({@code GET /gists/{gist_id}/commits}).
     *
     * @param gistId gist id
     * @return first HTTP page of gist commits
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGistCommit> listGistCommits(final String gistId) throws IOException {
        return listGistCommits(gistId, GitHubPageRequest.byPage());
    }

    /**
     * List gist commits ({@code GET /gists/{gist_id}/commits}).
     *
     * @param gistId gist id
     * @param request pagination token
     * @return first HTTP page of gist commits
     * @throws IllegalArgumentException if {@code gistId} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGistCommit> listGistCommits(final String gistId, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(GIST_COMMITS, Map.of(), request, GIST_COMMITS_TYPE, gistId);
    }

    /**
     * List gist forks ({@code GET /gists/{gist_id}/forks}).
     *
     * @param gistId gist id
     * @return first HTTP page of gist forks
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGistSimple> listGistForks(final String gistId) throws IOException {
        return listGistForks(gistId, GitHubPageRequest.byPage());
    }

    /**
     * List gist forks ({@code GET /gists/{gist_id}/forks}).
     *
     * @param gistId gist id
     * @param request pagination token
     * @return first HTTP page of gist forks
     * @throws IllegalArgumentException if {@code gistId} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGistSimple> listGistForks(final String gistId, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(GIST_FORKS, Map.of(), request, GIST_SIMPLES_TYPE, gistId);
    }

    /**
     * Fork a gist ({@code POST /gists/{gist_id}/forks}).
     *
     * @param gistId gist id
     * @return created fork
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubBaseGist forkGist(final String gistId) throws IOException {
        return caller.fetch(post(GIST_FORKS, gistId), GitHubBaseGist.class);
    }

    /**
     * Check if a gist is starred ({@code GET /gists/{gist_id}/star}).
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param gistId gist id
     * @return whether the gist is starred
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     */
    public boolean isGistStarred(final String gistId) throws IOException {
        return caller.isExisting(get(GIST_STAR, gistId));
    }

    /**
     * Star a gist ({@code PUT /gists/{gist_id}/star}).
     *
     * @param gistId gist id
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void starGist(final String gistId) throws IOException {
        caller.send(put(GIST_STAR, gistId));
    }

    /**
     * Unstar a gist ({@code DELETE /gists/{gist_id}/star}).
     *
     * @param gistId gist id
     * @throws IllegalArgumentException if {@code gistId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void unstarGist(final String gistId) throws IOException {
        caller.send(delete(GIST_STAR, gistId));
    }

    /**
     * Get a gist revision ({@code GET /gists/{gist_id}/{sha}}).
     *
     * @param gistId gist id
     * @param sha revision SHA
     * @return gist at the revision, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code gistId} or {@code sha} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubGistSimple> getGistRevision(final String gistId, final String sha) throws IOException {
        return caller.fetchOrEmpty(get(GIST_REVISION, gistId, sha), GitHubGistSimple.class);
    }

    /**
     * List gists for a user ({@code GET /users/{username}/gists}).
     *
     * @param username GitHub username
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listGistsForUser(final String username) throws IOException {
        return listGistsForUser(username, GitHubListForUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List gists for a user ({@code GET /users/{username}/gists}).
     *
     * @param username GitHub username
     * @param filter query filters
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listGistsForUser(final String username, final GitHubListForUserFilter filter)
            throws IOException {
        return listGistsForUser(username, filter, GitHubPageRequest.byPage());
    }

    /**
     * List gists for a user ({@code GET /users/{username}/gists}).
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listGistsForUser(final String username, final GitHubPageRequest request)
            throws IOException {
        return listGistsForUser(username, GitHubListForUserFilter.empty(), request);
    }

    /**
     * List gists for a user ({@code GET /users/{username}/gists}).
     *
     * @param username GitHub username
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of gists
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubBaseGist> listGistsForUser(
            final String username, final GitHubListForUserFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_GISTS, filter.toQueryParameters(), request, BASE_GISTS_TYPE, username);
    }
}
