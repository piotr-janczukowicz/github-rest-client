package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Owner of a restricted GitHub App on a branch restriction policy.
 *
 * @param login Login
 * @param id Id
 * @param nodeId Node id
 * @param url Url
 * @param reposUrl Repos url
 * @param eventsUrl Events url
 * @param hooksUrl Hooks url
 * @param issuesUrl Issues url
 * @param membersUrl Members url
 * @param publicMembersUrl Public members url
 * @param avatarUrl Avatar url
 * @param description Description
 * @param gravatarId Gravatar id
 * @param htmlUrl Html url
 * @param followersUrl Followers url
 * @param followingUrl Following url
 * @param gistsUrl Gists url
 * @param starredUrl Starred url
 * @param subscriptionsUrl Subscriptions url
 * @param organizationsUrl Organizations url
 * @param receivedEventsUrl Received events url
 * @param type Type
 * @param siteAdmin Site admin
 * @param userViewType User view type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/branch-restriction-policy/properties/apps/items/properties/owner")
public record GitHubBranchRestrictionPolicyAppOwner(
        @Nullable String login,
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable String url,
        @Nullable String reposUrl,
        @Nullable String eventsUrl,
        @Nullable String hooksUrl,
        @Nullable String issuesUrl,
        @Nullable String membersUrl,
        @Nullable String publicMembersUrl,
        @Nullable String avatarUrl,
        @Nullable String description,
        @Nullable String gravatarId,
        @Nullable String htmlUrl,
        @Nullable String followersUrl,
        @Nullable String followingUrl,
        @Nullable String gistsUrl,
        @Nullable String starredUrl,
        @Nullable String subscriptionsUrl,
        @Nullable String organizationsUrl,
        @Nullable String receivedEventsUrl,
        @Nullable String type,
        @Nullable Boolean siteAdmin,
        @Nullable String userViewType) {}
