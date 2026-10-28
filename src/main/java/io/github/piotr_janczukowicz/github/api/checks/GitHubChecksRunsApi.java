package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
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

/** GitHub check runs REST resource for API version {@code 2026-03-10}. */
public final class GitHubChecksRunsApi extends GitHubResourceApi {

    private static final PathTemplate CHECK_RUNS = PathTemplate.of("/repos/{owner}/{repo}/check-runs");
    private static final PathTemplate CHECK_RUN = PathTemplate.of("/repos/{owner}/{repo}/check-runs/{check_run_id}");
    private static final PathTemplate CHECK_RUN_ANNOTATIONS =
            PathTemplate.of("/repos/{owner}/{repo}/check-runs/{check_run_id}/annotations");
    private static final PathTemplate CHECK_RUN_REREQUEST =
            PathTemplate.of("/repos/{owner}/{repo}/check-runs/{check_run_id}/rerequest");
    private static final PathTemplate SUITE_CHECK_RUNS =
            PathTemplate.of("/repos/{owner}/{repo}/check-suites/{check_suite_id}/check-runs");
    private static final PathTemplate REF_CHECK_RUNS =
            PathTemplate.of("/repos/{owner}/{repo}/commits/{+ref}/check-runs");

    private static final JsonType<List<GitHubCheckAnnotation>> ANNOTATIONS_TYPE =
            JsonTypes.listOf(GitHubCheckAnnotation.class);

    private static final GitHubListSpec<GitHubCheckRun> CHECK_RUNS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE,
            GitHubCheckRunsPage.class,
            GitHubCheckRunsPage::checkRuns,
            GitHubCheckRunsPage::totalCount);

    /**
     * Check runs resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubChecksRunsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create a check run ({@code POST /repos/{owner}/{repo}/check-runs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload check run to create
     * @return created check run
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCheckRun createCheckRun(
            final String owner, final String repo, final GitHubCreateCheckRunPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(CHECK_RUNS, owner, repo), payload), GitHubCheckRun.class);
    }

    /**
     * Get a check run ({@code GET /repos/{owner}/{repo}/check-runs/{check_run_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkRunId check run id
     * @return check run, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCheckRun> getCheckRun(final String owner, final String repo, final long checkRunId)
            throws IOException {
        return caller.fetchOrEmpty(get(CHECK_RUN, owner, repo, checkRunId), GitHubCheckRun.class);
    }

    /**
     * Update a check run ({@code PATCH /repos/{owner}/{repo}/check-runs/{check_run_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkRunId check run id
     * @param payload fields to update
     * @return updated check run
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCheckRun updateCheckRun(
            final String owner, final String repo, final long checkRunId, final GitHubUpdateCheckRunPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(CHECK_RUN, owner, repo, checkRunId), payload), GitHubCheckRun.class);
    }

    /**
     * List check run annotations ({@code GET /repos/{owner}/{repo}/check-runs/{check_run_id}/annotations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkRunId check run id
     * @return first HTTP page of annotations
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCheckAnnotation> listAnnotations(
            final String owner, final String repo, final long checkRunId) throws IOException {
        return listAnnotations(owner, repo, checkRunId, GitHubPageRequest.byPage());
    }

    /**
     * List check run annotations ({@code GET /repos/{owner}/{repo}/check-runs/{check_run_id}/annotations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkRunId check run id
     * @param request pagination token
     * @return HTTP page of annotations
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCheckAnnotation> listAnnotations(
            final String owner, final String repo, final long checkRunId, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(CHECK_RUN_ANNOTATIONS, Map.of(), request, ANNOTATIONS_TYPE, owner, repo, checkRunId);
    }

    /**
     * Rerequest a check run ({@code POST /repos/{owner}/{repo}/check-runs/{check_run_id}/rerequest}). The response body
     * is empty.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkRunId check run id
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void rerequestRun(final String owner, final String repo, final long checkRunId) throws IOException {
        caller.send(post(CHECK_RUN_REREQUEST, owner, repo, checkRunId));
    }

    /**
     * List check runs in a check suite ({@code GET /repos/{owner}/{repo}/check-suites/{check_suite_id}/check-runs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkSuiteId check suite id
     * @return first HTTP page of check runs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCheckRun> listForSuite(final String owner, final String repo, final long checkSuiteId)
            throws IOException {
        return listForSuite(owner, repo, checkSuiteId, GitHubListForSuiteFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List check runs in a check suite ({@code GET /repos/{owner}/{repo}/check-suites/{check_suite_id}/check-runs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param checkSuiteId check suite id
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of check runs
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCheckRun> listForSuite(
            final String owner,
            final String repo,
            final long checkSuiteId,
            final GitHubListForSuiteFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(SUITE_CHECK_RUNS, filter.toQueryParameters(), request, CHECK_RUNS_SPEC, owner, repo, checkSuiteId);
    }

    /**
     * List check runs for a Git reference ({@code GET /repos/{owner}/{repo}/commits/{ref}/check-runs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit SHA, branch or tag name
     * @return first HTTP page of check runs
     * @throws IllegalArgumentException if {@code owner}, {@code repo} or {@code ref} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCheckRun> listForRef(final String owner, final String repo, final String ref)
            throws IOException {
        return listForRef(owner, repo, ref, GitHubListForRefFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List check runs for a Git reference ({@code GET /repos/{owner}/{repo}/commits/{ref}/check-runs}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ref commit SHA, branch or tag name
     * @param filter query filters
     * @param request pagination token
     * @return HTTP page of check runs
     * @throws IllegalArgumentException if {@code owner}, {@code repo} or {@code ref} is blank, or {@code request} is
     *     invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCheckRun> listForRef(
            final String owner,
            final String repo,
            final String ref,
            final GitHubListForRefFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REF_CHECK_RUNS, filter.toQueryParameters(), request, CHECK_RUNS_SPEC, owner, repo, ref);
    }
}
