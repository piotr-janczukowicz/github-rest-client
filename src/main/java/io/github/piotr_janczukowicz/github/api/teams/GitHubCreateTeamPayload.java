package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create team.
 *
 * @param name The name of the team.
 * @param description The description of the team.
 * @param maintainers List GitHub usernames for organization members who will become team maintainers.
 * @param repoNames The full name (e.g., "organization-name/repository-name") of repositories to add the team to.
 * @param privacy The level of privacy this team should have. The options are: **For a non-nested team:** * `secret` -
 *     only visible to organization owners and members of this team. * `closed` - visible to all members of this
 *     organization. Default: `secret` **For a parent or child team:** * `closed` - visible to all members of this
 *     organization. Default for child team: `closed`
 * @param notificationSetting The notification setting the team has chosen. The options are: * `notifications_enabled` -
 *     team members receive notifications when the team is @mentioned. * `notifications_disabled` - no one receives
 *     notifications. Default: `notifications_enabled`
 * @param parentTeamId The ID of a team to set as the parent team.
 * @param parentTeamSlug The slug of a team to set as the parent team. Ignored when `parent_team_id` is also provided.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/create/requestBody")
public record GitHubCreateTeamPayload(
        String name,
        @Nullable String description,
        @Nullable List<String> maintainers,
        @Nullable List<String> repoNames,
        @Nullable GitHubTeamPrivacy privacy,
        @Nullable GitHubTeamNotificationSetting notificationSetting,
        @Nullable Long parentTeamId,
        @Nullable String parentTeamSlug,
        @Nullable String ldapDn) {

    public GitHubCreateTeamPayload {
        requireNonNull(name, "name");
        if (maintainers != null) {
            maintainers = List.copyOf(maintainers);
        }
        if (repoNames != null) {
            repoNames = List.copyOf(repoNames);
        }
    }
}
