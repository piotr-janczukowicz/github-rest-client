package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An external group available to be mapped to a team.
 *
 * @param groupId The internal ID of the group
 * @param groupName The display name of the group
 * @param updatedAt The time of the last update for this group
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/external-groups/properties/groups/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubExternalGroupsGroup(long groupId, String groupName, String updatedAt) {

    public GitHubExternalGroupsGroup {
        requireNonNull(groupName, "groupName");
        requireNonNull(updatedAt, "updatedAt");
    }
}
