package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create blob.
 *
 * @param content The new blob's content.
 * @param encoding The encoding used for {@code content}. Currently, {@code "utf-8"} and {@code "base64"} are supported.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/create-blob/requestBody")
public record GitHubCreateBlobPayload(
        String content, @Nullable String encoding) {

    public GitHubCreateBlobPayload {
        requireNonNull(content, "content");
    }
}
