package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update team.
 *
 * @param name The name of the team.
 * @param description The description of the team.
 * @param privacy The level of privacy this team should have. Editing teams without specifying this parameter leaves
 *     `privacy` intact. When a team is nested, the `privacy` for parent teams cannot be `secret`. The options are:
 *     **For a non-nested team:** * `secret` - only visible to organization owners and members of this team. * `closed`
 *     - visible to all members of this organization. **For a parent or child team:** * `closed` - visible to all
 *     members of this organization.
 * @param notificationSetting The notification setting the team has chosen. Editing teams without specifying this
 *     parameter leaves `notification_setting` intact. The options are: * `notifications_enabled` - team members receive
 *     notifications when the team is @mentioned. * `notifications_disabled` - no one receives notifications.
 * @param permission **Closing down notice**. The permission that new repositories will be added to the team with when
 *     none is specified.
 * @param parentTeamId The ID of a team to set as the parent team.
 * @param parentTeamSlug The slug of a team to set as the parent team. Ignored when `parent_team_id` is also provided.
 * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams API.
 *     Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
 */
@Deprecated(since = "2020-01-21")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/update-legacy/requestBody")
public record GitHubUpdateTeamLegacyPayload(
        String name,
        @Nullable String description,
        @Nullable GitHubTeamPrivacy privacy,
        @Nullable GitHubTeamNotificationSetting notificationSetting,
        @Nullable GitHubTeamLegacyPermission permission,
        @Nullable OptionalNull<Long> parentTeamId,
        @Nullable OptionalNull<String> parentTeamSlug) {

    public GitHubUpdateTeamLegacyPayload {
        requireNonNull(name, "name");
    }
}
