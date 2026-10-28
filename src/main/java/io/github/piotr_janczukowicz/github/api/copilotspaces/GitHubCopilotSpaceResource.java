package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * A resource attached to a Copilot Space.
 *
 * @param id The unique identifier of the resource.
 * @param resourceType The type of the resource.
 * @param copilotChatAttachmentId The ID of the associated chat attachment, if any.
 * @param metadata Resource-specific metadata. The keys and values depend on the resource type.
 * @param createdAt The date and time the resource was created.
 * @param updatedAt The date and time the resource was last updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/copilot-space-resource")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceResource(
        long id,
        GitHubCopilotSpaceResourceType resourceType,
        @Nullable Long copilotChatAttachmentId,
        Map<String, Object> metadata,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubCopilotSpaceResource {
        requireNonNull(resourceType, "resourceType");
        requireNonNull(metadata, "metadata");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
