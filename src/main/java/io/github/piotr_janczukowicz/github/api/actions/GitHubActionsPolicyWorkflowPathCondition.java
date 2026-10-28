package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Parameters for an Actions policy workflow path condition. Omitting {@code workflow_path} when creating a policy
 * targets all workflows without storing an explicit condition. Omitting it when updating a policy preserves the
 * existing workflow targeting.
 *
 * @param workflowPath Workflow file paths or glob patterns the policy applies to
 */
@CatalogSchema("components/schemas/actions-policy-workflow-path-condition")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsPolicyWorkflowPathCondition(
        @Nullable GitHubActionsPolicyWorkflowPathConditionWorkflowPath workflowPath) {}
