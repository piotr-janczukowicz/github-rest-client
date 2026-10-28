package io.github.piotr_janczukowicz.github.api.copilotspaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Metadata specific to the resource type.
 *
 * @param repositoryId Repository ID for repository or file resources.
 * @param filePath File path for file resources.
 * @param text Text content for free text resources.
 * @param name Name for the resource.
 * @param number Issue or PR number.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/copilot-spaces/create-for-org/requestBody/properties/resources_attributes/items/properties/metadata",
    "operations/copilot-spaces/update-for-org/requestBody/properties/resources_attributes/items/properties/metadata",
    "operations/copilot-spaces/create-for-user/requestBody/properties/resources_attributes/items/properties/metadata",
    "operations/copilot-spaces/update-for-user/requestBody/properties/resources_attributes/items/properties/metadata"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceResourceAttributeInputMetadata(
        @Nullable Long repositoryId,
        @Nullable String filePath,
        @Nullable String text,
        @Nullable String name,
        @Nullable Integer number) {}
