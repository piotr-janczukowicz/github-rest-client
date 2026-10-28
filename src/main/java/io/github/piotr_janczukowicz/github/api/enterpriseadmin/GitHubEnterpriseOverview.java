package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubEnterpriseOverview
 *
 * @param repos repos
 * @param hooks hooks
 * @param pages pages
 * @param orgs orgs
 * @param users users
 * @param pulls pulls
 * @param issues issues
 * @param milestones milestones
 * @param gists gists
 * @param comments comments
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-overview")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseOverview(
        @Nullable GitHubEnterpriseOverviewRepos repos,
        @Nullable GitHubEnterpriseOverviewHooks hooks,
        @Nullable GitHubEnterpriseOverviewPages pages,
        @Nullable GitHubEnterpriseOverviewOrgs orgs,
        @Nullable GitHubEnterpriseOverviewUsers users,
        @Nullable GitHubEnterpriseOverviewPulls pulls,
        @Nullable GitHubEnterpriseOverviewIssues issues,
        @Nullable GitHubEnterpriseOverviewMilestones milestones,
        @Nullable GitHubEnterpriseOverviewGists gists,
        @Nullable GitHubEnterpriseOverviewComments comments) {}
