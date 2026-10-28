package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Create a workflow dispatch event.
 *
 * @param ref The git reference for the workflow, a branch or tag name
 * @param inputs Input keys and values configured in the workflow file
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/create-workflow-dispatch/requestBody")
public record GitHubCreateWorkflowDispatchPayload(
        String ref, @Nullable Map<String, Object> inputs) {

    public GitHubCreateWorkflowDispatchPayload {
        requireNonNull(ref, "ref");
        if (inputs != null) {
            requireSizeAtMost(inputs, 25, "inputs");
        }
        if (inputs != null) {
            inputs = Collections.unmodifiableMap(new LinkedHashMap<>(inputs));
        }
    }
}
