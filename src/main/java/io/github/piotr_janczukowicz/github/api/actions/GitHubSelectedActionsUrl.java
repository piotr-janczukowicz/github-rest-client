package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The API URL to use to get or set the actions and reusable workflows that are allowed to run, when allowed_actions is
 * set to selected.
 *
 * @param value JSON value
 */
@CatalogSchema("components/schemas/selected-actions-url")
public record GitHubSelectedActionsUrl(@JsonValue String value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public GitHubSelectedActionsUrl {
        requireNonNull(value, "value");
    }
}
