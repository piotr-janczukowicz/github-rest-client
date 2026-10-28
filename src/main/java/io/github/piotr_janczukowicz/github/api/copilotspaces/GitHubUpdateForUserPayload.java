package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Set a Copilot Space for a user.
 *
 * @param name The name of the Copilot Space.
 * @param description A description of the Copilot Space.
 * @param generalInstructions General instructions for the Copilot Space.
 * @param baseRole The base role that determines default permissions for the space. Changing this field requires admin
 *     permissions.
 * @param resourcesAttributes Resources to attach to the space.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot-spaces/update-for-user/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateForUserPayload(
        @Nullable String name,
        @Nullable String description,
        @Nullable String generalInstructions,
        @Nullable GitHubCopilotSpaceUserBaseRole baseRole,
        @Nullable List<GitHubCopilotSpaceResourceAttributeInput> resourcesAttributes) {

    private static final int MAX_GENERAL_INSTRUCTIONS_LENGTH = 4000;

    public GitHubUpdateForUserPayload {
        generalInstructions =
                requireMaxLengthIfPresent(generalInstructions, MAX_GENERAL_INSTRUCTIONS_LENGTH, "generalInstructions");
        if (resourcesAttributes != null) {
            resourcesAttributes = List.copyOf(resourcesAttributes);
        }
    }
}
