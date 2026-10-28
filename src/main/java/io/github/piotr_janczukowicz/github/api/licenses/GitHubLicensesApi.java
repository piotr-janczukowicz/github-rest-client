package io.github.piotr_janczukowicz.github.api.licenses;

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
import java.util.Optional;

/** GitHub licenses REST resource for API version {@code 2026-03-10}. */
public final class GitHubLicensesApi extends GitHubResourceApi {

    private static final PathTemplate LICENSES = PathTemplate.of("/licenses");
    private static final PathTemplate REPO_LICENSE = PathTemplate.of("/repos/{owner}/{repo}/license");
    private static final PathTemplate LICENSE = PathTemplate.of("/licenses/{license}");

    private static final JsonType<List<GitHubLicenseSimple>> LICENSES_TYPE =
            JsonTypes.listOf(GitHubLicenseSimple.class);

    /**
     * Licenses resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubLicensesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List commonly used licenses ({@code GET /licenses}).
     *
     * @return first HTTP page of licenses
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLicenseSimple> listCommonlyUsed() throws IOException {
        return listCommonlyUsed(GitHubGetAllCommonlyUsedFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List commonly used licenses ({@code GET /licenses}).
     *
     * @param request pagination token
     * @return first HTTP page of licenses
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLicenseSimple> listCommonlyUsed(final GitHubPageRequest request) throws IOException {
        return listCommonlyUsed(GitHubGetAllCommonlyUsedFilter.empty(), request);
    }

    /**
     * List commonly used licenses ({@code GET /licenses}).
     *
     * @param filter query filters
     * @return first HTTP page of licenses
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLicenseSimple> listCommonlyUsed(final GitHubGetAllCommonlyUsedFilter filter)
            throws IOException {
        return listCommonlyUsed(filter, GitHubPageRequest.byPage());
    }

    /**
     * List commonly used licenses ({@code GET /licenses}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of licenses
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLicenseSimple> listCommonlyUsed(
            final GitHubGetAllCommonlyUsedFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(LICENSES, filter.toQueryParameters(), request, LICENSES_TYPE);
    }

    /**
     * Get a license ({@code GET /licenses/{license}}).
     *
     * @param license license key
     * @return license, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code license} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubLicense> get(final String license) throws IOException {
        return caller.fetchOrEmpty(super.get(LICENSE, license), GitHubLicense.class);
    }

    /**
     * Get the license for a repository ({@code GET /repos/{owner}/{repo}/license}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return license content, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubLicenseContent> getForRepo(final String owner, final String repo) throws IOException {
        return getForRepo(owner, repo, GitHubGetForRepoFilter.empty());
    }

    /**
     * Get the license for a repository ({@code GET /repos/{owner}/{repo}/license}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return license content, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubLicenseContent> getForRepo(
            final String owner, final String repo, final GitHubGetForRepoFilter filter) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                super.get(REPO_LICENSE, owner, repo).withQuery(filter.toQueryParameters()), GitHubLicenseContent.class);
    }
}
