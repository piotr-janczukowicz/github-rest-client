package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Create pull request review comment request body. {@code position} is closing down; use {@code line} instead. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/create-review-comment/requestBody")
public record GitHubCreateReviewCommentPayload(
        String body,
        String commitId,
        String path,
        @Deprecated(since = "2022-11-01") @Nullable Integer position,
        @Nullable GitHubPullRequestReviewCommentSide side,
        @Nullable Integer line,
        @Nullable Integer startLine,
        @Nullable GitHubPullRequestReviewCommentStartSide startSide,
        @Nullable Long inReplyTo,
        @Nullable GitHubPullRequestReviewCommentSubjectType subjectType) {

    public GitHubCreateReviewCommentPayload {
        requireNotBlank(body, "body");
        requireNotBlank(commitId, "commitId");
        requireNotBlank(path, "path");
    }
}
