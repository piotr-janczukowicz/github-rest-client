package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.teams.GitHubEnterpriseTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The relationship a user has with a role in an enterprise context.
 *
 * @param name The name of the user.
 * @param email The email address of the user.
 * @param login The username of the user.
 * @param id The unique identifier of the user.
 * @param nodeId The GraphQL node id of the user.
 * @param avatarUrl The avatar URL of the user.
 * @param gravatarId The gravatar id of the user.
 * @param url The API URL of the user.
 * @param htmlUrl The profile URL of the user.
 * @param followersUrl The followers URL of the user.
 * @param followingUrl The following URL of the user.
 * @param gistsUrl The gists URL of the user.
 * @param starredUrl The starred URL of the user.
 * @param subscriptionsUrl The subscriptions URL of the user.
 * @param organizationsUrl The organizations URL of the user.
 * @param reposUrl The repositories URL of the user.
 * @param eventsUrl The events URL of the user.
 * @param receivedEventsUrl The received events URL of the user.
 * @param type The account type of the user.
 * @param siteAdmin Whether the user is a site administrator.
 * @param starredAt When the user starred the repository.
 * @param userViewType The view type of the user.
 * @param assignment Determines if the user has a direct, indirect, or mixed relationship to a role.
 * @param inheritedFrom Enterprise team the user has gotten the role through.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-user-role-assignment")
@Flavors(GitHubFlavor.GHEC)
public record GitHubEnterpriseUserRoleAssignment(
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
        @Nullable GitHubEnterpriseRoleAssignment assignment,
        @Nullable List<GitHubEnterpriseTeam> inheritedFrom) {

    public GitHubEnterpriseUserRoleAssignment {
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
