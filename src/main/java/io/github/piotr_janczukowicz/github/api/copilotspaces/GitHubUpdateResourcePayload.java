package io.github.piotr_janczukowicz.github.api.copilotspaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Set a resource for a Copilot Space.
 *
 * @param metadata Updated resource-specific metadata.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/copilot-spaces/update-resource-for-org/requestBody",
    "operations/copilot-spaces/update-resource-for-user/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateResourcePayload(@Nullable Map<String, Object> metadata) {

    public GitHubUpdateResourcePayload {
        if (metadata != null) {
            metadata = Collections.unmodifiableMap(new LinkedHashMap<>(metadata));
        }
    }
}
