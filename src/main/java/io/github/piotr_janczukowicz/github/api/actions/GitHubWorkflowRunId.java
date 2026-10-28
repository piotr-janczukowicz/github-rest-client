package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The ID of the workflow run.
 *
 * @param value JSON value
 */
@CatalogSchema("components/schemas/workflow-run-id")
public record GitHubWorkflowRunId(@JsonValue long value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public GitHubWorkflowRunId {
        // Empty on purpose: the compact constructor only carries @JsonCreator, so Jackson reads the bare value.
    }
}
