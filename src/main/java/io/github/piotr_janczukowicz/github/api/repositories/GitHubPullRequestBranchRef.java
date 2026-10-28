package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pull request branch reference.
 *
 * @param label Label
 * @param ref Ref
 * @param repo Repo
 * @param sha Sha
 * @param user User
 */
@CatalogSchema("components/schemas/pull-request-simple/properties/head")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestBranchRef(
        String label,
        String ref,
        GitHubRepository repo,
        String sha,
        @Nullable OptionalNull<GitHubSimpleUser> user) {
    public GitHubPullRequestBranchRef {
        requireNonNull(label, "label");
        requireNonNull(ref, "ref");
        requireNonNull(repo, "repo");
        requireNonNull(sha, "sha");
    }
}
