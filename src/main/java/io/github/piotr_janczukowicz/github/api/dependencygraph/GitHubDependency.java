package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.PACKAGE_URL;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatchesIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * A dependency declared in a manifest.
 *
 * @param packageUrl Package-url (PURL) of dependency. See https://github.com/package-url/purl-spec for more details.
 * @param metadata User-defined metadata to store domain-specific information limited to 8 keys with scalar values.
 * @param relationship A notation of whether a dependency is requested directly by this manifest or is a dependency of
 *     another dependency.
 * @param scope A notation of whether the dependency is required for the primary build artifact (runtime) or is only
 *     used for development. Future versions of this specification may allow for more granular scopes.
 * @param dependencies Array of package-url (PURLs) of direct child dependencies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/dependency")
@RecordBuilder
public record GitHubDependency(
        @Nullable String packageUrl,
        @Nullable @CatalogSimpleXor("string|number|boolean") Map<String, String> metadata,
        @Nullable GitHubDependencyRelationship relationship,
        @Nullable GitHubDependencyScope scope,
        @Nullable List<String> dependencies) {

    public GitHubDependency {
        packageUrl = requireMatchesIfPresent(packageUrl, PACKAGE_URL, "packageUrl");
        if (metadata != null) {
            metadata = Map.copyOf(requireSizeAtMost(metadata, 8, "metadata"));
        }
        if (dependencies != null) {
            dependencies = List.copyOf(dependencies);
        }
    }
}
