package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
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
 * Create campaign.
 *
 * @param name The name of the campaign
 * @param description A description for the campaign
 * @param managers The logins of the users to set as the campaign managers. At this time, only a single manager can be
 *     supplied.
 * @param teamManagers The slugs of the teams to set as the campaign managers.
 * @param endsAt The end date and time of the campaign. The date must be in the future.
 * @param contactLink The contact link of the campaign. Must be a URI.
 * @param codeScanningAlerts The code scanning alerts to include in this campaign
 * @param generateIssues If true, will automatically generate issues for the campaign. The default is false.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/campaigns/create-campaign/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateCampaignPayload(
        String name,
        String description,
        @Nullable List<String> managers,
        @Nullable List<String> teamManagers,
        Instant endsAt,
        @Nullable URI contactLink,
        @Nullable List<GitHubCreateCampaignCodeScanningAlert> codeScanningAlerts,
        @Nullable Boolean generateIssues) {

    private static final String DESCRIPTION_PARAMETER = "description";

    public GitHubCreateCampaignPayload {
        requireNonNull(name, "name");
        name = requireMaxLength(requireMinLength(name, 1, "name"), 50, "name");
        requireNonNull(description, DESCRIPTION_PARAMETER);
        description =
                requireMaxLength(requireMinLength(description, 1, DESCRIPTION_PARAMETER), 255, DESCRIPTION_PARAMETER);
        requireNonNull(endsAt, "endsAt");
        if (managers != null) {
            managers = List.copyOf(requireSizeAtMost(managers, 10, "managers"));
        }
        if (teamManagers != null) {
            teamManagers = List.copyOf(requireSizeAtMost(teamManagers, 10, "teamManagers"));
        }
        if (codeScanningAlerts != null) {
            codeScanningAlerts = List.copyOf(requireNonEmpty(codeScanningAlerts, "codeScanningAlerts"));
        }
    }
}
