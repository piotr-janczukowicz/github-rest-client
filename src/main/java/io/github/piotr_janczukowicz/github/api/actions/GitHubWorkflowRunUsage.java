package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Workflow run usage.
 *
 * @param billable The billable time per operating system
 * @param runDurationMs The duration of the workflow run in milliseconds
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/workflow-run-usage")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubWorkflowRunUsage(
        GitHubWorkflowRunUsageBillable billable, @Nullable Integer runDurationMs) {

    public GitHubWorkflowRunUsage {
        requireNonNull(billable, "billable");
    }
}
