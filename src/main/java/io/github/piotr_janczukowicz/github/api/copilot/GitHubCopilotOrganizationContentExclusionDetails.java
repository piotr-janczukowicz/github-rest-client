package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import java.util.Map;

/** List of all Copilot content exclusion rules for an organization, keyed by repository name. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/copilot-organization-content-exclusion-details")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotOrganizationContentExclusionDetails(Map<String, List<String>> rules) {

    public GitHubCopilotOrganizationContentExclusionDetails {
        requireNonNull(rules, "rules");
    }

    /**
     * Deserializes the flat rules map from JSON.
     *
     * @param rules file paths to exclude keyed by repository name
     * @return details
     */
    @JsonCreator
    public static GitHubCopilotOrganizationContentExclusionDetails fromJson(final Map<String, List<String>> rules) {
        return new GitHubCopilotOrganizationContentExclusionDetails(rules);
    }

    /**
     * Serializes as the flat rules map for JSON.
     *
     * @return file paths to exclude keyed by repository name
     */
    @JsonValue
    public Map<String, List<String>> toJson() {
        return rules;
    }
}
