package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserType;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The relationship a user has with an organization role.
 *
 * @param assignment Determines if the user has a direct, indirect, or mixed relationship to a role
 * @param inheritedFrom Teams the user has gotten the role through
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
 */
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/user-role-assignment")
public record GitHubUserRoleAssignment(
        @Nullable GitHubOrganizationRoleAssignment assignment,
        @Nullable List<GitHubTeamSimple> inheritedFrom,
        @Nullable String name,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable String email,
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
        GitHubUserType type,
        boolean siteAdmin,
        @Nullable String starredAt,
        @Nullable GitHubUserViewType userViewType) {

    public GitHubUserRoleAssignment {
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
