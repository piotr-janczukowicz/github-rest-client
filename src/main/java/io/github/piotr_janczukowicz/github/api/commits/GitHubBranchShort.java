package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Branch short ({@code #/components/schemas/branch-short}): name, head commit and protected flag only, without
 * {@code protection} or {@code protection_url}. Distinct from
 * {@link io.github.piotr_janczukowicz.github.api.branches.GitHubShortBranch} ({@code short-branch}), which adds them.
 *
 * @param name Name
 * @param commit Commit
 * @param protectedBranch Protected
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/branch-short")
@RecordBuilder
public record GitHubBranchShort(
        String name,
        GitHubBranchShortCommit commit,
        @JsonProperty("protected") boolean protectedBranch) {

    public GitHubBranchShort {
        requireNonNull(name, "name");
        requireNonNull(commit, "commit");
    }
}
