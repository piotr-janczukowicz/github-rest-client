package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * A package manifest: a collection of related dependencies declared in a file or a logical group of dependencies.
 *
 * @param name The name of the manifest.
 * @param file The file the manifest was read from.
 * @param metadata User-defined metadata to store domain-specific information limited to 8 keys with scalar values.
 * @param resolved A collection of resolved package dependencies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/manifest")
@RecordBuilder
public record GitHubManifest(
        String name,
        @Nullable GitHubManifestFile file,
        @Nullable @CatalogSimpleXor("string|number|boolean") Map<String, String> metadata,
        @Nullable Map<String, GitHubDependency> resolved) {

    public GitHubManifest {
        requireNonNull(name, "name");
        if (metadata != null) {
            metadata = Map.copyOf(requireSizeAtMost(metadata, 8, "metadata"));
        }
        if (resolved != null) {
            resolved = Map.copyOf(resolved);
        }
    }
}
