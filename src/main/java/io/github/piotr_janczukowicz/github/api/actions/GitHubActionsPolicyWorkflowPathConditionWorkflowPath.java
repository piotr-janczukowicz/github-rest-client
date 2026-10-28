package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Workflow file paths or glob patterns an Actions policy applies to.
 *
 * @param include Array of workflow file paths or glob patterns to include. An empty array includes all workflows not
 *     matched by an excluded pattern. Use {@code ~ALL} by itself to include all workflows. {@code ~ALL} cannot be
 *     combined with other included patterns.
 * @param exclude Array of workflow file paths or glob patterns to exclude. The condition will not pass if any of these
 *     patterns match. {@code ~ALL} is not allowed in this array.
 */
@CatalogSchema("components/schemas/actions-policy-workflow-path-condition/properties/workflow_path")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsPolicyWorkflowPathConditionWorkflowPath(List<String> include, List<String> exclude) {

    public GitHubActionsPolicyWorkflowPathConditionWorkflowPath {
        include = List.copyOf(requireNonNull(include, "include"));
        exclude = List.copyOf(requireNonNull(exclude, "exclude"));
    }
}
