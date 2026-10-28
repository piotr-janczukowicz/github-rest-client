package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Organizations selected to have GitHub Actions enabled in an enterprise.
 *
 * @param selectedOrganizationIds List of organization IDs to enable for GitHub Actions.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/set-selected-organizations-enabled-github-actions-enterprise/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayload(List<Long> selectedOrganizationIds) {

    public GitHubSetSelectedOrganizationsEnabledGithubActionsEnterprisePayload {
        selectedOrganizationIds = List.copyOf(requireNonNull(selectedOrganizationIds, "selectedOrganizationIds"));
    }
}
