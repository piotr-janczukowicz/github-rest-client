package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Whether actions must be pinned to a full-length commit SHA.
 *
 * @param value JSON value
 */
@CatalogSchema("components/schemas/sha-pinning-required")
public record GitHubShaPinningRequired(@JsonValue boolean value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public GitHubShaPinningRequired {
        // Empty on purpose: the compact constructor only carries @JsonCreator, so Jackson reads the bare value.
    }
}
