package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * File commit.
 *
 * @param content Content
 * @param commit Commit
 */
@CatalogSchema("components/schemas/file-commit")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubFileCommit(@Nullable GitHubFileCommitContent content, GitHubFileCommitResult commit) {
    public GitHubFileCommit {
        requireNonNull(commit, "commit");
    }
}
