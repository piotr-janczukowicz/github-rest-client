package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Restricted user on a branch restriction policy.
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
 * @param userViewType User view type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/branch-restriction-policy/properties/users/items")
public record GitHubBranchRestrictionPolicyUser(
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
        @Nullable String type,
        @Nullable Boolean siteAdmin,
        @Nullable String userViewType) {}
