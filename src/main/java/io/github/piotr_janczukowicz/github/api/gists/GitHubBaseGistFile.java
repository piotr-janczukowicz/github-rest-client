package io.github.piotr_janczukowicz.github.api.gists;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * One file of a base gist.
 *
 * @param filename Filename
 * @param type Media type
 * @param language Language
 * @param rawUrl Raw url
 * @param size Size in bytes
 * @param encoding The encoding used for {@code content}. Currently, {@code "utf-8"} and {@code "base64"} are supported.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/base-gist/properties/files/additionalProperties")
@RecordBuilder
public record GitHubBaseGistFile(
        @Nullable String filename,
        @Nullable String type,
        @Nullable String language,
        @Nullable String rawUrl,
        @Nullable Integer size,
        @Nullable String encoding) {}
