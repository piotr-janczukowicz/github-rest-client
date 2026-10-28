package io.github.piotr_janczukowicz.github.api.secretscanning;

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
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/**
 * GitHub Secret Scanning custom patterns REST resource for API version {@code 2026-03-10}.
 *
 * <p>Manage custom patterns for organizations and repositories.
 */
public final class GitHubSecretScanningCustomPatternsApi extends GitHubResourceApi {

    private static final PathTemplate REPO_SECRET_SCANNING_CUSTOM_PATTERN =
            PathTemplate.of("/repos/{owner}/{repo}/secret-scanning/custom-patterns/{pattern_id}");
    private static final PathTemplate ORG_SECRET_SCANNING_CUSTOM_PATTERN =
            PathTemplate.of("/orgs/{org}/secret-scanning/custom-patterns/{pattern_id}");
    private static final PathTemplate REPO_SECRET_SCANNING_CUSTOM_PATTERNS =
            PathTemplate.of("/repos/{owner}/{repo}/secret-scanning/custom-patterns");
    private static final PathTemplate ORG_SECRET_SCANNING_CUSTOM_PATTERNS =
            PathTemplate.of("/orgs/{org}/secret-scanning/custom-patterns");
    private static final PathTemplate ENTERPRISE_SECRET_SCANNING_CUSTOM_PATTERN =
            PathTemplate.of("/enterprises/{enterprise}/secret-scanning/custom-patterns/{pattern_id}");
    private static final PathTemplate ENTERPRISE_SECRET_SCANNING_CUSTOM_PATTERNS =
            PathTemplate.of("/enterprises/{enterprise}/secret-scanning/custom-patterns");

    private static final JsonType<List<GitHubSecretScanningCustomPattern>> CUSTOM_PATTERNS_TYPE =
            JsonTypes.listOf(GitHubSecretScanningCustomPattern.class);

    /**
     * Secret scanning custom patterns resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecretScanningCustomPatternsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization custom patterns ({@code GET /orgs/{org}/secret-scanning/custom-patterns}).
     *
     * @param org organization login
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSecretScanningCustomPattern> listOrgCustomPatterns(final String org) throws IOException {
        return listOrgCustomPatterns(
                org, GitHubListSecretScanningCustomPatternsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List organization custom patterns ({@code GET /orgs/{org}/secret-scanning/custom-patterns}).
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSecretScanningCustomPattern> listOrgCustomPatterns(
            final String org, final GitHubListSecretScanningCustomPatternsFilter filter) throws IOException {
        return listOrgCustomPatterns(org, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List organization custom patterns ({@code GET /orgs/{org}/secret-scanning/custom-patterns}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSecretScanningCustomPattern> listOrgCustomPatterns(
            final String org,
            final GitHubListSecretScanningCustomPatternsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return list(
                ORG_SECRET_SCANNING_CUSTOM_PATTERNS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, CUSTOM_PATTERNS_TYPE)
                        .withoutPerPage(),
                org);
    }

    /**
     * List repository custom patterns ({@code GET /repos/{owner}/{repo}/secret-scanning/custom-patterns}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSecretScanningCustomPattern> listRepoCustomPatterns(final String owner, final String repo)
            throws IOException {
        return listRepoCustomPatterns(
                owner, repo, GitHubListSecretScanningCustomPatternsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List repository custom patterns ({@code GET /repos/{owner}/{repo}/secret-scanning/custom-patterns}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSecretScanningCustomPattern> listRepoCustomPatterns(
            final String owner, final String repo, final GitHubListSecretScanningCustomPatternsFilter filter)
            throws IOException {
        return listRepoCustomPatterns(owner, repo, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List repository custom patterns ({@code GET /repos/{owner}/{repo}/secret-scanning/custom-patterns}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a page or since
     *     variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubSecretScanningCustomPattern> listRepoCustomPatterns(
            final String owner,
            final String repo,
            final GitHubListSecretScanningCustomPatternsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return list(
                REPO_SECRET_SCANNING_CUSTOM_PATTERNS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, CUSTOM_PATTERNS_TYPE)
                        .withoutPerPage(),
                owner,
                repo);
    }

    /**
     * Bulk create organization custom patterns ({@code POST /orgs/{org}/secret-scanning/custom-patterns}).
     *
     * @param org organization login
     * @param payload patterns to create
     * @return created patterns
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubBulkCreateSecretScanningCustomPatternsResult bulkCreateOrgCustomPatterns(
            final String org, final GitHubBulkCreateSecretScanningCustomPatternsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_SECRET_SCANNING_CUSTOM_PATTERNS, org), payload),
                GitHubBulkCreateSecretScanningCustomPatternsResult.class);
    }

    /**
     * Bulk create repository custom patterns ({@code POST /repos/{owner}/{repo}/secret-scanning/custom-patterns}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload patterns to create
     * @return created patterns
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubBulkCreateSecretScanningCustomPatternsResult bulkCreateRepoCustomPatterns(
            final String owner, final String repo, final GitHubBulkCreateSecretScanningCustomPatternsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(REPO_SECRET_SCANNING_CUSTOM_PATTERNS, owner, repo), payload),
                GitHubBulkCreateSecretScanningCustomPatternsResult.class);
    }

    /**
     * Bulk delete organization custom patterns ({@code DELETE /orgs/{org}/secret-scanning/custom-patterns}).
     *
     * @param org organization login
     * @param payload patterns to delete
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void bulkDeleteOrgCustomPatterns(
            final String org, final GitHubBulkDeleteSecretScanningCustomPatternsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(ORG_SECRET_SCANNING_CUSTOM_PATTERNS, org), payload));
    }

    /**
     * Bulk delete repository custom patterns ({@code DELETE /repos/{owner}/{repo}/secret-scanning/custom-patterns}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload patterns to delete
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void bulkDeleteRepoCustomPatterns(
            final String owner, final String repo, final GitHubBulkDeleteSecretScanningCustomPatternsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(REPO_SECRET_SCANNING_CUSTOM_PATTERNS, owner, repo), payload));
    }

    /**
     * Update an organization custom pattern ({@code PATCH /orgs/{org}/secret-scanning/custom-patterns/{pattern_id}}).
     *
     * @param org organization login
     * @param patternId custom pattern ID
     * @param payload fields to update
     * @return updated custom pattern
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubSecretScanningCustomPattern updateOrgCustomPattern(
            final String org, final long patternId, final GitHubUpdateSecretScanningCustomPatternPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ORG_SECRET_SCANNING_CUSTOM_PATTERN, org, patternId), payload),
                GitHubSecretScanningCustomPattern.class);
    }

    /**
     * Update a repository custom pattern ({@code PATCH
     * /repos/{owner}/{repo}/secret-scanning/custom-patterns/{pattern_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param patternId custom pattern ID
     * @param payload fields to update
     * @return updated custom pattern
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubSecretScanningCustomPattern updateRepoCustomPattern(
            final String owner,
            final String repo,
            final long patternId,
            final GitHubUpdateSecretScanningCustomPatternPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(REPO_SECRET_SCANNING_CUSTOM_PATTERN, owner, repo, patternId), payload),
                GitHubSecretScanningCustomPattern.class);
    }

    /**
     * List enterprise custom patterns ({@code GET /enterprises/{enterprise}/secret-scanning/custom-patterns}) with no
     * filter.
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listEnterpriseCustomPatterns(String,
     * GitHubListEnterpriseCustomPatternsFilter, GitHubPageRequest)}.
     *
     * @param enterprise enterprise slug
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubSecretScanningCustomPattern> listEnterpriseCustomPatterns(final String enterprise)
            throws IOException {
        return listEnterpriseCustomPatterns(
                enterprise, GitHubListEnterpriseCustomPatternsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List enterprise custom patterns ({@code GET /enterprises/{enterprise}/secret-scanning/custom-patterns}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}.
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubSecretScanningCustomPattern> listEnterpriseCustomPatterns(
            final String enterprise, final GitHubListEnterpriseCustomPatternsFilter filter) throws IOException {
        return listEnterpriseCustomPatterns(enterprise, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List enterprise custom patterns ({@code GET /enterprises/{enterprise}/secret-scanning/custom-patterns}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of custom patterns
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubSecretScanningCustomPattern> listEnterpriseCustomPatterns(
            final String enterprise,
            final GitHubListEnterpriseCustomPatternsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENTERPRISE_SECRET_SCANNING_CUSTOM_PATTERNS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, CUSTOM_PATTERNS_TYPE)
                        .withoutPerPage(),
                enterprise);
    }

    /**
     * Bulk create enterprise custom patterns ({@code POST /enterprises/{enterprise}/secret-scanning/custom-patterns}).
     *
     * @param enterprise enterprise slug
     * @param payload patterns to create
     * @return created patterns
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubBulkCreateEnterpriseCustomPatternsResult bulkCreateEnterpriseCustomPatterns(
            final String enterprise, final GitHubBulkCreateEnterpriseCustomPatternsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_SECRET_SCANNING_CUSTOM_PATTERNS, enterprise), payload),
                GitHubBulkCreateEnterpriseCustomPatternsResult.class);
    }

    /**
     * Bulk delete enterprise custom patterns ({@code DELETE
     * /enterprises/{enterprise}/secret-scanning/custom-patterns}).
     *
     * @param enterprise enterprise slug
     * @param payload patterns to delete
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void bulkDeleteEnterpriseCustomPatterns(
            final String enterprise, final GitHubBulkDeleteEnterpriseCustomPatternsPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(delete(ENTERPRISE_SECRET_SCANNING_CUSTOM_PATTERNS, enterprise), payload));
    }

    /**
     * Update an enterprise custom pattern ({@code PATCH
     * /enterprises/{enterprise}/secret-scanning/custom-patterns/{pattern_id}}).
     *
     * @param enterprise enterprise slug
     * @param patternId custom pattern ID
     * @param payload fields to update
     * @return updated custom pattern
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubSecretScanningCustomPattern updateEnterpriseCustomPattern(
            final String enterprise, final long patternId, final GitHubUpdateSecretScanningCustomPatternPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENTERPRISE_SECRET_SCANNING_CUSTOM_PATTERN, enterprise, patternId), payload),
                GitHubSecretScanningCustomPattern.class);
    }
}
