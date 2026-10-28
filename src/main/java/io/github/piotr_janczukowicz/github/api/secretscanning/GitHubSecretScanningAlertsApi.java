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
import java.util.Map;
import java.util.Optional;

/**
 * GitHub Secret Scanning alerts REST resource for API version {@code 2026-03-10}.
 *
 * <p>Read and update secret scanning alerts, push protection bypasses, and scan history for organizations and
 * repositories.
 */
public final class GitHubSecretScanningAlertsApi extends GitHubResourceApi {

    private static final PathTemplate SECRET_SCANNING_SCAN_HISTORY =
            PathTemplate.of("/repos/{owner}/{repo}/secret-scanning/scan-history");
    private static final PathTemplate SECRET_SCANNING_ALERT_LOCATIONS =
            PathTemplate.of("/repos/{owner}/{repo}/secret-scanning/alerts/{alert_number}/locations");
    private static final PathTemplate SECRET_SCANNING_PUSH_PROTECTION_BYPASSES =
            PathTemplate.of("/repos/{owner}/{repo}/secret-scanning/push-protection-bypasses");
    private static final PathTemplate SECRET_SCANNING_ALERT =
            PathTemplate.of("/repos/{owner}/{repo}/secret-scanning/alerts/{alert_number}");
    private static final PathTemplate REPO_SECRET_SCANNING_ALERTS =
            PathTemplate.of("/repos/{owner}/{repo}/secret-scanning/alerts");
    private static final PathTemplate ORG_SECRET_SCANNING_ALERTS =
            PathTemplate.of("/orgs/{org}/secret-scanning/alerts");
    private static final PathTemplate ENTERPRISE_SECRET_SCANNING_ALERTS =
            PathTemplate.of("/enterprises/{enterprise}/secret-scanning/alerts");

    private static final JsonType<List<GitHubOrganizationSecretScanningAlert>> ORG_ALERTS_TYPE =
            JsonTypes.listOf(GitHubOrganizationSecretScanningAlert.class);
    private static final JsonType<List<GitHubSecretScanningAlert>> REPO_ALERTS_TYPE =
            JsonTypes.listOf(GitHubSecretScanningAlert.class);
    private static final JsonType<List<GitHubSecretScanningLocation>> LOCATIONS_TYPE =
            JsonTypes.listOf(GitHubSecretScanningLocation.class);

    /**
     * Secret scanning alerts resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecretScanningAlertsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization secret scanning alerts ({@code GET /orgs/{org}/secret-scanning/alerts}).
     *
     * @param org organization login
     * @return first HTTP page of organization alerts
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSecretScanningAlert> listOrgAlerts(final String org) throws IOException {
        return listOrgAlerts(org, GitHubListSecretScanningAlertsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List organization secret scanning alerts ({@code GET /orgs/{org}/secret-scanning/alerts}).
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of organization alerts
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSecretScanningAlert> listOrgAlerts(
            final String org, final GitHubListSecretScanningAlertsFilter filter) throws IOException {
        return listOrgAlerts(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List organization secret scanning alerts ({@code GET /orgs/{org}/secret-scanning/alerts}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest},
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest}, and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest}.
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of organization alerts
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrganizationSecretScanningAlert> listOrgAlerts(
            final String org, final GitHubListSecretScanningAlertsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return list(
                ORG_SECRET_SCANNING_ALERTS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.PAGE_OR_CURSOR, ORG_ALERTS_TYPE),
                org);
    }

    /**
     * List repository secret scanning alerts ({@code GET /repos/{owner}/{repo}/secret-scanning/alerts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of repository alerts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSecretScanningAlert> listRepoAlerts(final String owner, final String repo)
            throws IOException {
        return listRepoAlerts(owner, repo, GitHubListSecretScanningAlertsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repository secret scanning alerts ({@code GET /repos/{owner}/{repo}/secret-scanning/alerts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return first HTTP page of repository alerts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSecretScanningAlert> listRepoAlerts(
            final String owner, final String repo, final GitHubListSecretScanningAlertsFilter filter)
            throws IOException {
        return listRepoAlerts(owner, repo, filter, GitHubPageRequest.byPage());
    }

    /**
     * List repository secret scanning alerts ({@code GET /repos/{owner}/{repo}/secret-scanning/alerts}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest},
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest}, and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of repository alerts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSecretScanningAlert> listRepoAlerts(
            final String owner,
            final String repo,
            final GitHubListSecretScanningAlertsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return list(
                REPO_SECRET_SCANNING_ALERTS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.PAGE_OR_CURSOR, REPO_ALERTS_TYPE),
                owner,
                repo);
    }

    /**
     * Get a secret scanning alert ({@code GET /repos/{owner}/{repo}/secret-scanning/alerts/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @return the alert with metadata, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubSecretScanningAlertWithMetadata> getAlert(
            final String owner, final String repo, final int alertNumber) throws IOException {
        return getAlert(owner, repo, alertNumber, GitHubGetSecretScanningAlertFilter.empty());
    }

    /**
     * Get a secret scanning alert ({@code GET /repos/{owner}/{repo}/secret-scanning/alerts/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @param filter query filters
     * @return the alert with metadata, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubSecretScanningAlertWithMetadata> getAlert(
            final String owner,
            final String repo,
            final int alertNumber,
            final GitHubGetSecretScanningAlertFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(SECRET_SCANNING_ALERT, owner, repo, alertNumber).withQuery(filter.toQueryParameters()),
                GitHubSecretScanningAlertWithMetadata.class);
    }

    /**
     * Update a secret scanning alert ({@code PATCH /repos/{owner}/{repo}/secret-scanning/alerts/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @param payload fields to update
     * @return the updated alert with metadata
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSecretScanningAlertWithMetadata updateAlert(
            final String owner,
            final String repo,
            final int alertNumber,
            final GitHubUpdateSecretScanningAlertPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(SECRET_SCANNING_ALERT, owner, repo, alertNumber), payload),
                GitHubSecretScanningAlertWithMetadata.class);
    }

    /**
     * Create a push protection bypass ({@code POST /repos/{owner}/{repo}/secret-scanning/push-protection-bypasses}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload bypass details
     * @return created bypass
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSecretScanningPushProtectionBypass createPushProtectionBypass(
            final String owner, final String repo, final GitHubCreatePushProtectionBypassPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(SECRET_SCANNING_PUSH_PROTECTION_BYPASSES, owner, repo), payload),
                GitHubSecretScanningPushProtectionBypass.class);
    }

    /**
     * List locations for a secret scanning alert ({@code GET
     * /repos/{owner}/{repo}/secret-scanning/alerts/{alert_number}/locations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @return first HTTP page of alert locations
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSecretScanningLocation> listAlertLocations(
            final String owner, final String repo, final int alertNumber) throws IOException {
        return listAlertLocations(owner, repo, alertNumber, GitHubPageRequest.byPage());
    }

    /**
     * List locations for a secret scanning alert ({@code GET
     * /repos/{owner}/{repo}/secret-scanning/alerts/{alert_number}/locations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} only.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @param request pagination token
     * @return first HTTP page of alert locations
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a since or
     *     cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubSecretScanningLocation> listAlertLocations(
            final String owner, final String repo, final int alertNumber, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(SECRET_SCANNING_ALERT_LOCATIONS, Map.of(), request, LOCATIONS_TYPE, owner, repo, alertNumber);
    }

    /**
     * Get secret scanning scan history for a repository ({@code GET
     * /repos/{owner}/{repo}/secret-scanning/scan-history}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return scan history
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubSecretScanningScanHistory getScanHistory(final String owner, final String repo) throws IOException {
        return caller.fetch(get(SECRET_SCANNING_SCAN_HISTORY, owner, repo), GitHubSecretScanningScanHistory.class);
    }

    /**
     * List enterprise secret scanning alerts ({@code GET /enterprises/{enterprise}/secret-scanning/alerts}) with no
     * filter.
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listAlertsForEnterprise(String,
     * GitHubListAlertsForEnterpriseFilter, GitHubPageRequest)}.
     *
     * @param enterprise enterprise slug
     * @return first HTTP page of enterprise alerts
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubOrganizationSecretScanningAlert> listAlertsForEnterprise(final String enterprise)
            throws IOException {
        return listAlertsForEnterprise(
                enterprise, GitHubListAlertsForEnterpriseFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List enterprise secret scanning alerts ({@code GET /enterprises/{enterprise}/secret-scanning/alerts}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}.
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @return first HTTP page of enterprise alerts
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubOrganizationSecretScanningAlert> listAlertsForEnterprise(
            final String enterprise, final GitHubListAlertsForEnterpriseFilter filter) throws IOException {
        return listAlertsForEnterprise(enterprise, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List enterprise secret scanning alerts ({@code GET /enterprises/{enterprise}/secret-scanning/alerts}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} only.
     *
     * @param enterprise enterprise slug
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of enterprise alerts
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubOrganizationSecretScanningAlert> listAlertsForEnterprise(
            final String enterprise, final GitHubListAlertsForEnterpriseFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ENTERPRISE_SECRET_SCANNING_ALERTS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ORG_ALERTS_TYPE),
                enterprise);
    }
}
