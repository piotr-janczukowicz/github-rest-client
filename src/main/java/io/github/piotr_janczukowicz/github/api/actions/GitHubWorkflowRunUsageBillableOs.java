package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Billable time of a workflow run on one operating system.
 *
 * @param totalMs Total milliseconds of billable time
 * @param jobs Number of jobs run on this operating system
 * @param jobRuns Runs of each job
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/workflow-run-usage/properties/billable/properties/UBUNTU",
    "components/schemas/workflow-run-usage/properties/billable/properties/MACOS",
    "components/schemas/workflow-run-usage/properties/billable/properties/WINDOWS"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubWorkflowRunUsageBillableOs(
        int totalMs, int jobs, @Nullable List<GitHubWorkflowRunUsageJobRun> jobRuns) {}
