package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Whether GitHub Actions is enabled on the repository.
 *
 * @param value JSON value
 */
@CatalogSchema("components/schemas/actions-enabled")
public record GitHubActionsEnabled(@JsonValue boolean value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public GitHubActionsEnabled {
        // Empty on purpose: the compact constructor only carries @JsonCreator, so Jackson reads the bare value.
    }
}
