package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Pull request review. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/pull-request-review-event/properties/review")
@RecordBuilder
public record GitHubPullRequestReviewEventReview(
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable GitHubSimpleUser user,
        @Nullable String body,
        @Nullable String commitId,
        @Nullable String submittedAt,
        @Nullable String state,
        @Nullable String htmlUrl,
        @Nullable String pullRequestUrl,
        @Nullable @JsonProperty("_links") GitHubPullRequestReviewEventReviewLinks links,
        @Nullable String updatedAt,
        @Nullable GitHubAuthorAssociation authorAssociation) {}
