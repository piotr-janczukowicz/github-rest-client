package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Contributor.
 *
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
 * @param contributions Contributions
 * @param email Email
 * @param name Name
 * @param userViewType User view type
 */
@CatalogSchema("components/schemas/contributor")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubContributor(
        @Nullable String login,
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable String avatarUrl,
        @Nullable String gravatarId,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String followersUrl,
        @Nullable String followingUrl,
        @Nullable String gistsUrl,
        @Nullable String starredUrl,
        @Nullable String subscriptionsUrl,
        @Nullable String organizationsUrl,
        @Nullable String reposUrl,
        @Nullable String eventsUrl,
        @Nullable String receivedEventsUrl,
        String type,
        @Nullable Boolean siteAdmin,
        int contributions,
        @Nullable String email,
        @Nullable String name,
        @Nullable String userViewType) {
    public GitHubContributor {
        requireNonNull(type, "type");
    }
}
