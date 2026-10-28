package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
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
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub code scanning REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodeScanningCodeScanningApi extends GitHubResourceApi {

    private static final String SARIF_MEDIA_TYPE = "application/sarif+json";

    private static final PathTemplate ENTERPRISE_CODE_SCANNING_ALERTS =
            PathTemplate.of("/enterprises/{enterprise}/code-scanning/alerts");
    private static final PathTemplate ORG_CODE_SCANNING_AI_SCAN = PathTemplate.of("/orgs/{org}/code-scanning/ai-scan");
    private static final PathTemplate ORG_CODE_SCANNING_ALERTS = PathTemplate.of("/orgs/{org}/code-scanning/alerts");
    private static final PathTemplate REPO_CODE_SCANNING_AI_SCAN =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/ai-scan");
    private static final PathTemplate REPO_CODE_SCANNING_ALERTS =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/alerts");
    private static final PathTemplate CODE_SCANNING_ALERT =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/alerts/{alert_number}");
    private static final PathTemplate CODE_SCANNING_AUTOFIX =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/autofix");
    private static final PathTemplate CODE_SCANNING_AUTOFIX_COMMITS =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/autofix/commits");
    private static final PathTemplate CODE_SCANNING_ALERT_INSTANCES =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/instances");
    private static final PathTemplate CODE_SCANNING_ANALYSES =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/analyses");
    private static final PathTemplate CODE_SCANNING_ANALYSIS =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/analyses/{analysis_id}");
    private static final PathTemplate CODEQL_DATABASES =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/codeql/databases");
    private static final PathTemplate CODEQL_DATABASE =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/codeql/databases/{language}");
    private static final PathTemplate CODEQL_VARIANT_ANALYSES =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/codeql/variant-analyses");
    private static final PathTemplate CODEQL_VARIANT_ANALYSIS =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/codeql/variant-analyses/{codeql_variant_analysis_id}");
    private static final PathTemplate CODEQL_VARIANT_ANALYSIS_REPO_TASK = PathTemplate.of(
            "/repos/{owner}/{repo}/code-scanning/codeql/variant-analyses/{codeql_variant_analysis_id}/repos/{repo_owner}/{repo_name}");
    private static final PathTemplate CODE_SCANNING_DEFAULT_SETUP =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/default-setup");
    private static final PathTemplate CODE_SCANNING_SARIFS =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/sarifs");
    private static final PathTemplate CODE_SCANNING_SARIF =
            PathTemplate.of("/repos/{owner}/{repo}/code-scanning/sarifs/{sarif_id}");

    private static final JsonType<List<GitHubCodeScanningAlertItems>> ALERT_ITEMS_TYPE =
            JsonTypes.listOf(GitHubCodeScanningAlertItems.class);
    private static final JsonType<List<GitHubCodeScanningAlertInstanceList>> ALERT_INSTANCES_TYPE =
            JsonTypes.listOf(GitHubCodeScanningAlertInstanceList.class);
    private static final JsonType<List<GitHubCodeScanningAnalysis>> ANALYSES_TYPE =
            JsonTypes.listOf(GitHubCodeScanningAnalysis.class);
    private static final JsonType<List<GitHubCodeScanningCodeqlDatabase>> CODEQL_DATABASES_TYPE =
            JsonTypes.listOf(GitHubCodeScanningCodeqlDatabase.class);

    private static final GitHubListSpec<GitHubCodeScanningOrganizationAlertItems> ORG_ALERTS_SPEC =
            GitHubListSpec.array(
                    GitHubPageRequestKindValidators.PAGE_OR_CURSOR,
                    JsonTypes.listOf(GitHubCodeScanningOrganizationAlertItems.class));
    private static final GitHubListSpec<GitHubCodeScanningAlertItems> REPO_ALERTS_SPEC =
            GitHubListSpec.array(GitHubPageRequestKindValidators.PAGE_OR_CURSOR, ALERT_ITEMS_TYPE);

    /**
     * Code scanning resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodeScanningCodeScanningApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List code scanning alerts for an enterprise ({@code GET /enterprises/{enterprise}/code-scanning/alerts}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #listAlertsForEnterprise(String,
     * GitHubListAlertsForEnterpriseFilter, GitHubPageRequest)}.
     *
     * @param enterprise enterprise slug
     * @return first page of alerts
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubCodeScanningOrganizationAlertItems> listAlertsForEnterprise(final String enterprise)
            throws IOException {
        return listAlertsForEnterprise(
                enterprise, GitHubListAlertsForEnterpriseFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List code scanning alerts for an enterprise ({@code GET /enterprises/{enterprise}/code-scanning/alerts}).
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @param request page or cursor pagination token
     * @return page of alerts
     * @throws IllegalArgumentException if {@code enterprise} is blank or {@code request} is not a page or cursor
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubCodeScanningOrganizationAlertItems> listAlertsForEnterprise(
            final String enterprise, final GitHubListAlertsForEnterpriseFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ENTERPRISE_CODE_SCANNING_ALERTS, filter.toQueryParameters(), request, ORG_ALERTS_SPEC, enterprise);
    }

    /**
     * List code scanning alerts for an organization ({@code GET /orgs/{org}/code-scanning/alerts}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #listAlertsForOrg(String,
     * GitHubListAlertsForOrgFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first page of alerts
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeScanningOrganizationAlertItems> listAlertsForOrg(final String org) throws IOException {
        return listAlertsForOrg(org, GitHubListAlertsForOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List code scanning alerts for an organization ({@code GET /orgs/{org}/code-scanning/alerts}).
     *
     * @param org organization name
     * @param filter query filters
     * @param request page or cursor pagination token
     * @return page of alerts
     * @throws IllegalArgumentException if {@code org} is blank or {@code request} is not a page or cursor
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeScanningOrganizationAlertItems> listAlertsForOrg(
            final String org, final GitHubListAlertsForOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_CODE_SCANNING_ALERTS, filter.toQueryParameters(), request, ORG_ALERTS_SPEC, org);
    }

    /**
     * List code scanning alerts for a repository ({@code GET /repos/{owner}/{repo}/code-scanning/alerts}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #listAlertsForRepo(String, String,
     * GitHubListAlertsForRepoFilter, GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first page of alerts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeScanningAlertItems> listAlertsForRepo(final String owner, final String repo)
            throws IOException {
        return listAlertsForRepo(owner, repo, GitHubListAlertsForRepoFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List code scanning alerts for a repository ({@code GET /repos/{owner}/{repo}/code-scanning/alerts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request page or cursor pagination token
     * @return page of alerts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank or {@code request} is not a page or
     *     cursor
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeScanningAlertItems> listAlertsForRepo(
            final String owner,
            final String repo,
            final GitHubListAlertsForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(REPO_CODE_SCANNING_ALERTS, filter.toQueryParameters(), request, REPO_ALERTS_SPEC, owner, repo);
    }

    /**
     * Get a code scanning alert ({@code GET /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @return alert, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeScanningAlert> getAlert(final String owner, final String repo, final int alertNumber)
            throws IOException {
        return caller.fetchOrEmpty(get(CODE_SCANNING_ALERT, owner, repo, alertNumber), GitHubCodeScanningAlert.class);
    }

    /**
     * Update a code scanning alert ({@code PATCH /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @param payload fields to update
     * @return updated alert
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCodeScanningAlert updateAlert(
            final String owner, final String repo, final int alertNumber, final GitHubUpdateAlertPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(CODE_SCANNING_ALERT, owner, repo, alertNumber), payload),
                GitHubCodeScanningAlert.class);
    }

    /**
     * Get the status of an autofix for a code scanning alert ({@code GET
     * /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/autofix}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @return autofix status, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodeScanningAutofix> getAutofix(final String owner, final String repo, final int alertNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(CODE_SCANNING_AUTOFIX, owner, repo, alertNumber), GitHubCodeScanningAutofix.class);
    }

    /**
     * Create an autofix for a code scanning alert ({@code POST
     * /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/autofix}). GitHub answers HTTP 202 while the autofix is
     * generated; the body has the same shape as HTTP 200.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @return autofix
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodeScanningAutofix createAutofix(final String owner, final String repo, final int alertNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(post(CODE_SCANNING_AUTOFIX, owner, repo, alertNumber), GitHubCodeScanningAutofix.class);
    }

    /**
     * Commit an autofix for a code scanning alert ({@code POST
     * /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/autofix/commits}). The body is optional.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @param payload commit details, or {@code null} to send no body
     * @return commit result
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodeScanningAutofixCommitsResponse commitAutofix(
            final String owner,
            final String repo,
            final int alertNumber,
            final @Nullable GitHubCodeScanningAutofixCommits payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                caller.jsonIfPresent(post(CODE_SCANNING_AUTOFIX_COMMITS, owner, repo, alertNumber), payload),
                GitHubCodeScanningAutofixCommitsResponse.class);
    }

    /**
     * List instances of a code scanning alert ({@code GET
     * /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/instances}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #listAlertInstances(String, String,
     * int, GitHubListAlertInstancesFilter, GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @return first page of alert instances
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeScanningAlertInstanceList> listAlertInstances(
            final String owner, final String repo, final int alertNumber) throws IOException {
        return listAlertInstances(
                owner, repo, alertNumber, GitHubListAlertInstancesFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List instances of a code scanning alert ({@code GET
     * /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/instances}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @param filter query filters
     * @param request numbered page pagination token
     * @return page of alert instances
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank or {@code request} is not a page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeScanningAlertInstanceList> listAlertInstances(
            final String owner,
            final String repo,
            final int alertNumber,
            final GitHubListAlertInstancesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                CODE_SCANNING_ALERT_INSTANCES,
                filter.toQueryParameters(),
                request,
                ALERT_INSTANCES_TYPE,
                owner,
                repo,
                alertNumber);
    }

    /**
     * List code scanning analyses for a repository ({@code GET /repos/{owner}/{repo}/code-scanning/analyses}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()} and an empty filter. See {@link #listRecentAnalyses(String, String,
     * GitHubListRecentAnalysesFilter, GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first page of analyses
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeScanningAnalysis> listRecentAnalyses(final String owner, final String repo)
            throws IOException {
        return listRecentAnalyses(owner, repo, GitHubListRecentAnalysesFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List code scanning analyses for a repository ({@code GET /repos/{owner}/{repo}/code-scanning/analyses}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request numbered page pagination token
     * @return page of analyses
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank or {@code request} is not a page
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeScanningAnalysis> listRecentAnalyses(
            final String owner,
            final String repo,
            final GitHubListRecentAnalysesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(CODE_SCANNING_ANALYSES, filter.toQueryParameters(), request, ANALYSES_TYPE, owner, repo);
    }

    /**
     * Get a code scanning analysis for a repository ({@code GET
     * /repos/{owner}/{repo}/code-scanning/analyses/{analysis_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param analysisId analysis id
     * @return analysis, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeScanningAnalysis> getAnalysis(
            final String owner, final String repo, final long analysisId) throws IOException {
        return caller.fetchOrEmpty(
                get(CODE_SCANNING_ANALYSIS, owner, repo, analysisId), GitHubCodeScanningAnalysis.class);
    }

    /**
     * Get the raw SARIF of a code scanning analysis ({@code GET
     * /repos/{owner}/{repo}/code-scanning/analyses/{analysis_id}} with {@code Accept: application/sarif+json}).
     *
     * <p><strong>Convenience overload, not part of the OpenAPI schema.</strong> The schema maps this path to
     * {@link #getAnalysis(String, String, long)}; the SARIF representation is selected by the {@code Accept} media
     * type. The body is held in memory, up to the response size limit of the transport.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param analysisId analysis id
     * @return SARIF document as text, or an empty optional on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<String> getAnalysisSarif(final String owner, final String repo, final long analysisId)
            throws IOException {
        return fetchTextOrEmpty(get(CODE_SCANNING_ANALYSIS, owner, repo, analysisId), SARIF_MEDIA_TYPE);
    }

    /**
     * Delete a code scanning analysis from a repository ({@code DELETE
     * /repos/{owner}/{repo}/code-scanning/analyses/{analysis_id}}).
     *
     * <p>Uses an empty filter. See {@link #deleteAnalysis(String, String, long, GitHubDeleteAnalysisFilter)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param analysisId analysis id
     * @return deletion result
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCodeScanningAnalysisDeletion deleteAnalysis(
            final String owner, final String repo, final long analysisId) throws IOException {
        return deleteAnalysis(owner, repo, analysisId, GitHubDeleteAnalysisFilter.empty());
    }

    /**
     * Delete a code scanning analysis from a repository ({@code DELETE
     * /repos/{owner}/{repo}/code-scanning/analyses/{analysis_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param analysisId analysis id
     * @param filter query filters (confirmation of deletion of the last analysis of a category)
     * @return deletion result
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCodeScanningAnalysisDeletion deleteAnalysis(
            final String owner, final String repo, final long analysisId, final GitHubDeleteAnalysisFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                delete(CODE_SCANNING_ANALYSIS, owner, repo, analysisId).withQuery(filter.toQueryParameters()),
                GitHubCodeScanningAnalysisDeletion.class);
    }

    /**
     * List CodeQL databases for a repository ({@code GET /repos/{owner}/{repo}/code-scanning/codeql/databases}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return CodeQL databases
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubCodeScanningCodeqlDatabase> listCodeqlDatabases(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(CODEQL_DATABASES, owner, repo), CODEQL_DATABASES_TYPE);
    }

    /**
     * Get a CodeQL database for a repository ({@code GET
     * /repos/{owner}/{repo}/code-scanning/codeql/databases/{language}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param language language of the database
     * @return CodeQL database, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code language} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodeScanningCodeqlDatabase> getCodeqlDatabase(
            final String owner, final String repo, final String language) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(CODEQL_DATABASE, owner, repo, language), GitHubCodeScanningCodeqlDatabase.class);
    }

    /**
     * Get the download location of a CodeQL database archive ({@code GET
     * /repos/{owner}/{repo}/code-scanning/codeql/databases/{language}} with {@code Accept: application/zip}). GitHub
     * answers HTTP 302 with a temporary download URL. This helper is not a schema-mapped operation; the JSON form is
     * {@link #getCodeqlDatabase(String, String, String)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param language language of the database
     * @return redirect location of the archive
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code language} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws IllegalStateException if the response is successful (HTTP 2xx) and so is not a redirect
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public URI getCodeqlDatabaseDownloadLocation(final String owner, final String repo, final String language)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.redirectLocation(
                get(CODEQL_DATABASE, owner, repo, language).withHeader(ACCEPT, "application/zip"));
    }

    /**
     * Delete a CodeQL database ({@code DELETE /repos/{owner}/{repo}/code-scanning/codeql/databases/{language}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param language language of the database
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code language} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteCodeqlDatabase(final String owner, final String repo, final String language) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(CODEQL_DATABASE, owner, repo, language));
    }

    /**
     * Create a CodeQL variant analysis ({@code POST /repos/{owner}/{repo}/code-scanning/codeql/variant-analyses}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload variant analysis to create
     * @return created variant analysis
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodeScanningVariantAnalysis createVariantAnalysis(
            final String owner, final String repo, final GitHubCreateVariantAnalysisPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(CODEQL_VARIANT_ANALYSES, owner, repo), payload),
                GitHubCodeScanningVariantAnalysis.class);
    }

    /**
     * Get the summary of a CodeQL variant analysis ({@code GET
     * /repos/{owner}/{repo}/code-scanning/codeql/variant-analyses/{codeql_variant_analysis_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param codeqlVariantAnalysisId variant analysis id
     * @return variant analysis, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodeScanningVariantAnalysis> getVariantAnalysis(
            final String owner, final String repo, final long codeqlVariantAnalysisId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(CODEQL_VARIANT_ANALYSIS, owner, repo, codeqlVariantAnalysisId),
                GitHubCodeScanningVariantAnalysis.class);
    }

    /**
     * Get the analysis status of a repository in a CodeQL variant analysis ({@code GET
     * /repos/{owner}/{repo}/code-scanning/codeql/variant-analyses/{codeql_variant_analysis_id}/repos/{repo_owner}/{repo_name}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param codeqlVariantAnalysisId variant analysis id
     * @param repoOwner owner of the analysed repository
     * @param repoName name of the analysed repository
     * @return repository task status, or empty on HTTP 404
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodeScanningVariantAnalysisRepoTask> getVariantAnalysisRepoTask(
            final String owner,
            final String repo,
            final long codeqlVariantAnalysisId,
            final String repoOwner,
            final String repoName)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(CODEQL_VARIANT_ANALYSIS_REPO_TASK, owner, repo, codeqlVariantAnalysisId, repoOwner, repoName),
                GitHubCodeScanningVariantAnalysisRepoTask.class);
    }

    /**
     * Get the AI Scan setting for an organization ({@code GET /orgs/{org}/code-scanning/ai-scan}).
     *
     * @param org organization name
     * @return AI Scan setting stored on the organization
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodeScanningAiScanOrgSettings getAiScanEnablementForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_CODE_SCANNING_AI_SCAN, org), GitHubCodeScanningAiScanOrgSettings.class);
    }

    /**
     * Update the AI Scan setting for an organization ({@code PATCH /orgs/{org}/code-scanning/ai-scan}).
     *
     * @param org organization name
     * @param payload AI Scan setting to apply
     * @return updated AI Scan setting
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodeScanningAiScanOrgSettings updateAiScanEnablementForOrg(
            final String org, final GitHubCodeScanningAiScanOrgEnablementUpdate payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ORG_CODE_SCANNING_AI_SCAN, org), payload), GitHubCodeScanningAiScanOrgSettings.class);
    }

    /**
     * Get AI Scan enablement for a repository ({@code GET /repos/{owner}/{repo}/code-scanning/ai-scan}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return AI Scan enablement
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodeScanningAiScanEnablement getAiScanEnablement(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(REPO_CODE_SCANNING_AI_SCAN, owner, repo), GitHubCodeScanningAiScanEnablement.class);
    }

    /**
     * Update AI Scan enablement for a repository ({@code PATCH /repos/{owner}/{repo}/code-scanning/ai-scan}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload AI Scan enablement to apply
     * @return updated AI Scan enablement
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodeScanningAiScanEnablement updateAiScanEnablement(
            final String owner, final String repo, final GitHubCodeScanningAiScanEnablementUpdate payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(REPO_CODE_SCANNING_AI_SCAN, owner, repo), payload),
                GitHubCodeScanningAiScanEnablement.class);
    }

    /**
     * Get a code scanning default setup configuration ({@code GET /repos/{owner}/{repo}/code-scanning/default-setup}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return default setup configuration
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCodeScanningDefaultSetup getDefaultSetup(final String owner, final String repo) throws IOException {
        return caller.fetch(get(CODE_SCANNING_DEFAULT_SETUP, owner, repo), GitHubCodeScanningDefaultSetup.class);
    }

    /**
     * Update a code scanning default setup configuration ({@code PATCH
     * /repos/{owner}/{repo}/code-scanning/default-setup}). GitHub answers HTTP 202 with the run that was started; HTTP
     * 200 carries no body.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload configuration to apply
     * @return update response when GitHub started a run (HTTP 202), or empty on HTTP 200
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeScanningDefaultSetupUpdateResponse> updateDefaultSetup(
            final String owner, final String repo, final GitHubCodeScanningDefaultSetupUpdate payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetchUnless(
                caller.json(patch(CODE_SCANNING_DEFAULT_SETUP, owner, repo), payload),
                GitHubCodeScanningDefaultSetupUpdateResponse.class,
                HttpStatus.OK);
    }

    /**
     * Upload an analysis as SARIF data ({@code POST /repos/{owner}/{repo}/code-scanning/sarifs}). GitHub answers HTTP
     * 202 with a receipt; processing continues in the background.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload SARIF upload
     * @return receipt with the SARIF id and upload URL
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCodeScanningSarifsReceipt uploadSarif(
            final String owner, final String repo, final GitHubUploadSarifPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(CODE_SCANNING_SARIFS, owner, repo), payload), GitHubCodeScanningSarifsReceipt.class);
    }

    /**
     * Get information about a SARIF upload ({@code GET /repos/{owner}/{repo}/code-scanning/sarifs/{sarif_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param sarifId SARIF id
     * @return processing status of the upload, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code sarifId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubCodeScanningSarifsStatus> getSarif(
            final String owner, final String repo, final String sarifId) throws IOException {
        return caller.fetchOrEmpty(
                get(CODE_SCANNING_SARIF, owner, repo, sarifId), GitHubCodeScanningSarifsStatus.class);
    }
}
