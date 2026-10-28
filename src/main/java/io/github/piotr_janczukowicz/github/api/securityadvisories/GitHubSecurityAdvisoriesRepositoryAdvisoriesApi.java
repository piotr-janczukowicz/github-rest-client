package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubFullRepository;
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
import java.util.Optional;

/**
 * GitHub repository security advisories REST resource for API version {@code 2026-03-10}.
 *
 * <p>Read and write organization and repository security advisories.
 */
public final class GitHubSecurityAdvisoriesRepositoryAdvisoriesApi extends GitHubResourceApi {

    private static final PathTemplate SECURITY_ADVISORY_FORKS =
            PathTemplate.of("/repos/{owner}/{repo}/security-advisories/{ghsa_id}/forks");
    private static final PathTemplate SECURITY_ADVISORY_CVE =
            PathTemplate.of("/repos/{owner}/{repo}/security-advisories/{ghsa_id}/cve");
    private static final PathTemplate SECURITY_ADVISORY =
            PathTemplate.of("/repos/{owner}/{repo}/security-advisories/{ghsa_id}");
    private static final PathTemplate SECURITY_ADVISORIES_REPORTS =
            PathTemplate.of("/repos/{owner}/{repo}/security-advisories/reports");
    private static final PathTemplate REPO_SECURITY_ADVISORIES =
            PathTemplate.of("/repos/{owner}/{repo}/security-advisories");
    private static final PathTemplate ORG_SECURITY_ADVISORIES = PathTemplate.of("/orgs/{org}/security-advisories");

    private static final JsonType<List<GitHubRepositoryAdvisory>> REPOSITORY_ADVISORIES_TYPE =
            JsonTypes.listOf(GitHubRepositoryAdvisory.class);

    /**
     * Repository security advisories resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization repository advisories ({@code GET /orgs/{org}/security-advisories}).
     *
     * @param org organization login
     * @return first HTTP page of repository advisories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubRepositoryAdvisory> listOrgRepositoryAdvisories(final String org) throws IOException {
        return listOrgRepositoryAdvisories(
                org, GitHubListRepositoryAdvisoriesFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List organization repository advisories ({@code GET /orgs/{org}/security-advisories}).
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of repository advisories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubRepositoryAdvisory> listOrgRepositoryAdvisories(
            final String org, final GitHubListRepositoryAdvisoriesFilter filter) throws IOException {
        return listOrgRepositoryAdvisories(org, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List organization repository advisories ({@code GET /orgs/{org}/security-advisories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of repository advisories
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubRepositoryAdvisory> listOrgRepositoryAdvisories(
            final String org, final GitHubListRepositoryAdvisoriesFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return list(
                ORG_SECURITY_ADVISORIES,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, REPOSITORY_ADVISORIES_TYPE),
                org);
    }

    /**
     * List repository advisories ({@code GET /repos/{owner}/{repo}/security-advisories}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of repository advisories
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubRepositoryAdvisory> listRepositoryAdvisories(final String owner, final String repo)
            throws IOException {
        return listRepositoryAdvisories(
                owner, repo, GitHubListRepositoryAdvisoriesFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List repository advisories ({@code GET /repos/{owner}/{repo}/security-advisories}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return first HTTP page of repository advisories
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubRepositoryAdvisory> listRepositoryAdvisories(
            final String owner, final String repo, final GitHubListRepositoryAdvisoriesFilter filter)
            throws IOException {
        return listRepositoryAdvisories(owner, repo, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List repository advisories ({@code GET /repos/{owner}/{repo}/security-advisories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of repository advisories
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a page or since
     *     variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubRepositoryAdvisory> listRepositoryAdvisories(
            final String owner,
            final String repo,
            final GitHubListRepositoryAdvisoriesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return list(
                REPO_SECURITY_ADVISORIES,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, REPOSITORY_ADVISORIES_TYPE),
                owner,
                repo);
    }

    /**
     * Get a repository advisory ({@code GET /repos/{owner}/{repo}/security-advisories/{ghsa_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ghsaId GHSA identifier
     * @return the repository advisory, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ghsaId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubRepositoryAdvisory> getRepositoryAdvisory(
            final String owner, final String repo, final String ghsaId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchOrEmpty(get(SECURITY_ADVISORY, owner, repo, ghsaId), GitHubRepositoryAdvisory.class);
    }

    /**
     * Create a repository advisory ({@code POST /repos/{owner}/{repo}/security-advisories}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload advisory to create
     * @return the created repository advisory
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubRepositoryAdvisory createRepositoryAdvisory(
            final String owner, final String repo, final GitHubCreateRepositoryAdvisoryPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_SECURITY_ADVISORIES, owner, repo), payload), GitHubRepositoryAdvisory.class);
    }

    /**
     * Create a private vulnerability report ({@code POST /repos/{owner}/{repo}/security-advisories/reports}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload report to create
     * @return the created repository advisory
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubRepositoryAdvisory createPrivateVulnerabilityReport(
            final String owner, final String repo, final GitHubCreatePrivateVulnerabilityReportPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(SECURITY_ADVISORIES_REPORTS, owner, repo), payload), GitHubRepositoryAdvisory.class);
    }

    /**
     * Update a repository advisory ({@code PATCH /repos/{owner}/{repo}/security-advisories/{ghsa_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ghsaId GHSA identifier
     * @param payload fields to update
     * @return the updated repository advisory
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ghsaId} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubRepositoryAdvisory updateRepositoryAdvisory(
            final String owner,
            final String repo,
            final String ghsaId,
            final GitHubUpdateRepositoryAdvisoryPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(SECURITY_ADVISORY, owner, repo, ghsaId), payload), GitHubRepositoryAdvisory.class);
    }

    /**
     * Request a CVE for a repository advisory ({@code POST /repos/{owner}/{repo}/security-advisories/{ghsa_id}/cve}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ghsaId GHSA identifier
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ghsaId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void createRepositoryAdvisoryCveRequest(final String owner, final String repo, final String ghsaId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        caller.send(post(SECURITY_ADVISORY_CVE, owner, repo, ghsaId));
    }

    /**
     * Create a temporary private fork ({@code POST /repos/{owner}/{repo}/security-advisories/{ghsa_id}/forks}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ghsaId GHSA identifier
     * @return the private fork repository
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code ghsaId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubFullRepository createFork(final String owner, final String repo, final String ghsaId)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(post(SECURITY_ADVISORY_FORKS, owner, repo, ghsaId), GitHubFullRepository.class);
    }
}
