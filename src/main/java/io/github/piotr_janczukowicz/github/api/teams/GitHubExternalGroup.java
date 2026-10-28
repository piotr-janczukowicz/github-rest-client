package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Information about an external group's usage and its members.
 *
 * @param groupId The internal ID of the group
 * @param groupName The display name for the group
 * @param updatedAt The date when the group was last updated
 * @param teams An array of teams linked to this group
 * @param members An array of external members linked to this group
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/external-group")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubExternalGroup(
        long groupId,
        String groupName,
        @Nullable String updatedAt,
        List<GitHubExternalGroupTeam> teams,
        List<GitHubExternalGroupMember> members) {

    public GitHubExternalGroup {
        requireNonNull(groupName, "groupName");
        requireNonNull(teams, "teams");
        requireNonNull(members, "members");
    }
}
