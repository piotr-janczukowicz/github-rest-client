package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub Enterprise Server statistics REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminAdminStatsApi extends GitHubResourceApi {

    private static final PathTemplate STATS_ALL = PathTemplate.of("/enterprise/stats/all");
    private static final PathTemplate STATS_COMMENTS = PathTemplate.of("/enterprise/stats/comments");
    private static final PathTemplate STATS_GISTS = PathTemplate.of("/enterprise/stats/gists");
    private static final PathTemplate STATS_HOOKS = PathTemplate.of("/enterprise/stats/hooks");
    private static final PathTemplate STATS_ISSUES = PathTemplate.of("/enterprise/stats/issues");
    private static final PathTemplate STATS_MILESTONES = PathTemplate.of("/enterprise/stats/milestones");
    private static final PathTemplate STATS_ORGS = PathTemplate.of("/enterprise/stats/orgs");
    private static final PathTemplate STATS_PAGES = PathTemplate.of("/enterprise/stats/pages");
    private static final PathTemplate STATS_PULLS = PathTemplate.of("/enterprise/stats/pulls");
    private static final PathTemplate STATS_REPOS = PathTemplate.of("/enterprise/stats/repos");
    private static final PathTemplate STATS_SECURITY_PRODUCTS = PathTemplate.of("/enterprise/stats/security-products");
    private static final PathTemplate STATS_USERS = PathTemplate.of("/enterprise/stats/users");

    private static final PathTemplate SERVER_STATISTICS =
            PathTemplate.of("/enterprise-installation/{enterprise_or_org}/server-statistics");

    private static final JsonType<List<GitHubServerStatistics>> SERVER_STATISTICS_TYPE =
            JsonTypes.listOf(GitHubServerStatistics.class);

    /**
     * Server statistics resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminAdminStatsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the server statistics of an enterprise or organization ({@code GET
     * /enterprise-installation/{enterprise_or_org}/server-statistics}) with no filter.
     *
     * <p>See {@link #getServerStatistics(String, GitHubGetServerStatisticsFilter)}.
     *
     * @param enterpriseOrOrg the slug version of the enterprise name or the login of an organization
     * @return server statistics payloads
     * @throws IllegalArgumentException if {@code enterpriseOrOrg} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public List<GitHubServerStatistics> getServerStatistics(final String enterpriseOrOrg) throws IOException {
        return getServerStatistics(enterpriseOrOrg, GitHubGetServerStatisticsFilter.empty());
    }

    /**
     * Get the server statistics of an enterprise or organization ({@code GET
     * /enterprise-installation/{enterprise_or_org}/server-statistics}).
     *
     * @param enterpriseOrOrg the slug version of the enterprise name or the login of an organization
     * @param filter query filters with the date cursors
     * @return server statistics payloads
     * @throws IllegalArgumentException if {@code enterpriseOrOrg} is empty
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public List<GitHubServerStatistics> getServerStatistics(
            final String enterpriseOrOrg, final GitHubGetServerStatisticsFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(
                get(SERVER_STATISTICS, enterpriseOrOrg).withQuery(filter.toQueryParameters()), SERVER_STATISTICS_TYPE);
    }

    /**
     * Get all statistics ({@code GET /enterprise/stats/all}).
     *
     * @return all statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseOverview getAllStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_ALL), GitHubEnterpriseOverview.class);
    }

    /**
     * Get comment statistics ({@code GET /enterprise/stats/comments}).
     *
     * @return comment statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseCommentOverview getCommentStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_COMMENTS), GitHubEnterpriseCommentOverview.class);
    }

    /**
     * Get gist statistics ({@code GET /enterprise/stats/gists}).
     *
     * @return gist statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseGistOverview getGistStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_GISTS), GitHubEnterpriseGistOverview.class);
    }

    /**
     * Get hook statistics ({@code GET /enterprise/stats/hooks}).
     *
     * @return hook statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseHookOverview getHooksStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_HOOKS), GitHubEnterpriseHookOverview.class);
    }

    /**
     * Get issue statistics ({@code GET /enterprise/stats/issues}).
     *
     * @return issue statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseIssueOverview getIssueStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_ISSUES), GitHubEnterpriseIssueOverview.class);
    }

    /**
     * Get milestone statistics ({@code GET /enterprise/stats/milestones}).
     *
     * @return milestone statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseMilestoneOverview getMilestoneStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_MILESTONES), GitHubEnterpriseMilestoneOverview.class);
    }

    /**
     * Get organization statistics ({@code GET /enterprise/stats/orgs}).
     *
     * @return organization statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseOrganizationOverview getOrgStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_ORGS), GitHubEnterpriseOrganizationOverview.class);
    }

    /**
     * Get pages statistics ({@code GET /enterprise/stats/pages}).
     *
     * @return pages statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterprisePageOverview getPagesStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_PAGES), GitHubEnterprisePageOverview.class);
    }

    /**
     * Get pull request statistics ({@code GET /enterprise/stats/pulls}).
     *
     * @return pull request statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterprisePullRequestOverview getPullRequestStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_PULLS), GitHubEnterprisePullRequestOverview.class);
    }

    /**
     * Get repository statistics ({@code GET /enterprise/stats/repos}).
     *
     * @return repository statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseRepositoryOverview getRepoStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_REPOS), GitHubEnterpriseRepositoryOverview.class);
    }

    /**
     * Get security products statistics ({@code GET /enterprise/stats/security-products}).
     *
     * @return security products statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseSecurityProductsOverview getSecurityProducts() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_SECURITY_PRODUCTS), GitHubEnterpriseSecurityProductsOverview.class);
    }

    /**
     * Get user statistics ({@code GET /enterprise/stats/users}).
     *
     * @return user statistics
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubEnterpriseUserOverview getUserStats() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(STATS_USERS), GitHubEnterpriseUserOverview.class);
    }
}
