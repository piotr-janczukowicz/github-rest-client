package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * The team's customization document. Only the keys {@code pluginAssignments} and {@code extraKnownMarketplaces} are
 * supported.
 *
 * @param pluginAssignments Plugins assigned to the team, keyed by {@code PLUGIN@MARKETPLACE}.
 * @param extraKnownMarketplaces Additional plugin marketplaces available to the team, keyed by marketplace name.
 * @param additionalProperties Customization keys outside the named schema properties
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/set-copilot-team-customizations/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetCopilotTeamCustomizationsPayload(
        @JsonProperty("pluginAssignments") @Nullable Map<String, GitHubSetCopilotTeamCustomizationsPluginAssignment> pluginAssignments,

        @JsonProperty("extraKnownMarketplaces")
        @SuppressSchemaTypeMismatch("dump values are empty objects; java is Map")
        @Nullable Map<String, Map<String, Object>> extraKnownMarketplaces,

        @JsonIgnore Map<String, Object> additionalProperties) {

    public GitHubSetCopilotTeamCustomizationsPayload {
        if (pluginAssignments != null) {
            pluginAssignments = Map.copyOf(pluginAssignments);
        }
        if (extraKnownMarketplaces != null) {
            extraKnownMarketplaces = Collections.unmodifiableMap(new LinkedHashMap<>(extraKnownMarketplaces));
        }
        requireNonNull(additionalProperties, "additionalProperties");
        if (additionalProperties.containsKey("pluginAssignments")
                || additionalProperties.containsKey("extraKnownMarketplaces")) {
            throw new IllegalArgumentException(
                    "additionalProperties must not contain pluginAssignments or extraKnownMarketplaces");
        }
        additionalProperties = Collections.unmodifiableMap(new LinkedHashMap<>(additionalProperties));
    }

    /**
     * Additional customization keys serialized at the top level.
     *
     * @return additional customization values
     */
    @JsonAnyGetter
    public Map<String, Object> additionalPropertiesForJson() {
        return additionalProperties;
    }
}
