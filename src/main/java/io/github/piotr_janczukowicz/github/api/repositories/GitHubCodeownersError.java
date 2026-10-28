package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * CODEOWNERS error.
 *
 * @param line Line number
 * @param column Column number
 * @param source Line contents
 * @param kind Error kind
 * @param suggestion Suggested fix
 * @param message Human-readable error message
 * @param path File path
 */
@CatalogSchema("components/schemas/codeowners-errors/properties/errors/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCodeownersError(
        int line,
        int column,
        @Nullable String source,
        String kind,
        @Nullable OptionalNull<String> suggestion,
        String message,
        String path) {

    public GitHubCodeownersError {
        requireNonNull(kind, "kind");
        requireNonNull(message, "message");
        requireNonNull(path, "path");
    }
}
