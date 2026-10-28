package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Code scanning alerts of one repository to include in a campaign.
 *
 * @param repositoryId The repository id
 * @param alertNumbers The alert numbers
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/campaigns/create-campaign/requestBody/properties/code_scanning_alerts/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateCampaignCodeScanningAlert(long repositoryId, List<Integer> alertNumbers) {

    public GitHubCreateCampaignCodeScanningAlert {
        requireNonNull(alertNumbers, "alertNumbers");
        alertNumbers = List.copyOf(requireNonEmpty(alertNumbers, "alertNumbers"));
    }
}
