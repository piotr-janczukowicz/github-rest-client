package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * An external group mapped to a team.
 *
 * @param groupId The ID of the group
 * @param groupName The name of the group
 * @param groupDescription A description of the group
 * @param status The status of the group
 * @param syncedAt The time the group was last synced
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/group-mapping/properties/groups/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGroupMappingGroup(
        String groupId,
        String groupName,
        String groupDescription,
        @Nullable String status,
        @Nullable String syncedAt) {

    public GitHubGroupMappingGroup {
        requireNonNull(groupId, "groupId");
        requireNonNull(groupName, "groupName");
        requireNonNull(groupDescription, "groupDescription");
    }
}
