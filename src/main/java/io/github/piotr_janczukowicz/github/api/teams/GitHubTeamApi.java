package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
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

/**
 * GitHub Teams REST resource for API version {@code 2026-03-10}.
 *
 * <p>Interact with organization teams and the authenticated user's teams.
 */
public final class GitHubTeamApi extends GitHubResourceApi {

    private static final PathTemplate TEAM_TEAMS = PathTemplate.of("/teams/{team_id}/teams");
    private static final PathTemplate TEAM_REPO = PathTemplate.of("/teams/{team_id}/repos/{owner}/{repo}");
    private static final PathTemplate TEAM_REPOS = PathTemplate.of("/teams/{team_id}/repos");
    private static final PathTemplate TEAM = PathTemplate.of("/teams/{team_id}");
    private static final PathTemplate ORG_TEAM_TEAMS = PathTemplate.of("/orgs/{org}/teams/{team_slug}/teams");
    private static final PathTemplate ORG_TEAM_REPO =
            PathTemplate.of("/orgs/{org}/teams/{team_slug}/repos/{owner}/{repo}");
    private static final PathTemplate ORG_TEAM_REPOS = PathTemplate.of("/orgs/{org}/teams/{team_slug}/repos");
    private static final PathTemplate ORG_TEAM = PathTemplate.of("/orgs/{org}/teams/{team_slug}");
    private static final PathTemplate ORG_TEAMS = PathTemplate.of("/orgs/{org}/teams");
    private static final PathTemplate USER_TEAMS = PathTemplate.of("/user/teams");

    private static final String DEPRECATED_SINCE = "2020-01-21";

    private static final String REPOSITORY_MEDIA_TYPE = "application/vnd.github.v3.repository+json";

    private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);
    private static final JsonType<List<GitHubTeamFull>> TEAMS_FULL_TYPE = JsonTypes.listOf(GitHubTeamFull.class);
    private static final JsonType<List<GitHubMinimalRepository>> REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubMinimalRepository.class);

    /**
     * Teams resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubTeamApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List teams ({@code GET /orgs/{org}/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #list(String, GitHubPageRequest)}.
     *
     * @param org organization login
     * @return first HTTP page of teams
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeam> list(final String org) throws IOException {
        return list(org, GitHubListTeamsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List teams ({@code GET /orgs/{org}/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #list(String, GitHubListTeamsFilter, GitHubPageRequest)}.
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of teams
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeam> list(final String org, final GitHubListTeamsFilter filter) throws IOException {
        return list(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List teams ({@code GET /orgs/{org}/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param request pagination token
     * @return first HTTP page of teams
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeam> list(final String org, final GitHubPageRequest request) throws IOException {
        return list(org, GitHubListTeamsFilter.empty(), request);
    }

    /**
     * List teams ({@code GET /orgs/{org}/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of teams
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeam> list(
            final String org, final GitHubListTeamsFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_TEAMS, filter.toQueryParameters(), request, TEAMS_TYPE, org);
    }

    /**
     * Create a team ({@code POST /orgs/{org}/teams}).
     *
     * @param org organization login
     * @param payload team to create
     * @return the created team
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubTeamFull create(final String org, final GitHubCreateTeamPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_TEAMS, org), payload), GitHubTeamFull.class);
    }

    /**
     * Get a team by name ({@code GET /orgs/{org}/teams/{team_slug}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param org organization login
     * @param slug team slug
     * @return the team with full details, or empty if GitHub has no such team
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubTeamFull> getByName(final String org, final String slug) throws IOException {
        return caller.fetchOrEmpty(get(ORG_TEAM, org, slug), GitHubTeamFull.class);
    }

    /**
     * Update a team ({@code PATCH /orgs/{org}/teams/{team_slug}}).
     *
     * @param org organization login
     * @param slug team slug
     * @param payload fields to update
     * @return the updated team
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubTeamFull update(final String org, final String slug, final GitHubUpdateTeamPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ORG_TEAM, org, slug), payload), GitHubTeamFull.class);
    }

    /**
     * Delete a team ({@code DELETE /orgs/{org}/teams/{team_slug}}).
     *
     * @param org organization login
     * @param slug team slug
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void delete(final String org, final String slug) throws IOException {
        caller.send(delete(ORG_TEAM, org, slug));
    }

    /**
     * List team repositories ({@code GET /orgs/{org}/teams/{team_slug}/repos}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listRepositories(String, String, GitHubPageRequest)}.
     *
     * @param org organization login
     * @param slug team slug
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listRepositories(final String org, final String slug)
            throws IOException {
        return listRepositories(org, slug, GitHubPageRequest.byPage());
    }

    /**
     * List team repositories ({@code GET /orgs/{org}/teams/{team_slug}/repos}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param slug team slug
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank, or {@code request} is a since or cursor
     *     variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listRepositories(
            final String org, final String slug, final GitHubPageRequest request) throws IOException {
        return pageList(ORG_TEAM_REPOS, Map.of(), request, REPOSITORIES_TYPE, org, slug);
    }

    /**
     * Check team repository permission ({@code GET /orgs/{org}/teams/{team_slug}/repos/{owner}/{repo}}).
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param org organization login
     * @param slug team slug
     * @param owner repository owner login
     * @param repo repository name
     * @return whether the team has permission on the repository
     * @throws IllegalArgumentException if {@code org}, {@code slug}, {@code owner}, or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     */
    public boolean hasRepositoryPermission(final String org, final String slug, final String owner, final String repo)
            throws IOException {
        return caller.isExisting(get(ORG_TEAM_REPO, org, slug, owner, repo));
    }

    /**
     * Get team repository permission ({@code GET /orgs/{org}/teams/{team_slug}/repos/{owner}/{repo}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404. Uses Accept {@code application/vnd.github.v3.repository+json}.
     *
     * @param org organization login
     * @param slug team slug
     * @param owner repository owner login
     * @param repo repository name
     * @return the repository permission, or empty if the team has no access
     * @throws IllegalArgumentException if {@code org}, {@code slug}, {@code owner}, or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     */
    public Optional<GitHubTeamRepository> getRepositoryPermission(
            final String org, final String slug, final String owner, final String repo) throws IOException {
        return caller.fetchOrEmpty(
                get(ORG_TEAM_REPO, org, slug, owner, repo).withHeader(ACCEPT, REPOSITORY_MEDIA_TYPE),
                GitHubTeamRepository.class);
    }

    /**
     * Add or update team repository permission ({@code PUT /orgs/{org}/teams/{team_slug}/repos/{owner}/{repo}}).
     *
     * @param org organization login
     * @param slug team slug
     * @param owner repository owner login
     * @param repo repository name
     * @throws IllegalArgumentException if {@code org}, {@code slug}, {@code owner}, or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void upsertRepositoryPermission(final String org, final String slug, final String owner, final String repo)
            throws IOException {
        caller.send(put(ORG_TEAM_REPO, org, slug, owner, repo));
    }

    /**
     * Add or update team repository permission ({@code PUT /orgs/{org}/teams/{team_slug}/repos/{owner}/{repo}}).
     *
     * @param org organization login
     * @param slug team slug
     * @param owner repository owner login
     * @param repo repository name
     * @param payload repository permission
     * @throws IllegalArgumentException if {@code org}, {@code slug}, {@code owner}, or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void upsertRepositoryPermission(
            final String org,
            final String slug,
            final String owner,
            final String repo,
            final GitHubUpsertTeamRepositoryPermissionPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_TEAM_REPO, org, slug, owner, repo), payload));
    }

    /**
     * Remove a repository from a team ({@code DELETE /orgs/{org}/teams/{team_slug}/repos/{owner}/{repo}}).
     *
     * @param org organization login
     * @param slug team slug
     * @param owner repository owner login
     * @param repo repository name
     * @throws IllegalArgumentException if {@code org}, {@code slug}, {@code owner}, or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void removeRepository(final String org, final String slug, final String owner, final String repo)
            throws IOException {
        caller.send(delete(ORG_TEAM_REPO, org, slug, owner, repo));
    }

    /**
     * List child teams ({@code GET /orgs/{org}/teams/{team_slug}/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listChildTeams(String, String, GitHubPageRequest)}.
     *
     * @param org organization login
     * @param slug team slug
     * @return first HTTP page of child teams
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeam> listChildTeams(final String org, final String slug) throws IOException {
        return listChildTeams(org, slug, GitHubPageRequest.byPage());
    }

    /**
     * List child teams ({@code GET /orgs/{org}/teams/{team_slug}/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param slug team slug
     * @param request pagination token
     * @return first HTTP page of child teams
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank, or {@code request} is a since or cursor
     *     variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeam> listChildTeams(final String org, final String slug, final GitHubPageRequest request)
            throws IOException {
        return pageList(ORG_TEAM_TEAMS, Map.of(), request, TEAMS_TYPE, org, slug);
    }

    /**
     * Get a team by id (legacy) ({@code GET /teams/{team_id}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #getByName(String, String)}.
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param teamId team id
     * @return the team with full details, or empty if GitHub has no such team
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public Optional<GitHubTeamFull> getLegacy(final long teamId) throws IOException {
        return caller.fetchOrEmpty(get(TEAM, teamId), GitHubTeamFull.class);
    }

    /**
     * Update a team (legacy) ({@code PATCH /teams/{team_id}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #update(String, String, GitHubUpdateTeamPayload)}.
     *
     * @param teamId team id
     * @param payload fields to update
     * @return the updated team
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubTeamFull updateLegacy(final long teamId, final GitHubUpdateTeamLegacyPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(TEAM, teamId), payload), GitHubTeamFull.class);
    }

    /**
     * Delete a team (legacy) ({@code DELETE /teams/{team_id}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #delete(String, String)}.
     *
     * @param teamId team id
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public void deleteLegacy(final long teamId) throws IOException {
        caller.send(delete(TEAM, teamId));
    }

    /**
     * List team repositories (legacy) ({@code GET /teams/{team_id}/repos}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listRepositories(String, String)}.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listRepositoriesLegacy(long, GitHubPageRequest)}.
     *
     * @param teamId team id
     * @return first HTTP page of repositories
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubPage<GitHubMinimalRepository> listRepositoriesLegacy(final long teamId) throws IOException {
        return listRepositoriesLegacy(teamId, GitHubPageRequest.byPage());
    }

    /**
     * List team repositories (legacy) ({@code GET /teams/{team_id}/repos}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listRepositories(String, String, GitHubPageRequest)}.
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param teamId team id
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubPage<GitHubMinimalRepository> listRepositoriesLegacy(
            final long teamId, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TEAM_REPOS, Map.of(), request, REPOSITORIES_TYPE, teamId);
    }

    /**
     * Check team repository permission (legacy) ({@code GET /teams/{team_id}/repos/{owner}/{repo}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #hasRepositoryPermission(String, String, String, String)}.
     *
     * <p>Returns {@code true} on HTTP 2xx and {@code false} on HTTP 404.
     *
     * @param teamId team id
     * @param owner repository owner login
     * @param repo repository name
     * @return whether the team has permission on the repository
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields false)
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public boolean hasRepositoryPermissionLegacy(final long teamId, final String owner, final String repo)
            throws IOException {

        return caller.isExisting(get(TEAM_REPO, teamId, owner, repo));
    }

    /**
     * Get team repository permission (legacy) ({@code GET /teams/{team_id}/repos/{owner}/{repo}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #getRepositoryPermission(String, String, String, String)}.
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404. Uses Accept {@code application/vnd.github.v3.repository+json}.
     *
     * @param teamId team id
     * @param owner repository owner login
     * @param repo repository name
     * @return the repository permission, or empty if the team has no access
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public Optional<GitHubTeamRepository> getRepositoryPermissionLegacy(
            final long teamId, final String owner, final String repo) throws IOException {

        return caller.fetchOrEmpty(
                get(TEAM_REPO, teamId, owner, repo).withHeader(ACCEPT, REPOSITORY_MEDIA_TYPE),
                GitHubTeamRepository.class);
    }

    /**
     * Add or update team repository permission (legacy) ({@code PUT /teams/{team_id}/repos/{owner}/{repo}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #upsertRepositoryPermission(String, String, String, String)}.
     *
     * @param teamId team id
     * @param owner repository owner login
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public void upsertRepositoryPermissionLegacy(final long teamId, final String owner, final String repo)
            throws IOException {

        caller.send(put(TEAM_REPO, teamId, owner, repo));
    }

    /**
     * Add or update team repository permission (legacy) ({@code PUT /teams/{team_id}/repos/{owner}/{repo}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #upsertRepositoryPermission(String, String, String, String,
     * GitHubUpsertTeamRepositoryPermissionPayload)}.
     *
     * @param teamId team id
     * @param owner repository owner login
     * @param repo repository name
     * @param payload repository permission
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public void upsertRepositoryPermissionLegacy(
            final long teamId,
            final String owner,
            final String repo,
            final GitHubUpsertTeamRepositoryPermissionLegacyPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(TEAM_REPO, teamId, owner, repo), payload));
    }

    /**
     * Remove a repository from a team (legacy) ({@code DELETE /teams/{team_id}/repos/{owner}/{repo}}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #removeRepository(String, String, String, String)}.
     *
     * @param teamId team id
     * @param owner repository owner login
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public void removeRepositoryLegacy(final long teamId, final String owner, final String repo) throws IOException {
        caller.send(delete(TEAM_REPO, teamId, owner, repo));
    }

    /**
     * List child teams (legacy) ({@code GET /teams/{team_id}/teams}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listChildTeams(String, String)}.
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listChildTeamsLegacy(long, GitHubPageRequest)}.
     *
     * @param teamId team id
     * @return first HTTP page of child teams
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubPage<GitHubTeam> listChildTeamsLegacy(final long teamId) throws IOException {
        return listChildTeamsLegacy(teamId, GitHubPageRequest.byPage());
    }

    /**
     * List child teams (legacy) ({@code GET /teams/{team_id}/teams}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listChildTeams(String, String, GitHubPageRequest)}.
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param teamId team id
     * @param request pagination token
     * @return first HTTP page of child teams
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    public GitHubPage<GitHubTeam> listChildTeamsLegacy(final long teamId, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TEAM_TEAMS, Map.of(), request, TEAMS_TYPE, teamId);
    }

    /**
     * List teams for the authenticated user ({@code GET /user/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listForAuthenticatedUser(GitHubPageRequest)}.
     *
     * @return first HTTP page of teams
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeamFull> listForAuthenticatedUser() throws IOException {
        return listForAuthenticatedUser(GitHubPageRequest.byPage());
    }

    /**
     * List teams for the authenticated user ({@code GET /user/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param request pagination token
     * @return first HTTP page of teams
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeamFull> listForAuthenticatedUser(final GitHubPageRequest request) throws IOException {
        return pageList(USER_TEAMS, Map.of(), request, TEAMS_FULL_TYPE);
    }
}
