package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create or update IdP group connections of a team. The new {@code groups} replace the original ones.
 *
 * @param groups The IdP groups to connect to a GitHub team
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/create-or-update-idp-group-connections-in-org/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload(
        @Nullable List<GitHubCreateOrUpdateIdpGroupConnectionsInOrgGroup> groups) {

    public GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload {
        if (groups != null) {
            groups = List.copyOf(groups);
        }
    }
}
