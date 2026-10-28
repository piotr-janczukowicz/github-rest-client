package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Container metadata for a GitHub package version.
 *
 * @param tags Tags
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/package-version/properties/metadata/properties/container")
public record GitHubPackageContainerMetadata(List<String> tags) {

    public GitHubPackageContainerMetadata {
        requireNonNull(tags, "tags");
    }
}
