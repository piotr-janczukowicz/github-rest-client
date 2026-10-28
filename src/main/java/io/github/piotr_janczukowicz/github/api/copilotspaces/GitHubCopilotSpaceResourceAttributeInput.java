package io.github.piotr_janczukowicz.github.api.copilotspaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A resource to attach to a Copilot Space.
 *
 * @param resourceType The type of resource.
 * @param metadata Metadata specific to the resource type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/copilot-spaces/create-for-org/requestBody/properties/resources_attributes/items",
    "operations/copilot-spaces/update-for-org/requestBody/properties/resources_attributes/items",
    "operations/copilot-spaces/create-for-user/requestBody/properties/resources_attributes/items",
    "operations/copilot-spaces/update-for-user/requestBody/properties/resources_attributes/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceResourceAttributeInput(
        @Nullable GitHubCopilotSpaceResourceType resourceType,
        @Nullable GitHubCopilotSpaceResourceAttributeInputMetadata metadata) {}
