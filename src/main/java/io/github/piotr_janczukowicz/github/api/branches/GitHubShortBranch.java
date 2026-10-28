package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Short branch ({@code #/components/schemas/short-branch}): name, head commit and protected flag, plus the optional
 * {@code protection} and {@code protection_url}. Distinct from
 * {@link io.github.piotr_janczukowicz.github.api.commits.GitHubBranchShort} ({@code branch-short}), which has no
 * protection details.
 *
 * @param name Name
 * @param commit Commit
 * @param protectedBranch Protected
 * @param protection Protection
 * @param protectionUrl Protection url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/short-branch")
@RecordBuilder
public record GitHubShortBranch(
        String name,
        GitHubShortBranchCommit commit,
        @JsonProperty("protected") boolean protectedBranch,
        @Nullable GitHubBranchProtection protection,
        @Nullable String protectionUrl) {

    public GitHubShortBranch {
        requireNonNull(name, "name");
        requireNonNull(commit, "commit");
    }
}
