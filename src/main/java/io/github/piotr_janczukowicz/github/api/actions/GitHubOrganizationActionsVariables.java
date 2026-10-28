package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsVariable;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Organization actions variables.
 *
 * @param totalCount Total number of variables
 * @param variables The variables
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-org-variables/200")
public record GitHubOrganizationActionsVariables(int totalCount, List<GitHubOrganizationActionsVariable> variables) {

    public GitHubOrganizationActionsVariables {
        requireNonNull(variables, "variables");
    }
}
