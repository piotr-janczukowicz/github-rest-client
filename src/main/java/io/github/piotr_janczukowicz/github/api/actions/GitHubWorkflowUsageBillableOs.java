package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Billable time of a workflow on one operating system.
 *
 * @param totalMs Total milliseconds of billable time
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/workflow-usage/properties/billable/properties/UBUNTU",
    "components/schemas/workflow-usage/properties/billable/properties/MACOS",
    "components/schemas/workflow-usage/properties/billable/properties/WINDOWS"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubWorkflowUsageBillableOs(@Nullable Integer totalMs) {}
