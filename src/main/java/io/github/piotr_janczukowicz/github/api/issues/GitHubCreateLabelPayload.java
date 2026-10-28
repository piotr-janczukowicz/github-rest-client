package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create a label.
 *
 * @param name The name of the label. Emoji can be added to label names, using either native emoji or colon-style
 *     markup. For example, typing `:strawberry:` will render the emoji. For a full list of available emoji and codes,
 *     see "Emoji cheat sheet".
 * @param color The hexadecimal color code for the label, without the leading `#`.
 * @param description A short description of the label. Must be 100 characters or fewer.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/create-label/requestBody")
@RecordBuilder
public record GitHubCreateLabelPayload(
        String name, @Nullable String color, @Nullable String description) {

    public GitHubCreateLabelPayload {
        requireNonNull(name, "name");
    }
}
