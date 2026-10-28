package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Information about the seat breakdown and policies set for an organization with a Copilot Business or Copilot
 * Enterprise subscription.
 *
 * @param seatBreakdown The breakdown of Copilot Business seats for the organization.
 * @param publicCodeSuggestions The organization policy for allowing or blocking suggestions matching public code
 *     (duplication detection filter).
 * @param ideChat The organization policy for allowing or disallowing Copilot Chat in the IDE.
 * @param platformChat The organization policy for allowing or disallowing Copilot features on GitHub.com.
 * @param cli The organization policy for allowing or disallowing Copilot CLI.
 * @param seatManagementSetting The mode of assigning new seats.
 * @param planType The Copilot plan of the organization, or the parent enterprise, when applicable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/copilot-organization-details")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotOrganizationDetails(
        GitHubCopilotOrganizationSeatBreakdown seatBreakdown,
        GitHubCopilotOrganizationPublicCodeSuggestions publicCodeSuggestions,
        @Nullable GitHubCopilotOrganizationFeatureSetting ideChat,
        @Nullable GitHubCopilotOrganizationFeatureSetting platformChat,
        @Nullable GitHubCopilotOrganizationFeatureSetting cli,
        GitHubCopilotOrganizationSeatManagementSetting seatManagementSetting,
        @Nullable GitHubCopilotOrganizationPlanType planType) {

    public GitHubCopilotOrganizationDetails {
        requireNonNull(seatBreakdown, "seatBreakdown");
        requireNonNull(publicCodeSuggestions, "publicCodeSuggestions");
        requireNonNull(seatManagementSetting, "seatManagementSetting");
    }
}
