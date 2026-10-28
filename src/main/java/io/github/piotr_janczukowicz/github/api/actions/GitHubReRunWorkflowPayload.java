package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Re-run a workflow run.
 *
 * @param enableDebugLogging Whether to enable debug logging for the re-run
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/re-run-workflow/requestBody")
public record GitHubReRunWorkflowPayload(@Nullable Boolean enableDebugLogging) {}
