package io.github.piotr_janczukowicz.github.api.copilot;

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
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub Copilot usage metrics REST resource for API version {@code 2026-03-10}. */
public final class GitHubCopilotCopilotUsageMetricsApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_USAGE_RECORDS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/usage-records");
    private static final GitHubListSpec<GitHubGetUsageRecordsItem> USAGE_RECORDS_SPEC = GitHubListSpec.array(
                    GitHubPageRequestKindValidators.CURSOR, JsonTypes.listOf(GitHubGetUsageRecordsItem.class))
            .withMaxPerPage(25);
    private static final PathTemplate ENTERPRISE_ONE_DAY_USAGE_METRICS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/metrics/reports/enterprise-1-day");
    private static final PathTemplate ENTERPRISE_28_DAY_USAGE_METRICS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/metrics/reports/enterprise-28-day/latest");
    private static final PathTemplate ENTERPRISE_REPOS_ONE_DAY_REPORT =
            PathTemplate.of("/enterprises/{enterprise}/copilot/metrics/reports/repos-1-day");
    private static final PathTemplate ENTERPRISE_USER_TEAMS_ONE_DAY_REPORT =
            PathTemplate.of("/enterprises/{enterprise}/copilot/metrics/reports/user-teams-1-day");
    private static final PathTemplate ENTERPRISE_USERS_ONE_DAY_USAGE_METRICS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/metrics/reports/users-1-day");
    private static final PathTemplate ENTERPRISE_USERS_28_DAY_USAGE_METRICS =
            PathTemplate.of("/enterprises/{enterprise}/copilot/metrics/reports/users-28-day/latest");
    private static final PathTemplate ORG_ONE_DAY_USAGE_METRICS =
            PathTemplate.of("/orgs/{org}/copilot/metrics/reports/organization-1-day");
    private static final PathTemplate ORG_28_DAY_USAGE_METRICS =
            PathTemplate.of("/orgs/{org}/copilot/metrics/reports/organization-28-day/latest");
    private static final PathTemplate ORG_REPOS_ONE_DAY_REPORT =
            PathTemplate.of("/orgs/{org}/copilot/metrics/reports/repos-1-day");
    private static final PathTemplate ORG_USER_TEAMS_ONE_DAY_REPORT =
            PathTemplate.of("/orgs/{org}/copilot/metrics/reports/user-teams-1-day");
    private static final PathTemplate ORG_USERS_ONE_DAY_USAGE_METRICS =
            PathTemplate.of("/orgs/{org}/copilot/metrics/reports/users-1-day");
    private static final PathTemplate ORG_USERS_28_DAY_USAGE_METRICS =
            PathTemplate.of("/orgs/{org}/copilot/metrics/reports/users-28-day/latest");

    /**
     * Copilot usage metrics resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCopilotCopilotUsageMetricsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the one-day enterprise usage metrics report ({@code GET
     * /enterprises/{enterprise}/copilot/metrics/reports/enterprise-1-day}).
     *
     * @param enterprise enterprise slug
     * @param filter report day
     * @return one-day report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotUsageMetrics1DayReport getEnterpriseOneDayUsageMetrics(
            final String enterprise, final GitHubCopilotEnterpriseOneDayUsageMetricsFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ENTERPRISE_ONE_DAY_USAGE_METRICS, enterprise).withQuery(filter.toQueryParameters()),
                GitHubCopilotUsageMetrics1DayReport.class);
    }

    /**
     * Get the latest 28-day enterprise usage metrics report ({@code GET
     * /enterprises/{enterprise}/copilot/metrics/reports/enterprise-28-day/latest}).
     *
     * @param enterprise enterprise slug
     * @return 28-day report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotUsageMetrics28DayReport getEnterpriseUsageMetrics(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_28_DAY_USAGE_METRICS, enterprise), GitHubCopilotUsageMetrics28DayReport.class);
    }

    /**
     * Get the one-day enterprise repositories report ({@code GET
     * /enterprises/{enterprise}/copilot/metrics/reports/repos-1-day}).
     *
     * @param enterprise enterprise slug
     * @param filter report day
     * @return one-day report, or empty on HTTP 204
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotUsageMetrics1DayReport> getEnterpriseReposOneDayReport(
            final String enterprise, final GitHubCopilotEnterpriseReposOneDayReportFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchUnless(
                get(ENTERPRISE_REPOS_ONE_DAY_REPORT, enterprise).withQuery(filter.toQueryParameters()),
                GitHubCopilotUsageMetrics1DayReport.class,
                HttpStatus.NO_CONTENT);
    }

    /**
     * Get the one-day enterprise user teams report ({@code GET
     * /enterprises/{enterprise}/copilot/metrics/reports/user-teams-1-day}).
     *
     * @param enterprise enterprise slug
     * @param filter report day
     * @return one-day report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotUsageMetrics1DayReport getEnterpriseUserTeamsOneDayReport(
            final String enterprise, final GitHubCopilotEnterpriseUserTeamsOneDayReportFilter filter)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ENTERPRISE_USER_TEAMS_ONE_DAY_REPORT, enterprise).withQuery(filter.toQueryParameters()),
                GitHubCopilotUsageMetrics1DayReport.class);
    }

    /**
     * Get the one-day enterprise users usage metrics report ({@code GET
     * /enterprises/{enterprise}/copilot/metrics/reports/users-1-day}).
     *
     * @param enterprise enterprise slug
     * @param filter report day
     * @return one-day report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotUsageMetrics1DayReport getUsersOneDayUsageMetrics(
            final String enterprise, final GitHubCopilotUsersOneDayUsageMetricsFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(ENTERPRISE_USERS_ONE_DAY_USAGE_METRICS, enterprise).withQuery(filter.toQueryParameters()),
                GitHubCopilotUsageMetrics1DayReport.class);
    }

    /**
     * Get the latest 28-day enterprise users usage metrics report ({@code GET
     * /enterprises/{enterprise}/copilot/metrics/reports/users-28-day/latest}).
     *
     * @param enterprise enterprise slug
     * @return 28-day report
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotUsageMetrics28DayReport getUsersUsageMetrics(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(
                get(ENTERPRISE_USERS_28_DAY_USAGE_METRICS, enterprise), GitHubCopilotUsageMetrics28DayReport.class);
    }

    /**
     * Get the one-day organization usage metrics report ({@code GET
     * /orgs/{org}/copilot/metrics/reports/organization-1-day}).
     *
     * @param org organization name
     * @param filter report day
     * @return one-day report, or empty on HTTP 204
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotUsageMetrics1DayReport> getOrganizationOneDayUsageMetrics(
            final String org, final GitHubCopilotOrganizationOneDayUsageMetricsFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchUnless(
                get(ORG_ONE_DAY_USAGE_METRICS, org).withQuery(filter.toQueryParameters()),
                GitHubCopilotUsageMetrics1DayReport.class,
                HttpStatus.NO_CONTENT);
    }

    /**
     * Get the latest 28-day organization usage metrics report ({@code GET
     * /orgs/{org}/copilot/metrics/reports/organization-28-day/latest}).
     *
     * @param org organization name
     * @return 28-day report
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotUsageMetrics28DayReport getOrganizationUsageMetrics(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_28_DAY_USAGE_METRICS, org), GitHubCopilotUsageMetrics28DayReport.class);
    }

    /**
     * Get the one-day organization repositories report ({@code GET /orgs/{org}/copilot/metrics/reports/repos-1-day}).
     *
     * @param org organization name
     * @param filter report day
     * @return one-day report, or empty on HTTP 204
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotUsageMetrics1DayReport> getOrganizationReposOneDayReport(
            final String org, final GitHubCopilotOrganizationReposOneDayReportFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchUnless(
                get(ORG_REPOS_ONE_DAY_REPORT, org).withQuery(filter.toQueryParameters()),
                GitHubCopilotUsageMetrics1DayReport.class,
                HttpStatus.NO_CONTENT);
    }

    /**
     * Get the one-day organization user teams report ({@code GET
     * /orgs/{org}/copilot/metrics/reports/user-teams-1-day}).
     *
     * @param org organization name
     * @param filter report day
     * @return one-day report, or empty on HTTP 204
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotUsageMetrics1DayReport> getOrganizationUserTeamsOneDayReport(
            final String org, final GitHubCopilotOrganizationUserTeamsOneDayReportFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchUnless(
                get(ORG_USER_TEAMS_ONE_DAY_REPORT, org).withQuery(filter.toQueryParameters()),
                GitHubCopilotUsageMetrics1DayReport.class,
                HttpStatus.NO_CONTENT);
    }

    /**
     * Get the one-day organization users usage metrics report ({@code GET
     * /orgs/{org}/copilot/metrics/reports/users-1-day}).
     *
     * @param org organization name
     * @param filter report day
     * @return one-day report, or empty on HTTP 204
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCopilotUsageMetrics1DayReport> getOrganizationUsersOneDayUsageMetrics(
            final String org, final GitHubCopilotOrganizationUsersOneDayUsageMetricsFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchUnless(
                get(ORG_USERS_ONE_DAY_USAGE_METRICS, org).withQuery(filter.toQueryParameters()),
                GitHubCopilotUsageMetrics1DayReport.class,
                HttpStatus.NO_CONTENT);
    }

    /**
     * Get the latest 28-day organization users usage metrics report ({@code GET
     * /orgs/{org}/copilot/metrics/reports/users-28-day/latest}).
     *
     * @param org organization name
     * @return 28-day report
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCopilotUsageMetrics28DayReport getOrganizationUsersUsageMetrics(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_USERS_28_DAY_USAGE_METRICS, org), GitHubCopilotUsageMetrics28DayReport.class);
    }

    /**
     * Get Copilot usage records of an enterprise ({@code GET /enterprises/{enterprise}/copilot/usage-records}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()} and an empty filter. See {@link #getUsageRecords(String,
     * GitHubGetUsageRecordsFilter, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of usage records
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubGetUsageRecordsItem> getUsageRecords(final String enterprise) throws IOException {
        return getUsageRecords(enterprise, GitHubGetUsageRecordsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * Get Copilot usage records of an enterprise ({@code GET /enterprises/{enterprise}/copilot/usage-records}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} only, as the {@code before}
     * and {@code after} cursors come from the {@code Link} header.
     *
     * @param enterprise the slug version of the enterprise name
     * @param filter query filters
     * @param request cursor pagination token
     * @return requested page of usage records
     * @throws IllegalArgumentException if {@code enterprise} is blank, or {@code request} is not a cursor
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubGetUsageRecordsItem> getUsageRecords(
            final String enterprise, final GitHubGetUsageRecordsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ENTERPRISE_USAGE_RECORDS, filter.toQueryParameters(), request, USAGE_RECORDS_SPEC, enterprise);
    }
}
