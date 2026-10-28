package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Docker metadata for a GitHub package version.
 *
 * @param tags Tags
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/package-version/properties/metadata/properties/docker")
public record GitHubPackageDockerMetadata(List<String> tags) {

    public GitHubPackageDockerMetadata {
        requireNonNull(tags, "tags");
    }
}
