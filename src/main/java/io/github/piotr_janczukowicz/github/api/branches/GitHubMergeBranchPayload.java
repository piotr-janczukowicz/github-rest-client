package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Merge branch.
 *
 * @param base The name of the base branch that the head will be merged into.
 * @param head The head to merge.
 * @param commitMessage Commit message to use for the merge commit.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/merge/requestBody")
public record GitHubMergeBranchPayload(
        String base, String head, @Nullable String commitMessage) {

    public GitHubMergeBranchPayload {
        requireNonNull(base, "base");
        requireNonNull(head, "head");
    }
}
