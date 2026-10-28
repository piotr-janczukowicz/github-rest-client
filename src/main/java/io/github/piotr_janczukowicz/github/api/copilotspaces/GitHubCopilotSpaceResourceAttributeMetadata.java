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
 * @param copilotChatAttachmentId Chat attachment ID for uploaded files or media.
 * @param mediaType Media type for media content resources.
 * @param url URL for media content resources.
 * @param height Height for media content resources.
 * @param width Width for media content resources.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/copilot-space/properties/resources_attributes/items/properties/metadata")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceResourceAttributeMetadata(
        @Nullable Long repositoryId,
        @Nullable String filePath,
        @Nullable String text,
        @Nullable String name,
        @Nullable Integer number,
        @Nullable Long copilotChatAttachmentId,
        @Nullable String mediaType,
        @Nullable String url,
        @Nullable Integer height,
        @Nullable Integer width) {}
