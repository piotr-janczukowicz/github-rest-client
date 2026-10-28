package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An IdP group to connect to a GitHub team.
 *
 * @param groupId ID of the IdP group.
 * @param groupName Name of the IdP group.
 * @param groupDescription Description of the IdP group.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/create-or-update-idp-group-connections-in-org/requestBody/properties/groups/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateOrUpdateIdpGroupConnectionsInOrgGroup(
        String groupId, String groupName, String groupDescription) {

    public GitHubCreateOrUpdateIdpGroupConnectionsInOrgGroup {
        requireNonNull(groupId, "groupId");
        requireNonNull(groupName, "groupName");
        requireNonNull(groupDescription, "groupDescription");
    }
}
