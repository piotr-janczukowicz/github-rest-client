package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Team Member. A user that is a member of a team, including their role on the team and whether the membership is
 * inherited from a child team.
 *
 * @param name Name
 * @param email Email
 * @param login Login
 * @param id Id
 * @param nodeId Node id
 * @param avatarUrl Avatar url
 * @param gravatarId Gravatar id
 * @param url Url
 * @param htmlUrl Html url
 * @param followersUrl Followers url
 * @param followingUrl Following url
 * @param gistsUrl Gists url
 * @param starredUrl Starred url
 * @param subscriptionsUrl Subscriptions url
 * @param organizationsUrl Organizations url
 * @param reposUrl Repos url
 * @param eventsUrl Events url
 * @param receivedEventsUrl Received events url
 * @param type Type
 * @param siteAdmin Site admin
 * @param starredAt Starred at
 * @param userViewType User view type
 * @param role The member's role on the team. Only present on the `List team members` endpoint, and only when the
 *     feature is enabled for the organization.
 * @param inherited Whether the user is a member of the team only through a child team. `true` means the membership is
 *     inherited from a child team; `false` means the user is a direct (immediate) member of the team. Only present on
 *     the `List team members` endpoint, and only when the feature is enabled for the organization.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team-member")
@RecordBuilder
public record GitHubTeamMember(
        @Nullable String name,
        @Nullable String email,
        String login,
        long id,
        String nodeId,
        String avatarUrl,
        @Nullable String gravatarId,
        String url,
        String htmlUrl,
        String followersUrl,
        String followingUrl,
        String gistsUrl,
        String starredUrl,
        String subscriptionsUrl,
        String organizationsUrl,
        String reposUrl,
        String eventsUrl,
        String receivedEventsUrl,
        String type,
        boolean siteAdmin,
        @Nullable String starredAt,
        @Nullable GitHubUserViewType userViewType,
        @Nullable GitHubTeamRole role,
        @Nullable Boolean inherited) {

    public GitHubTeamMember {
        requireNonNull(login, "login");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(avatarUrl, "avatarUrl");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(followersUrl, "followersUrl");
        requireNonNull(followingUrl, "followingUrl");
        requireNonNull(gistsUrl, "gistsUrl");
        requireNonNull(starredUrl, "starredUrl");
        requireNonNull(subscriptionsUrl, "subscriptionsUrl");
        requireNonNull(organizationsUrl, "organizationsUrl");
        requireNonNull(reposUrl, "reposUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(receivedEventsUrl, "receivedEventsUrl");
        requireNonNull(type, "type");
    }
}
