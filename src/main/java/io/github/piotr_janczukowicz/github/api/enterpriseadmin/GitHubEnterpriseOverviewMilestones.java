package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Enterprise Milestone Stats
 *
 * @param totalMilestones total milestones
 * @param openMilestones open milestones
 * @param closedMilestones closed milestones
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-overview/properties/milestones")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseOverviewMilestones(int totalMilestones, int openMilestones, int closedMilestones) {}
