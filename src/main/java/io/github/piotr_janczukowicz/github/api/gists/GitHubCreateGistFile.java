package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * One file of a gist to create.
 *
 * @param content Content of the file
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/gists/create/requestBody/properties/files/additionalProperties")
@RecordBuilder
public record GitHubCreateGistFile(String content) {

    public GitHubCreateGistFile {
        requireNonNull(content, "content");
    }
}
