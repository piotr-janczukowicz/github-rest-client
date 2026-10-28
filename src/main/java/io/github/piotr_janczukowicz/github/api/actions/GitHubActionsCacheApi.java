package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;

/** GitHub Actions cache REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsCacheApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_USAGE_POLICY =
            PathTemplate.of("/enterprises/{enterprise}/actions/cache/usage-policy");
    private static final PathTemplate REPO_USAGE_POLICY =
            PathTemplate.of("/repos/{owner}/{repo}/actions/cache/usage-policy");
    private static final PathTemplate ENTERPRISE_RETENTION_LIMIT =
            PathTemplate.of("/enterprises/{enterprise}/actions/cache/retention-limit");
    private static final PathTemplate ENTERPRISE_STORAGE_LIMIT =
            PathTemplate.of("/enterprises/{enterprise}/actions/cache/storage-limit");
    private static final PathTemplate ORG_RETENTION_LIMIT =
            PathTemplate.of("/organizations/{org}/actions/cache/retention-limit");
    private static final PathTemplate ORG_STORAGE_LIMIT =
            PathTemplate.of("/organizations/{org}/actions/cache/storage-limit");
    private static final PathTemplate ENTERPRISE_CACHE_USAGE =
            PathTemplate.of("/enterprises/{enterprise}/actions/cache/usage");
    private static final PathTemplate ORG_CACHE_USAGE = PathTemplate.of("/orgs/{org}/actions/cache/usage");
    private static final PathTemplate ORG_CACHE_USAGE_BY_REPOSITORY =
            PathTemplate.of("/orgs/{org}/actions/cache/usage-by-repository");
    private static final PathTemplate REPO_RETENTION_LIMIT =
            PathTemplate.of("/repos/{owner}/{repo}/actions/cache/retention-limit");
    private static final PathTemplate REPO_STORAGE_LIMIT =
            PathTemplate.of("/repos/{owner}/{repo}/actions/cache/storage-limit");
    private static final PathTemplate REPO_CACHE_USAGE = PathTemplate.of("/repos/{owner}/{repo}/actions/cache/usage");
    private static final PathTemplate REPO_CACHES = PathTemplate.of("/repos/{owner}/{repo}/actions/caches");
    private static final PathTemplate REPO_CACHE = PathTemplate.of("/repos/{owner}/{repo}/actions/caches/{cache_id}");

    private static final GitHubListSpec<GitHubActionsCacheUsageByRepository> ORG_CACHE_USAGE_SPEC =
            GitHubListSpec.wrapper(
                    GitHubPageRequestKindValidators.PAGE,
                    GitHubActionsCacheUsages.class,
                    GitHubActionsCacheUsages::repositoryCacheUsages,
                    GitHubActionsCacheUsages::totalCount);
    private static final GitHubListSpec<GitHubActionsCache> CACHES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubActionsCacheList.class,
            GitHubActionsCacheList::actionsCaches,
            GitHubActionsCacheList::totalCount);

    /**
     * Cache resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsCacheApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the enterprise cache retention limit ({@code GET /enterprises/{enterprise}/actions/cache/retention-limit}).
     *
     * @param enterprise enterprise slug
     * @return retention limit
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsCacheRetentionLimitForEnterprise getEnterpriseCacheRetentionLimit(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_RETENTION_LIMIT, enterprise), GitHubActionsCacheRetentionLimitForEnterprise.class);
    }

    /**
     * Set the enterprise cache retention limit ({@code PUT /enterprises/{enterprise}/actions/cache/retention-limit}).
     *
     * @param enterprise enterprise slug
     * @param payload retention limit in days
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setEnterpriseCacheRetentionLimit(
            final String enterprise, final GitHubActionsCacheRetentionLimitForEnterprise payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_RETENTION_LIMIT, enterprise), payload));
    }

    /**
     * Get the enterprise cache storage limit ({@code GET /enterprises/{enterprise}/actions/cache/storage-limit}).
     *
     * @param enterprise enterprise slug
     * @return storage limit
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsCacheStorageLimitForEnterprise getEnterpriseCacheStorageLimit(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_STORAGE_LIMIT, enterprise), GitHubActionsCacheStorageLimitForEnterprise.class);
    }

    /**
     * Set the enterprise cache storage limit ({@code PUT /enterprises/{enterprise}/actions/cache/storage-limit}).
     *
     * @param enterprise enterprise slug
     * @param payload storage limit in GB
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setEnterpriseCacheStorageLimit(
            final String enterprise, final GitHubActionsCacheStorageLimitForEnterprise payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_STORAGE_LIMIT, enterprise), payload));
    }

    /**
     * Get the organization cache retention limit ({@code GET /organizations/{org}/actions/cache/retention-limit}).
     *
     * @param org organization name
     * @return retention limit
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsCacheRetentionLimitForOrganization getOrgCacheRetentionLimit(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_RETENTION_LIMIT, org), GitHubActionsCacheRetentionLimitForOrganization.class);
    }

    /**
     * Set the organization cache retention limit ({@code PUT /organizations/{org}/actions/cache/retention-limit}).
     *
     * @param org organization name
     * @param payload retention limit in days
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setOrgCacheRetentionLimit(
            final String org, final GitHubActionsCacheRetentionLimitForOrganization payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_RETENTION_LIMIT, org), payload));
    }

    /**
     * Get the organization cache storage limit ({@code GET /organizations/{org}/actions/cache/storage-limit}).
     *
     * @param org organization name
     * @return storage limit
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsCacheStorageLimitForOrganization getOrgCacheStorageLimit(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_STORAGE_LIMIT, org), GitHubActionsCacheStorageLimitForOrganization.class);
    }

    /**
     * Set the organization cache storage limit ({@code PUT /organizations/{org}/actions/cache/storage-limit}).
     *
     * @param org organization name
     * @param payload storage limit in GB
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setOrgCacheStorageLimit(final String org, final GitHubActionsCacheStorageLimitForOrganization payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_STORAGE_LIMIT, org), payload));
    }

    /**
     * Get the cache usage of an organization ({@code GET /orgs/{org}/actions/cache/usage}).
     *
     * @param org organization name
     * @return total active caches of the organization
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsCacheUsageOrgEnterprise getOrgCacheUsage(final String org) throws IOException {
        return caller.fetch(get(ORG_CACHE_USAGE, org), GitHubActionsCacheUsageOrgEnterprise.class);
    }

    /**
     * List cache usage per repository of an organization ({@code GET /orgs/{org}/actions/cache/usage-by-repository}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}.
     *
     * @param org organization name
     * @return first HTTP page of repository cache usages
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsCacheUsageByRepository> listOrgCacheUsageByRepository(final String org)
            throws IOException {
        return listOrgCacheUsageByRepository(org, GitHubPageRequest.byPage());
    }

    /**
     * List cache usage per repository of an organization ({@code GET /orgs/{org}/actions/cache/usage-by-repository}).
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of repository cache usages
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsCacheUsageByRepository> listOrgCacheUsageByRepository(
            final String org, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_CACHE_USAGE_BY_REPOSITORY, Map.of(), request, ORG_CACHE_USAGE_SPEC, org);
    }

    /**
     * Get the cache retention limit of a repository ({@code GET /repos/{owner}/{repo}/actions/cache/retention-limit}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return retention limit
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsCacheRetentionLimitForRepository getRepoCacheRetentionLimit(
            final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(REPO_RETENTION_LIMIT, owner, repo), GitHubActionsCacheRetentionLimitForRepository.class);
    }

    /**
     * Set the cache retention limit of a repository ({@code PUT /repos/{owner}/{repo}/actions/cache/retention-limit}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload retention limit in days
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setRepoCacheRetentionLimit(
            final String owner, final String repo, final GitHubActionsCacheRetentionLimitForRepository payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_RETENTION_LIMIT, owner, repo), payload));
    }

    /**
     * Get the cache storage limit of a repository ({@code GET /repos/{owner}/{repo}/actions/cache/storage-limit}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return storage limit
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubActionsCacheStorageLimitForRepository getRepoCacheStorageLimit(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(REPO_STORAGE_LIMIT, owner, repo), GitHubActionsCacheStorageLimitForRepository.class);
    }

    /**
     * Set the cache storage limit of a repository ({@code PUT /repos/{owner}/{repo}/actions/cache/storage-limit}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload storage limit in GB
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void setRepoCacheStorageLimit(
            final String owner, final String repo, final GitHubActionsCacheStorageLimitForRepository payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_STORAGE_LIMIT, owner, repo), payload));
    }

    /**
     * Get the cache usage of a repository ({@code GET /repos/{owner}/{repo}/actions/cache/usage}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return cache usage of the repository
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsCacheUsageByRepository getRepoCacheUsage(final String owner, final String repo)
            throws IOException {
        return caller.fetch(get(REPO_CACHE_USAGE, owner, repo), GitHubActionsCacheUsageByRepository.class);
    }

    /**
     * List caches of a repository ({@code GET /repos/{owner}/{repo}/actions/caches}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of caches
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsCache> listActionsCaches(final String owner, final String repo) throws IOException {
        return listActionsCaches(owner, repo, GitHubPageRequest.byPage());
    }

    /**
     * List caches of a repository ({@code GET /repos/{owner}/{repo}/actions/caches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param request pagination token
     * @return first HTTP page of caches
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsCache> listActionsCaches(
            final String owner, final String repo, final GitHubPageRequest request) throws IOException {
        return listActionsCaches(owner, repo, GitHubGetActionsCacheListFilter.empty(), request);
    }

    /**
     * List caches of a repository ({@code GET /repos/{owner}/{repo}/actions/caches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of caches
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubActionsCache> listActionsCaches(
            final String owner,
            final String repo,
            final GitHubGetActionsCacheListFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_CACHES, filter.toQueryParameters(), request, CACHES_SPEC, owner, repo);
    }

    /**
     * Delete caches by key ({@code DELETE /repos/{owner}/{repo}/actions/caches}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter cache key and optional ref
     * @return deleted caches
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubActionsCacheList deleteActionsCacheByKey(
            final String owner, final String repo, final GitHubDeleteActionsCacheByKeyFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                delete(REPO_CACHES, owner, repo).withQuery(filter.toQueryParameters()), GitHubActionsCacheList.class);
    }

    /**
     * Delete a cache by ID ({@code DELETE /repos/{owner}/{repo}/actions/caches/{cache_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param cacheId cache ID
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteActionsCacheById(final String owner, final String repo, final long cacheId) throws IOException {
        caller.send(delete(REPO_CACHE, owner, repo, cacheId));
    }

    /**
     * Get the Actions cache usage of an enterprise ({@code GET /enterprises/{enterprise}/actions/cache/usage}).
     *
     * @param enterprise enterprise slug
     * @return cache usage of the enterprise
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubActionsCacheUsageOrgEnterprise getEnterpriseCacheUsage(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(ENTERPRISE_CACHE_USAGE, enterprise), GitHubActionsCacheUsageOrgEnterprise.class);
    }

    /**
     * Get the cache usage policy of a repository ({@code GET /repos/{owner}/{repo}/actions/cache/usage-policy}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return usage policy
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubActionsCacheUsagePolicyForRepository getRepoCacheUsagePolicy(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(REPO_USAGE_POLICY, owner, repo), GitHubActionsCacheUsagePolicyForRepository.class);
    }

    /**
     * Set the cache usage policy of a repository ({@code PATCH /repos/{owner}/{repo}/actions/cache/usage-policy}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload usage policy
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void setRepoCacheUsagePolicy(
            final String owner, final String repo, final GitHubActionsCacheUsagePolicyForRepository payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(REPO_USAGE_POLICY, owner, repo), payload));
    }

    /**
     * Get the enterprise cache usage policy ({@code GET /enterprises/{enterprise}/actions/cache/usage-policy}).
     *
     * @param enterprise enterprise slug
     * @return usage policy
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubActionsCacheUsagePolicyEnterprise getEnterpriseCacheUsagePolicy(final String enterprise)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(ENTERPRISE_USAGE_POLICY, enterprise), GitHubActionsCacheUsagePolicyEnterprise.class);
    }

    /**
     * Set the enterprise cache usage policy ({@code PATCH /enterprises/{enterprise}/actions/cache/usage-policy}).
     *
     * @param enterprise enterprise slug
     * @param payload usage policy
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void setEnterpriseCacheUsagePolicy(
            final String enterprise, final GitHubActionsCacheUsagePolicyEnterprise payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(ENTERPRISE_USAGE_POLICY, enterprise), payload));
    }
}
