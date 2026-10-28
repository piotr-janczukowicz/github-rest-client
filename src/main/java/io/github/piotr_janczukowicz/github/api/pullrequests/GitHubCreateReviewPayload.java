package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Create pull request review request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/create-review/requestBody")
public record GitHubCreateReviewPayload(
        @Nullable String commitId,
        @Nullable String body,
        @Nullable GitHubPullRequestReviewEvent event,
        @Nullable List<GitHubCreateReviewCommentItemPayload> comments) {

    public GitHubCreateReviewPayload {
        if (comments != null) {
            comments = List.copyOf(comments);
        }
    }
}
