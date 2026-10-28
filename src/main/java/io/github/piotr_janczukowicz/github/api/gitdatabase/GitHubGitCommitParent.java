package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Parent of a Git commit.
 *
 * @param sha SHA for the commit
 * @param url Url
 * @param htmlUrl Html url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-commit/properties/parents/items")
public record GitHubGitCommitParent(String sha, String url, String htmlUrl) {

    public GitHubGitCommitParent {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
    }
}
