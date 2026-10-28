package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Core GitHub Repos REST resource for API version {@code 2026-03-10}.
 *
 * <p>Repository CRUD, topics, toggles, and related {@code subcategory=repos} operations.
 */
@SuppressWarnings("java:S6539")
public final class GitHubRepositoryApi extends GitHubResourceApi {

    private static final PathTemplate HASH_ALGORITHM = PathTemplate.of("/repos/{owner}/{repo}/hash-algorithm");
    private static final PathTemplate CODEOWNERS_ERRORS = PathTemplate.of("/repos/{owner}/{repo}/codeowners/errors");
    private static final PathTemplate IMMUTABLE_RELEASES = PathTemplate.of("/repos/{owner}/{repo}/immutable-releases");
    private static final PathTemplate PRIVATE_VULNERABILITY_REPORTING =
            PathTemplate.of("/repos/{owner}/{repo}/private-vulnerability-reporting");
    private static final PathTemplate VULNERABILITY_ALERTS =
            PathTemplate.of("/repos/{owner}/{repo}/vulnerability-alerts");
    private static final PathTemplate AUTOMATED_SECURITY_FIXES =
            PathTemplate.of("/repos/{owner}/{repo}/automated-security-fixes");
    private static final PathTemplate ACTIVITY = PathTemplate.of("/repos/{owner}/{repo}/activity");
    private static final PathTemplate TEAMS = PathTemplate.of("/repos/{owner}/{repo}/teams");
    private static final PathTemplate TAGS = PathTemplate.of("/repos/{owner}/{repo}/tags");
    private static final PathTemplate CACHE_INFO = PathTemplate.of("/repos/{owner}/{repo}/replicas/caches");
    private static final PathTemplate CONTRIBUTORS = PathTemplate.of("/repos/{owner}/{repo}/contributors");
    private static final PathTemplate DISPATCHES = PathTemplate.of("/repos/{owner}/{repo}/dispatches");
    private static final PathTemplate LANGUAGES = PathTemplate.of("/repos/{owner}/{repo}/languages");
    private static final PathTemplate TOPICS = PathTemplate.of("/repos/{owner}/{repo}/topics");
    private static final PathTemplate TRANSFER = PathTemplate.of("/repos/{owner}/{repo}/transfer");
    private static final PathTemplate TEMPLATE_GENERATE =
            PathTemplate.of("/repos/{template_owner}/{template_repo}/generate");
    private static final PathTemplate USERS_REPOS = PathTemplate.of("/users/{username}/repos");
    private static final PathTemplate USER_REPOS = PathTemplate.of("/user/repos");
    private static final PathTemplate REPO = PathTemplate.of("/repos/{owner}/{repo}");
    private static final PathTemplate ORG_REPOS = PathTemplate.of("/orgs/{org}/repos");
    private static final PathTemplate REPOSITORIES = PathTemplate.of("/repositories");

    private static final JsonType<List<GitHubMinimalRepository>> MINIMAL_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubMinimalRepository.class);
    private static final JsonType<List<GitHubRepository>> REPOSITORIES_TYPE = JsonTypes.listOf(GitHubRepository.class);
    private static final JsonType<List<GitHubRepositoryActivity>> ACTIVITIES_TYPE =
            JsonTypes.listOf(GitHubRepositoryActivity.class);
    private static final JsonType<List<GitHubContributor>> CONTRIBUTORS_TYPE =
            JsonTypes.listOf(GitHubContributor.class);
    private static final JsonType<List<GitHubRepositoryTag>> TAGS_TYPE = JsonTypes.listOf(GitHubRepositoryTag.class);
    private static final JsonType<List<GitHubListCacheInfoItem>> CACHE_INFO_TYPE =
            JsonTypes.listOf(GitHubListCacheInfoItem.class);
    private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);

    /**
     * Repositories resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoryApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization repositories ({@code GET /orgs/{org}/repos}).
     *
     * @param org organization login
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForOrg(final String org) throws IOException {
        return listForOrg(org, GitHubListForOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List organization repositories ({@code GET /orgs/{org}/repos}).
     *
     * @param org organization login
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForOrg(final String org, final GitHubPageRequest request)
            throws IOException {
        return listForOrg(org, GitHubListForOrgFilter.empty(), request);
    }

    /**
     * List organization repositories ({@code GET /orgs/{org}/repos}).
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForOrg(
            final String org, final GitHubListForOrgFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_REPOS, filter.toQueryParameters(), request, MINIMAL_REPOSITORIES_TYPE, org);
    }

    /**
     * Create an organization repository ({@code POST /orgs/{org}/repos}).
     *
     * @param org organization login
     * @param payload repository to create
     * @return created repository
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubFullRepository createInOrg(final String org, final GitHubCreateInOrgPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORG_REPOS, org), payload), GitHubFullRepository.class);
    }

    /**
     * Get a repository ({@code GET /repos/{owner}/{repo}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return the repository, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubFullRepository> get(final String owner, final String repo) throws IOException {
        return caller.fetchOrEmpty(get(REPO, owner, repo), GitHubFullRepository.class);
    }

    /**
     * Update a repository ({@code PATCH /repos/{owner}/{repo}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload fields to update
     * @return updated repository
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubFullRepository update(final String owner, final String repo, final GitHubUpdatePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(REPO, owner, repo), payload), GitHubFullRepository.class);
    }

    /**
     * Delete a repository ({@code DELETE /repos/{owner}/{repo}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void delete(final String owner, final String repo) throws IOException {
        caller.send(delete(REPO, owner, repo));
    }

    /**
     * List repositories for the authenticated user ({@code GET /user/repos}).
     *
     * @return first HTTP page of repositories
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listForAuthenticatedUser() throws IOException {
        return listForAuthenticatedUser(GitHubListForAuthenticatedUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repositories for the authenticated user ({@code GET /user/repos}).
     *
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listForAuthenticatedUser(final GitHubPageRequest request) throws IOException {
        return listForAuthenticatedUser(GitHubListForAuthenticatedUserFilter.empty(), request);
    }

    /**
     * List repositories for the authenticated user ({@code GET /user/repos}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepository> listForAuthenticatedUser(
            final GitHubListForAuthenticatedUserFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_REPOS, filter.toQueryParameters(), request, REPOSITORIES_TYPE);
    }

    /**
     * Create a repository for the authenticated user ({@code POST /user/repos}).
     *
     * @param payload repository to create
     * @return created repository
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubFullRepository createForAuthenticatedUser(final GitHubCreateForAuthenticatedUserPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(USER_REPOS), payload), GitHubFullRepository.class);
    }

    /**
     * List repositories for a user ({@code GET /users/{username}/repos}).
     *
     * @param username GitHub username
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForUser(final String username) throws IOException {
        return listForUser(username, GitHubListForUserFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repositories for a user ({@code GET /users/{username}/repos}).
     *
     * @param username GitHub username
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForUser(final String username, final GitHubPageRequest request)
            throws IOException {
        return listForUser(username, GitHubListForUserFilter.empty(), request);
    }

    /**
     * List repositories for a user ({@code GET /users/{username}/repos}).
     *
     * @param username GitHub username
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of repositories
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listForUser(
            final String username, final GitHubListForUserFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_REPOS, filter.toQueryParameters(), request, MINIMAL_REPOSITORIES_TYPE, username);
    }

    /**
     * List public repositories ({@code GET /repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#none()}. See {@link #listPublic(GitHubPageRequest)}.
     *
     * @return first HTTP page of repositories
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listPublic() throws IOException {
        return listPublic(GitHubPageRequest.none());
    }

    /**
     * List public repositories ({@code GET /repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest} only; page and cursor variants are
     * rejected. Pagination is powered exclusively by the {@code since} parameter; this method does not send
     * {@code per_page}.
     *
     * @param request since-id pagination token
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a page or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listPublic(final GitHubPageRequest request) throws IOException {
        return listPublic(GitHubListPublicFilter.empty(), request);
    }

    /**
     * List public repositories with filters ({@code GET /repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest} only; page and cursor variants are
     * rejected. Pagination is powered exclusively by the {@code since} parameter; this method does not send
     * {@code per_page}. The {@code visibility} filter is defined by the {@code GHES} flavor only.
     *
     * @param filter query filters
     * @param request since-id pagination token
     * @return first HTTP page of repositories
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a page or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubMinimalRepository> listPublic(
            final GitHubListPublicFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                REPOSITORIES,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.since(MINIMAL_REPOSITORIES_TYPE).withoutPerPage());
    }

    /**
     * Create a repository using a template ({@code POST /repos/{template_owner}/{template_repo}/generate}).
     *
     * @param templateOwner template repository owner
     * @param templateRepo template repository name
     * @param payload repository to create
     * @return created repository
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code templateOwner} or {@code templateRepo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubFullRepository createUsingTemplate(
            final String templateOwner, final String templateRepo, final GitHubCreateUsingTemplatePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(TEMPLATE_GENERATE, templateOwner, templateRepo), payload), GitHubFullRepository.class);
    }

    /**
     * Transfer a repository ({@code POST /repos/{owner}/{repo}/transfer}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload transfer details
     * @return transferred repository
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubMinimalRepository transfer(final String owner, final String repo, final GitHubTransferPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(TRANSFER, owner, repo), payload), GitHubMinimalRepository.class);
    }

    /**
     * Get all repository topics ({@code GET /repos/{owner}/{repo}/topics}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return repository topics
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryTopics getAllTopics(final String owner, final String repo) throws IOException {
        return getAllTopics(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * Get all repository topics ({@code GET /repos/{owner}/{repo}/topics}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return repository topics
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryTopics getAllTopics(final String owner, final String repo, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetch(
                caller.pageParameters(get(TOPICS, owner, repo), request, GitHubPageRequestKindValidators.PAGE),
                GitHubRepositoryTopics.class);
    }

    /**
     * Replace all repository topics ({@code PUT /repos/{owner}/{repo}/topics}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload topics to set
     * @return updated topics
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryTopics replaceAllTopics(
            final String owner, final String repo, final GitHubReplaceAllTopicsPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(put(TOPICS, owner, repo), payload), GitHubRepositoryTopics.class);
    }

    /**
     * List repository languages ({@code GET /repos/{owner}/{repo}/languages}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return language byte counts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryLanguages listLanguages(final String owner, final String repo) throws IOException {
        return caller.fetch(get(LANGUAGES, owner, repo), GitHubRepositoryLanguages.class);
    }

    /**
     * Create a repository dispatch event ({@code POST /repos/{owner}/{repo}/dispatches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload dispatch event
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void createDispatchEvent(
            final String owner, final String repo, final GitHubCreateDispatchEventPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(DISPATCHES, owner, repo), payload));
    }

    /**
     * List repository contributors ({@code GET /repos/{owner}/{repo}/contributors}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of contributors, empty when GitHub answers 204
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubContributor> listContributors(final String owner, final String repo) throws IOException {
        return listContributors(owner, repo, GitHubListContributorsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repository contributors ({@code GET /repos/{owner}/{repo}/contributors}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of contributors, empty when GitHub answers 204
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubContributor> listContributors(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listContributors(owner, repo, GitHubListContributorsFilter.empty(), request);
    }

    /**
     * List repository contributors ({@code GET /repos/{owner}/{repo}/contributors}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of contributors, empty when GitHub answers 204
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubContributor> listContributors(
            final String owner,
            final String repo,
            final GitHubListContributorsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(CONTRIBUTORS, filter.toQueryParameters(), request, CONTRIBUTORS_TYPE, owner, repo);
    }

    /**
     * List repository tags ({@code GET /repos/{owner}/{repo}/tags}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of tags
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryTag> listTags(final String owner, final String repo) throws IOException {
        return listTags(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository tags ({@code GET /repos/{owner}/{repo}/tags}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of tags
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryTag> listTags(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TAGS, Map.of(), request, TAGS_TYPE, owner, repo);
    }

    /**
     * List the cache replication status of a repository ({@code GET /repos/{owner}/{repo}/replicas/caches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of cache replicas
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubListCacheInfoItem> listCacheInfo(final String owner, final String repo) throws IOException {
        return listCacheInfo(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List the cache replication status of a repository ({@code GET /repos/{owner}/{repo}/replicas/caches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of cache replicas
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubPage<GitHubListCacheInfoItem> listCacheInfo(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(CACHE_INFO, Map.of(), request, CACHE_INFO_TYPE, owner, repo);
    }

    /**
     * List repository teams ({@code GET /repos/{owner}/{repo}/teams}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of teams
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeam> listTeams(final String owner, final String repo) throws IOException {
        return listTeams(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List repository teams ({@code GET /repos/{owner}/{repo}/teams}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of teams
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTeam> listTeams(final String owner, final String repo, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TEAMS, Map.of(), request, TEAMS_TYPE, owner, repo);
    }

    /**
     * List repository activities ({@code GET /repos/{owner}/{repo}/activity}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of activities
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryActivity> listActivities(final String owner, final String repo)
            throws IOException {
        return listActivities(owner, repo, GitHubListActivitiesFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List repository activities ({@code GET /repos/{owner}/{repo}/activity}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request cursor pagination token
     * @return first HTTP page of activities
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a page or since
     *     variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryActivity> listActivities(
            final String owner,
            final String repo,
            final GitHubListActivitiesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ACTIVITY,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ACTIVITIES_TYPE),
                owner,
                repo);
    }

    /**
     * Check if Dependabot security updates are enabled ({@code GET /repos/{owner}/{repo}/automated-security-fixes}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return status
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubAutomatedSecurityFixesStatus checkAutomatedSecurityFixes(final String owner, final String repo)
            throws IOException {

        return caller.fetch(get(AUTOMATED_SECURITY_FIXES, owner, repo), GitHubAutomatedSecurityFixesStatus.class);
    }

    /**
     * Enable Dependabot security updates ({@code PUT /repos/{owner}/{repo}/automated-security-fixes}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void enableAutomatedSecurityFixes(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(AUTOMATED_SECURITY_FIXES, owner, repo));
    }

    /**
     * Disable Dependabot security updates ({@code DELETE /repos/{owner}/{repo}/automated-security-fixes}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void disableAutomatedSecurityFixes(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(AUTOMATED_SECURITY_FIXES, owner, repo));
    }

    /**
     * Check if vulnerability alerts are enabled ({@code GET /repos/{owner}/{repo}/vulnerability-alerts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return {@code true} when alerts are enabled (HTTP 204)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields {@code false})
     */
    public boolean checkVulnerabilityAlerts(final String owner, final String repo) throws IOException {
        return caller.isExisting(get(VULNERABILITY_ALERTS, owner, repo));
    }

    /**
     * Enable vulnerability alerts ({@code PUT /repos/{owner}/{repo}/vulnerability-alerts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void enableVulnerabilityAlerts(final String owner, final String repo) throws IOException {
        caller.send(put(VULNERABILITY_ALERTS, owner, repo));
    }

    /**
     * Disable vulnerability alerts ({@code DELETE /repos/{owner}/{repo}/vulnerability-alerts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void disableVulnerabilityAlerts(final String owner, final String repo) throws IOException {
        caller.send(delete(VULNERABILITY_ALERTS, owner, repo));
    }

    /**
     * Check if private vulnerability reporting is enabled ({@code GET
     * /repos/{owner}/{repo}/private-vulnerability-reporting}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return reporting status
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPrivateVulnerabilityReportingStatus checkPrivateVulnerabilityReporting(
            final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                get(PRIVATE_VULNERABILITY_REPORTING, owner, repo), GitHubPrivateVulnerabilityReportingStatus.class);
    }

    /**
     * Enable private vulnerability reporting ({@code PUT /repos/{owner}/{repo}/private-vulnerability-reporting}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void enablePrivateVulnerabilityReporting(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(put(PRIVATE_VULNERABILITY_REPORTING, owner, repo));
    }

    /**
     * Disable private vulnerability reporting ({@code DELETE /repos/{owner}/{repo}/private-vulnerability-reporting}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void disablePrivateVulnerabilityReporting(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(PRIVATE_VULNERABILITY_REPORTING, owner, repo));
    }

    /**
     * Check if immutable releases are enabled ({@code GET /repos/{owner}/{repo}/immutable-releases}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return status
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubImmutableReleasesStatus checkImmutableReleases(final String owner, final String repo)
            throws IOException {

        return caller.fetch(get(IMMUTABLE_RELEASES, owner, repo), GitHubImmutableReleasesStatus.class);
    }

    /**
     * Enable immutable releases ({@code PUT /repos/{owner}/{repo}/immutable-releases}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void enableImmutableReleases(final String owner, final String repo) throws IOException {
        caller.send(put(IMMUTABLE_RELEASES, owner, repo));
    }

    /**
     * Disable immutable releases ({@code DELETE /repos/{owner}/{repo}/immutable-releases}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void disableImmutableReleases(final String owner, final String repo) throws IOException {
        caller.send(delete(IMMUTABLE_RELEASES, owner, repo));
    }

    /**
     * List CODEOWNERS errors ({@code GET /repos/{owner}/{repo}/codeowners/errors}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return CODEOWNERS errors, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCodeownersErrors> codeownersErrors(final String owner, final String repo) throws IOException {
        return codeownersErrors(owner, repo, GitHubCodeownersErrorsFilter.empty());
    }

    /**
     * List CODEOWNERS errors ({@code GET /repos/{owner}/{repo}/codeowners/errors}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return CODEOWNERS errors, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCodeownersErrors> codeownersErrors(
            final String owner, final String repo, final GitHubCodeownersErrorsFilter filter) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(CODEOWNERS_ERRORS, owner, repo).withQuery(filter.toQueryParameters()),
                GitHubCodeownersErrors.class);
    }

    /**
     * Get repository hash algorithm ({@code GET /repos/{owner}/{repo}/hash-algorithm}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return hash algorithm
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubRepositoryHashAlgorithmResponse getHashAlgorithm(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(get(HASH_ALGORITHM, owner, repo), GitHubRepositoryHashAlgorithmResponse.class);
    }
}
