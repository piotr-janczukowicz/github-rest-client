package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Organizations to assign an enterprise team to.
 *
 * @param organizationSlugs Organization slug to assign the team to.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-team-organizations/bulk-add/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubBulkAddEnterpriseTeamOrganizationsPayload(List<String> organizationSlugs) {

    public GitHubBulkAddEnterpriseTeamOrganizationsPayload {
        requireNonNull(organizationSlugs, "organizationSlugs");
        organizationSlugs = List.copyOf(organizationSlugs);
    }
}
