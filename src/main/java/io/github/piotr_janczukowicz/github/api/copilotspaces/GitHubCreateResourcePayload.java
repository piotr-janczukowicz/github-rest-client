package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create a resource for a Copilot Space.
 *
 * @param resourceType The type of resource to create.
 * @param metadata Resource-specific metadata.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/copilot-spaces/create-resource-for-org/requestBody",
    "operations/copilot-spaces/create-resource-for-user/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateResourcePayload(GitHubCreateResourceType resourceType, Map<String, Object> metadata) {

    public GitHubCreateResourcePayload {
        requireNonNull(resourceType, "resourceType");
        metadata = Collections.unmodifiableMap(new LinkedHashMap<>(requireNonNull(metadata, "metadata")));
    }
}
