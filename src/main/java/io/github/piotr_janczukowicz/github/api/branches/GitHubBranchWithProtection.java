package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Branch with protection.
 *
 * @param name Name
 * @param commit Commit
 * @param links Links
 * @param protectedBranch Protected
 * @param protection Protection
 * @param protectionUrl Protection url
 * @param pattern Pattern
 * @param requiredApprovingReviewCount Required approving review count
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/branch-with-protection")
public record GitHubBranchWithProtection(
        String name,
        GitHubCommit commit,
        @JsonProperty("_links") GitHubBranchWithProtectionLinks links,
        @JsonProperty("protected") boolean protectedBranch,
        GitHubBranchProtection protection,
        String protectionUrl,
        @Nullable String pattern,
        @Nullable Integer requiredApprovingReviewCount) {

    public GitHubBranchWithProtection {
        requireNonNull(name, "name");
        requireNonNull(commit, "commit");
        requireNonNull(links, "links");
        requireNonNull(protection, "protection");
        requireNonNull(protectionUrl, "protectionUrl");
    }
}
