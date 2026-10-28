package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Billable time of a workflow run per operating system.
 *
 * @param ubuntu Billable time on Ubuntu
 * @param macos Billable time on macOS
 * @param windows Billable time on Windows
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/workflow-run-usage/properties/billable")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubWorkflowRunUsageBillable(
        @JsonProperty("UBUNTU") @Nullable GitHubWorkflowRunUsageBillableOs ubuntu,
        @JsonProperty("MACOS") @Nullable GitHubWorkflowRunUsageBillableOs macos,
        @JsonProperty("WINDOWS") @Nullable GitHubWorkflowRunUsageBillableOs windows) {}
