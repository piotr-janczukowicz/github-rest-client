package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update campaign.
 *
 * @param name The name of the campaign
 * @param description A description for the campaign
 * @param managers The logins of the users to set as the campaign managers. At this time, only a single manager can be
 *     supplied.
 * @param teamManagers The slugs of the teams to set as the campaign managers.
 * @param endsAt The end date and time of the campaign, in ISO 8601 format':' YYYY-MM-DDTHH:MM:SSZ.
 * @param contactLink The contact link of the campaign. Must be a URI.
 * @param state Indicates whether a campaign is open or closed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/campaigns/update-campaign/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateCampaignPayload(
        @Nullable String name,
        @Nullable String description,
        @Nullable List<String> managers,
        @Nullable List<String> teamManagers,
        @Nullable Instant endsAt,
        @Nullable URI contactLink,
        @Nullable GitHubCampaignState state) {

    public GitHubUpdateCampaignPayload {
        name = requireMaxLengthIfPresent(requireMinLengthIfPresent(name, 1, "name"), 50, "name");
        description =
                requireMaxLengthIfPresent(requireMinLengthIfPresent(description, 1, "description"), 255, "description");
        if (managers != null) {
            managers = List.copyOf(requireSizeAtMost(managers, 10, "managers"));
        }
        if (teamManagers != null) {
            teamManagers = List.copyOf(requireSizeAtMost(teamManagers, 10, "teamManagers"));
        }
    }
}
