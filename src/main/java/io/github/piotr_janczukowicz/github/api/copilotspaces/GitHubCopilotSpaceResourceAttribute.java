package io.github.piotr_janczukowicz.github.api.copilotspaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A resource attached to a Copilot Space, as embedded in the space.
 *
 * @param id The unique identifier of the resource.
 * @param resourceType The type of resource.
 * @param copilotChatAttachmentId The unique identifier of the chat attachment for uploaded files or media content.
 * @param createdAt The date and time the resource was created.
 * @param updatedAt The date and time the resource was last updated.
 * @param metadata Metadata specific to the resource type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/copilot-space/properties/resources_attributes/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceResourceAttribute(
        @Nullable Long id,
        @Nullable GitHubCopilotSpaceResourceType resourceType,
        @Nullable Long copilotChatAttachmentId,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable GitHubCopilotSpaceResourceAttributeMetadata metadata) {}
