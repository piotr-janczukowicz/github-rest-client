package io.github.piotr_janczukowicz.github.api.dependabot;

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
import java.util.Optional;

/** GitHub Dependabot alerts REST resource for API version {@code 2026-03-10}. */
public final class GitHubDependabotAlertsApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_DEPENDABOT_ALERTS =
            PathTemplate.of("/enterprises/{enterprise}/dependabot/alerts");
    private static final PathTemplate ORG_DEPENDABOT_ALERTS = PathTemplate.of("/orgs/{org}/dependabot/alerts");
    private static final PathTemplate REPO_DEPENDABOT_ALERTS =
            PathTemplate.of("/repos/{owner}/{repo}/dependabot/alerts");
    private static final PathTemplate DEPENDABOT_ALERT =
            PathTemplate.of("/repos/{owner}/{repo}/dependabot/alerts/{alert_number}");

    private static final JsonType<List<GitHubDependabotAlertWithRepository>> ALERTS_WITH_REPOSITORY_TYPE =
            JsonTypes.listOf(GitHubDependabotAlertWithRepository.class);
    private static final JsonType<List<GitHubDependabotAlert>> ALERTS_TYPE =
            JsonTypes.listOf(GitHubDependabotAlert.class);

    /**
     * Dependabot alerts resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependabotAlertsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List Dependabot alerts for an enterprise ({@code GET /enterprises/{enterprise}/dependabot/alerts}).
     *
     * @param enterprise enterprise slug or ID
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlertWithRepository> listAlertsForEnterprise(final String enterprise)
            throws IOException {
        return listAlertsForEnterprise(
                enterprise, GitHubListAlertsForEnterpriseFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List Dependabot alerts for an enterprise ({@code GET /enterprises/{enterprise}/dependabot/alerts}).
     *
     * @param enterprise enterprise slug or ID
     * @param filter query filters
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlertWithRepository> listAlertsForEnterprise(
            final String enterprise, final GitHubListAlertsForEnterpriseFilter filter) throws IOException {
        return listAlertsForEnterprise(enterprise, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List Dependabot alerts for an enterprise ({@code GET /enterprises/{enterprise}/dependabot/alerts}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} only.
     *
     * @param enterprise enterprise slug or ID
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlertWithRepository> listAlertsForEnterprise(
            final String enterprise, final GitHubListAlertsForEnterpriseFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return list(
                ENTERPRISE_DEPENDABOT_ALERTS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ALERTS_WITH_REPOSITORY_TYPE),
                enterprise);
    }

    /**
     * List Dependabot alerts for an organization ({@code GET /orgs/{org}/dependabot/alerts}).
     *
     * @param org organization name
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlertWithRepository> listAlertsForOrg(final String org) throws IOException {
        return listAlertsForOrg(org, GitHubListAlertsForOrgFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List Dependabot alerts for an organization ({@code GET /orgs/{org}/dependabot/alerts}).
     *
     * @param org organization name
     * @param filter query filters
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlertWithRepository> listAlertsForOrg(
            final String org, final GitHubListAlertsForOrgFilter filter) throws IOException {
        return listAlertsForOrg(org, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List Dependabot alerts for an organization ({@code GET /orgs/{org}/dependabot/alerts}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} only.
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlertWithRepository> listAlertsForOrg(
            final String org, final GitHubListAlertsForOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return list(
                ORG_DEPENDABOT_ALERTS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ALERTS_WITH_REPOSITORY_TYPE),
                org);
    }

    /**
     * List Dependabot alerts for a repository ({@code GET /repos/{owner}/{repo}/dependabot/alerts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlert> listAlertsForRepo(final String owner, final String repo)
            throws IOException {
        return listAlertsForRepo(owner, repo, GitHubListAlertsForRepoFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List Dependabot alerts for a repository ({@code GET /repos/{owner}/{repo}/dependabot/alerts}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlert> listAlertsForRepo(
            final String owner, final String repo, final GitHubListAlertsForRepoFilter filter) throws IOException {
        return listAlertsForRepo(owner, repo, filter, GitHubPageRequest.byCursor());
    }

    /**
     * List Dependabot alerts for a repository ({@code GET /repos/{owner}/{repo}/dependabot/alerts}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} only.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of alerts
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is a page or since
     *     variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubDependabotAlert> listAlertsForRepo(
            final String owner,
            final String repo,
            final GitHubListAlertsForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return list(
                REPO_DEPENDABOT_ALERTS,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ALERTS_TYPE),
                owner,
                repo);
    }

    /**
     * Get a Dependabot alert ({@code GET /repos/{owner}/{repo}/dependabot/alerts/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @return the alert, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubDependabotAlert> getAlert(final String owner, final String repo, final int alertNumber)
            throws IOException {
        return caller.fetchOrEmpty(get(DEPENDABOT_ALERT, owner, repo, alertNumber), GitHubDependabotAlert.class);
    }

    /**
     * Update a Dependabot alert ({@code PATCH /repos/{owner}/{repo}/dependabot/alerts/{alert_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param alertNumber alert number
     * @param payload fields to update
     * @return the updated alert
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubDependabotAlert updateAlert(
            final String owner, final String repo, final int alertNumber, final GitHubUpdateAlertPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(DEPENDABOT_ALERT, owner, repo, alertNumber), payload), GitHubDependabotAlert.class);
    }
}
