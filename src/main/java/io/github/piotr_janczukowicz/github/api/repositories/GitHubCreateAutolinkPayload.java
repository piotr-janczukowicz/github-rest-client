package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create autolink reference.
 *
 * @param keyPrefix This prefix appended by certain characters will generate a link any time it is found.
 * @param urlTemplate The URL must contain {@code <num>} for the reference number.
 * @param isAlphanumeric Whether this autolink reference matches alphanumeric characters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-autolink/requestBody")
public record GitHubCreateAutolinkPayload(
        String keyPrefix, String urlTemplate, @Nullable Boolean isAlphanumeric) {
    public GitHubCreateAutolinkPayload {
        requireNonNull(keyPrefix, "keyPrefix");
        requireNonNull(urlTemplate, "urlTemplate");
    }
}
