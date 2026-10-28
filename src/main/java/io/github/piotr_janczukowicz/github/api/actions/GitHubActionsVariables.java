package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsVariable;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Actions variables of an environment or a repository.
 *
 * @param totalCount Total number of variables
 * @param variables The variables
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/actions/list-environment-variables/200",
    "operations/actions/list-repo-variables/200",
    "operations/actions/list-repo-organization-variables/200"
})
public record GitHubActionsVariables(int totalCount, List<GitHubActionsVariable> variables) {

    public GitHubActionsVariables {
        requireNonNull(variables, "variables");
    }
}
