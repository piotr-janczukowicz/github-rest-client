package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Conditions for a repository Actions policy. The object may be empty to preserve or use the default workflow
 * targeting, or contain only {@code workflow_path}.
 *
 * @param workflowPath Workflow file paths or glob patterns the policy applies to
 */
@CatalogSchema("components/schemas/actions-policy-repo-conditions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsPolicyRepoConditions(
        @Nullable GitHubActionsPolicyWorkflowPathConditionWorkflowPath workflowPath) {}
