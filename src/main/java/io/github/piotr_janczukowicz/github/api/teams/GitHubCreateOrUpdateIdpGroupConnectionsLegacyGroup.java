package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * An IdP group to connect to a GitHub team (legacy).
 *
 * @param groupId ID of the IdP group.
 * @param groupName Name of the IdP group.
 * @param groupDescription Description of the IdP group.
 * @param id Identifier of the group in the external system.
 * @param name Name of the group in the external system.
 * @param description Description of the group in the external system.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/create-or-update-idp-group-connections-legacy/requestBody/properties/groups/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateOrUpdateIdpGroupConnectionsLegacyGroup(
        String groupId,
        String groupName,
        String groupDescription,
        @Nullable String id,
        @Nullable String name,
        @Nullable String description) {

    public GitHubCreateOrUpdateIdpGroupConnectionsLegacyGroup {
        requireNonNull(groupId, "groupId");
        requireNonNull(groupName, "groupName");
        requireNonNull(groupDescription, "groupDescription");
    }
}
