package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Level of access that workflows outside of the repository have to actions and reusable workflows.
 *
 * @param accessLevel The level of access
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-workflow-access-to-repository")
public record GitHubActionsWorkflowAccessToRepository(GitHubWorkflowAccessLevel accessLevel) {

    public GitHubActionsWorkflowAccessToRepository {
        requireNonNull(accessLevel, "accessLevel");
    }
}
