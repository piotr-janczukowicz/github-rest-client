package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Comment author. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/pull-request-review-comment-event/properties/comment/properties/user")
@RecordBuilder
public record GitHubPullRequestReviewCommentEventCommentUser(
        @Nullable String avatarUrl,
        @Nullable Boolean deleted,
        @Nullable String email,
        @Nullable String eventsUrl,
        @Nullable String followersUrl,
        @Nullable String followingUrl,
        @Nullable String gistsUrl,
        @Nullable String gravatarId,
        @Nullable String htmlUrl,
        @Nullable Long id,
        @Nullable String login,
        @Nullable String name,
        @Nullable String nodeId,
        @Nullable String organizationsUrl,
        @Nullable String receivedEventsUrl,
        @Nullable String reposUrl,
        @Nullable Boolean siteAdmin,
        @Nullable String starredUrl,
        @Nullable String subscriptionsUrl,
        @Nullable GitHubPullRequestReviewCommentEventCommentUserType type,
        @Nullable String url,
        @Nullable String userViewType) {}
