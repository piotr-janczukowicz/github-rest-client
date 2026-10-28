package io.github.piotr_janczukowicz.github.api.gists;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * One gist file to update or rename.
 *
 * @param content The new content of the file.
 * @param filename The new filename for the file.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/gists/update/requestBody/properties/files/additionalProperties")
@RecordBuilder
public record GitHubUpdateGistFile(
        @Nullable String content, @Nullable String filename) {}
