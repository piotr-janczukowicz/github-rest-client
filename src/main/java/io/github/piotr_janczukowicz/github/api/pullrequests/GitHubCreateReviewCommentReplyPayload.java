package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Create pull request review comment reply request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/create-reply-for-review-comment/requestBody")
public record GitHubCreateReviewCommentReplyPayload(String body) {

    public GitHubCreateReviewCommentReplyPayload {
        requireNotBlank(body, "body");
    }
}
