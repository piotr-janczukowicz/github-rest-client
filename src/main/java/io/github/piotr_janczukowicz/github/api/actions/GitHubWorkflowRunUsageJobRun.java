package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Duration of one job run.
 *
 * @param jobId The ID of the job
 * @param durationMs The duration of the job in milliseconds
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/workflow-run-usage/properties/billable/properties/UBUNTU/properties/job_runs/items",
    "components/schemas/workflow-run-usage/properties/billable/properties/MACOS/properties/job_runs/items",
    "components/schemas/workflow-run-usage/properties/billable/properties/WINDOWS/properties/job_runs/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubWorkflowRunUsageJobRun(long jobId, int durationMs) {}
