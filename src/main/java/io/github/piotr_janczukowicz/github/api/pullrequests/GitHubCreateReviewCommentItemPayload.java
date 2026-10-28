package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Nested review comment item for create review request bodies. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/create-review/requestBody/properties/comments/items")
public record GitHubCreateReviewCommentItemPayload(
        String path,
        String body,
        @Nullable Integer position,
        @Nullable Integer line,
        @Nullable String side,
        @Nullable Integer startLine,
        @Nullable String startSide) {

    public GitHubCreateReviewCommentItemPayload {
        requireNotBlank(path, "path");
        requireNotBlank(body, "body");
    }
}
