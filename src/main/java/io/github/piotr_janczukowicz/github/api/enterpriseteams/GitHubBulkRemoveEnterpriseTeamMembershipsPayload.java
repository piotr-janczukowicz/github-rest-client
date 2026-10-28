package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Users to remove from an enterprise team.
 *
 * @param usernames The GitHub user handles to be removed from the team.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-team-memberships/bulk-remove/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubBulkRemoveEnterpriseTeamMembershipsPayload(List<String> usernames) {

    public GitHubBulkRemoveEnterpriseTeamMembershipsPayload {
        requireNonNull(usernames, "usernames");
        usernames = List.copyOf(usernames);
    }
}
