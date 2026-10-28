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

/** Content exclusion rules to set for an enterprise, keyed by repository name. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/set-copilot-content-exclusion-for-enterprise/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubSetCopilotContentExclusionForEnterprisePayload(
        Map<String, List<GitHubCopilotEnterpriseContentExclusionRule>> rules) {

    public GitHubSetCopilotContentExclusionForEnterprisePayload {
        rules = Map.copyOf(requireNonNull(rules, "rules"));
    }

    /**
     * Deserializes the flat rules map from JSON.
     *
     * @param rules content exclusion rules keyed by repository name
     * @return payload
     */
    @JsonCreator
    public static GitHubSetCopilotContentExclusionForEnterprisePayload fromJson(
            final Map<String, List<GitHubCopilotEnterpriseContentExclusionRule>> rules) {
        return new GitHubSetCopilotContentExclusionForEnterprisePayload(rules);
    }

    /**
     * Serializes as the flat rules map for JSON.
     *
     * @return content exclusion rules keyed by repository name
     */
    @JsonValue
    public Map<String, List<GitHubCopilotEnterpriseContentExclusionRule>> toJson() {
        return rules;
    }
}
