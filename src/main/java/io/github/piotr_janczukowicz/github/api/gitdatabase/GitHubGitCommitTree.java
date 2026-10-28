package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Tree of a Git commit.
 *
 * @param sha SHA for the commit
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-commit/properties/tree")
public record GitHubGitCommitTree(String sha, String url) {

    public GitHubGitCommitTree {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
    }
}
