package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Metadata for a GitHub package version.
 *
 * @param packageType Package type
 * @param container Container metadata
 * @param docker Docker metadata
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/package-version/properties/metadata")
public record GitHubPackageVersionMetadata(
        GitHubPackageType packageType,
        @Nullable GitHubPackageContainerMetadata container,
        @Nullable GitHubPackageDockerMetadata docker) {

    public GitHubPackageVersionMetadata {
        requireNonNull(packageType, "packageType");
    }
}
