package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Organizations that can access a self-hosted runner group of an enterprise.
 *
 * @param selectedOrganizationIds List of organization IDs that can access the runner group
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/set-org-access-to-self-hosted-runner-group-in-enterprise/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubSetOrgAccessToSelfHostedRunnerGroupInEnterprisePayload(List<Long> selectedOrganizationIds) {

    public GitHubSetOrgAccessToSelfHostedRunnerGroupInEnterprisePayload {
        selectedOrganizationIds = List.copyOf(requireNonNull(selectedOrganizationIds, "selectedOrganizationIds"));
    }
}
