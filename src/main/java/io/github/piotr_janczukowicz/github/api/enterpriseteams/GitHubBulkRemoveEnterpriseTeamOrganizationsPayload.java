package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Organizations to unassign an enterprise team from.
 *
 * @param organizationSlugs Organization slug to unassign the team from.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-team-organizations/bulk-remove/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubBulkRemoveEnterpriseTeamOrganizationsPayload(List<String> organizationSlugs) {

    public GitHubBulkRemoveEnterpriseTeamOrganizationsPayload {
        requireNonNull(organizationSlugs, "organizationSlugs");
        organizationSlugs = List.copyOf(organizationSlugs);
    }
}
