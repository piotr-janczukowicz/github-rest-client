package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A user collaborator of a Copilot Space.
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
 * @param actorType The collaborator actor type.
 * @param role The role granted to the collaborator
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/copilot-space-collaborator/anyOf/0")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotSpaceUserCollaborator(
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
        GitHubCopilotSpaceUserCollaboratorActorType actorType,
        GitHubCopilotSpaceCollaboratorRole role)
        implements GitHubCopilotSpaceCollaborator {

    public GitHubCopilotSpaceUserCollaborator {
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
        requireNonNull(actorType, "actorType");
        requireNonNull(role, "role");
    }
}
