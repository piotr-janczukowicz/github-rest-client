package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Workflows of a repository.
 *
 * @param totalCount Total number of workflows
 * @param workflows The workflows
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-repo-workflows/200")
public record GitHubWorkflows(int totalCount, List<GitHubWorkflow> workflows) {

    public GitHubWorkflows {
        requireNonNull(workflows, "workflows");
    }
}
