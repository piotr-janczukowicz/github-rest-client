package io.github.piotr_janczukowicz.github.api.campaigns;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Alert counts for a campaign.
 *
 * @param openCount The number of open alerts
 * @param closedCount The number of closed alerts
 * @param inProgressCount The number of in-progress alerts
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/campaign-summary/properties/alert_stats")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCampaignSummaryAlertStats(int openCount, int closedCount, int inProgressCount) {}
