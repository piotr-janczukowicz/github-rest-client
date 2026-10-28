package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Enterprise Organization Stats
 *
 * @param totalOrgs total orgs
 * @param disabledOrgs disabled orgs
 * @param totalTeams total teams
 * @param totalTeamMembers total team members
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-organization-overview")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseOrganizationOverview(
        int totalOrgs, int disabledOrgs, int totalTeams, int totalTeamMembers) {}
