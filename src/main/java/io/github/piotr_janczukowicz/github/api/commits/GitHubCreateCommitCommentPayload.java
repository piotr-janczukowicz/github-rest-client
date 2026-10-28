package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create commit comment payload.
 *
 * @param body The contents of the comment
 * @param path Relative path of the file to comment on
 * @param position Line index in the diff to comment on
 * @param line Line number in the file to comment on
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-commit-comment/requestBody")
public record GitHubCreateCommitCommentPayload(
        String body,
        @Nullable String path,
        @Nullable Integer position,
        @Nullable Integer line) {

    public GitHubCreateCommitCommentPayload {
        requireNonNull(body, "body");
    }
}
