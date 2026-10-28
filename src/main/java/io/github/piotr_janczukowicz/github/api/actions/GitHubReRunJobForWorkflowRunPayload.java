package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Re-run a job of a workflow run.
 *
 * @param enableDebugLogging Whether to enable debug logging for the re-run
 * @param enableDebugger Whether to enable the debugger for the re-run of this job
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/re-run-job-for-workflow-run/requestBody")
public record GitHubReRunJobForWorkflowRunPayload(
        @Nullable Boolean enableDebugLogging, @Nullable Boolean enableDebugger) {}
