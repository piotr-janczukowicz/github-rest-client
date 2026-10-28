package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Reaction counts. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/pull-request-review-comment-event/properties/comment/properties/reactions")
@RecordBuilder
public record GitHubPullRequestReviewCommentEventCommentReactions(
        @Nullable @JsonProperty("+1") Integer plusOne,
        @Nullable @JsonProperty("-1") Integer minusOne,
        @Nullable Integer confused,
        @Nullable Integer eyes,
        @Nullable Integer heart,
        @Nullable Integer hooray,
        @Nullable Integer laugh,
        @Nullable Integer rocket,
        @Nullable Integer totalCount,
        @Nullable String url) {}
