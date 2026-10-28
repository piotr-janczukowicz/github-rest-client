package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub check suites REST resource for API version {@code 2026-03-10}. */
public final class GitHubChecksSuitesApi extends GitHubResourceApi {

    private static final PathTemplate CHECK_SUITES = PathTemplate.of("/repos/{owner}/{repo}/check-suites");
    private static final PathTemplate CHECK_SUITES_PREFERENCES =
            PathTemplate.of("/repos/{owner}/{repo}/check-suites/preferences");
    private static final PathTemplate CHECK_SUITE =
            PathTemplate.of("/repos/{owner}/{repo}/check-suites/{check_suite_id}");
    private static final PathTemplate CHECK_SUITE_REREQUEST =
            PathTemplate.of("/repos/{owner}/{repo}/check-suites/{check_suite_id}/rerequest");
    private static final PathTemplate REF_CHECK_SUITES =
            PathTemplate.of("/repos/{owner}/{repo}/commits/{+ref}/check-suites");

    private static final GitHubListSpec<GitHubCheckSuite> CHECK_SUITES_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubCheckSuitesPage.class,
            GitHubCheckSuitesPage::checkSuites,
            GitHubCheckSuitesPage::totalCount);

    /**
     * Check suites resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubChecksSuitesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a check suite ({@code POST /repos/{owner}/{repo}/check-suites}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload check suite to create
     * @return created check suite
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCheckSuite createSuite(final String owner, final String repo, final GitHubCreateSuitePayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(CHECK_SUITES, owner, repo), payload), GitHubCheckSuite.class);
    }

    /**
     * Update repository preferences for check suites ({@code PATCH /repos/{owner}/{repo}/check-suites/preferences}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload preferences to set
     * @return updated preferences
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCheckSuitePreference updateSuitesPreferences(
            final String owner, final String repo, final GitHubSetSuitesPreferencesPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(CHECK_SUITES_PREFERENCES, owner, repo), payload), GitHubCheckSuitePreference.class);
    }

    /**
     * Get a check suite ({@code GET /repos/{owner}/{repo}/check-suites/{check_suite_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkSuiteId check suite id
     * @return check suite, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCheckSuite> getSuite(final String owner, final String repo, final long checkSuiteId)
            throws IOException {
        return caller.fetchOrEmpty(get(CHECK_SUITE, owner, repo, checkSuiteId), GitHubCheckSuite.class);
    }

    /**
     * Rerequest a check suite ({@code POST /repos/{owner}/{repo}/check-suites/{check_suite_id}/rerequest}). The
     * response body is empty.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkSuiteId check suite id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void rerequestSuite(final String owner, final String repo, final long checkSuiteId) throws IOException {
        caller.send(post(CHECK_SUITE_REREQUEST, owner, repo, checkSuiteId));
    }

    /**
     * List check suites for a Git reference ({@code GET /repos/{owner}/{repo}/commits/{ref}/check-suites}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit SHA, branch or tag name
     * @return first HTTP page of check suites
     * @throws IllegalArgumentException if {@code owner}, {@code repo} or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCheckSuite> listSuitesForRef(final String owner, final String repo, final String ref)
            throws IOException {
        return listSuitesForRef(owner, repo, ref, GitHubListSuitesForRefFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List check suites for a Git reference ({@code GET /repos/{owner}/{repo}/commits/{ref}/check-suites}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit SHA, branch or tag name
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of check suites
     * @throws IllegalArgumentException if {@code owner}, {@code repo} or {@code ref} is blank, or {@code request} is
     *     invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCheckSuite> listSuitesForRef(
            final String owner,
            final String repo,
            final String ref,
            final GitHubListSuitesForRefFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REF_CHECK_SUITES, filter.toQueryParameters(), request, CHECK_SUITES_SPEC, owner, repo, ref);
    }
}
