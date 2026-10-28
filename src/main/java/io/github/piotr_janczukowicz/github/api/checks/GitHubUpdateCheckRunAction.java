package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Action button displayed on GitHub for a check run when updating it.
 *
 * @param label The text to be displayed on a button in the web UI.
 * @param description A short explanation of what this action would do.
 * @param identifier A reference for the action on the integrator's system.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/update/requestBody/properties/actions/items")
public record GitHubUpdateCheckRunAction(String label, String description, String identifier) {

    public GitHubUpdateCheckRunAction {
        requireNonNull(label, "label");
        requireNonNull(description, "description");
        requireNonNull(identifier, "identifier");
        label = requireMaxLength(label, 20, "label");
        description = requireMaxLength(description, 40, "description");
        identifier = requireMaxLength(identifier, 20, "identifier");
    }
}
