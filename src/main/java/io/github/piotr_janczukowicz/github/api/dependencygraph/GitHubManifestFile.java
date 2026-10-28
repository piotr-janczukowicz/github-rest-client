package io.github.piotr_janczukowicz.github.api.dependencygraph;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The file a manifest was read from.
 *
 * @param sourceLocation The path of the manifest file relative to the root of the Git repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/manifest/properties/file")
@RecordBuilder
public record GitHubManifestFile(@Nullable String sourceLocation) {}
