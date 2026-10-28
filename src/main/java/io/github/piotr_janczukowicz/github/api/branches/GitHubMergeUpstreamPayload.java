package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Merge upstream branch.
 *
 * @param branch The branch to merge into the target repository.
 */
@RecordBuilder
@CatalogSchema("operations/repos/merge-upstream/requestBody")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubMergeUpstreamPayload(String branch) {

    public GitHubMergeUpstreamPayload {
        requireNonNull(branch, "branch");
    }
}
