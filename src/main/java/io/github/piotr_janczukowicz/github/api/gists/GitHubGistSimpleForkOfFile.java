package io.github.piotr_janczukowicz.github.api.gists;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * One file of the gist a simple gist was forked from.
 *
 * @param filename Filename
 * @param type Media type
 * @param language Language
 * @param rawUrl Raw url
 * @param size Size in bytes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gist-simple/properties/fork_of/properties/files/additionalProperties")
@RecordBuilder
public record GitHubGistSimpleForkOfFile(
        @Nullable String filename,
        @Nullable String type,
        @Nullable String language,
        @Nullable String rawUrl,
        @Nullable Integer size) {}
