package io.github.piotr_janczukowicz.github.api.campaigns;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The campaign metadata and alert stats.
 *
 * @param number The number of the newly created campaign
 * @param createdAt The date and time the campaign was created, in ISO 8601 format':' YYYY-MM-DDTHH:MM:SSZ.
 * @param updatedAt The date and time the campaign was last updated, in ISO 8601 format':' YYYY-MM-DDTHH:MM:SSZ.
 * @param name The campaign name
 * @param description The campaign description
 * @param managers The campaign managers
 * @param teamManagers The campaign team managers
 * @param publishedAt The date and time the campaign was published, in ISO 8601 format':' YYYY-MM-DDTHH:MM:SSZ.
 * @param endsAt The date and time the campaign has ended, in ISO 8601 format':' YYYY-MM-DDTHH:MM:SSZ.
 * @param closedAt The date and time the campaign was closed, in ISO 8601 format':' YYYY-MM-DDTHH:MM:SSZ. Will be null
 *     if the campaign is still open.
 * @param state Indicates whether a campaign is open or closed
 * @param contactLink The contact link of the campaign.
 * @param alertStats Alert stats
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/campaign-summary")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCampaignSummary(
        int number,
        Instant createdAt,
        Instant updatedAt,
        @Nullable String name,
        String description,
        List<GitHubSimpleUser> managers,
        @Nullable List<GitHubTeam> teamManagers,
        @Nullable Instant publishedAt,
        Instant endsAt,
        @Nullable Instant closedAt,
        GitHubCampaignState state,
        @Nullable String contactLink,
        @Nullable GitHubCampaignSummaryAlertStats alertStats) {

    public GitHubCampaignSummary {
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(description, "description");
        requireNonNull(managers, "managers");
        requireNonNull(endsAt, "endsAt");
        requireNonNull(state, "state");
    }
}
