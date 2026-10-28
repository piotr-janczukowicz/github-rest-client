package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Autolink reference.
 *
 * @param id Id
 * @param keyPrefix The prefix of a key that is linkified.
 * @param urlTemplate A template for the target URL that is generated if a key was found.
 * @param isAlphanumeric Whether this autolink reference matches alphanumeric characters.
 * @param updatedAt Updated at
 */
@CatalogSchema("components/schemas/autolink")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubAutolink(
        long id,
        String keyPrefix,
        String urlTemplate,
        boolean isAlphanumeric,
        @Nullable Instant updatedAt) {

    public GitHubAutolink {
        requireNonNull(keyPrefix, "keyPrefix");
        requireNonNull(urlTemplate, "urlTemplate");
    }
}
