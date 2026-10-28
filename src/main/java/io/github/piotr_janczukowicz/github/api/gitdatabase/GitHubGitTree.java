package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The hierarchy between files in a Git repository.
 *
 * @param sha Sha
 * @param url Url
 * @param truncated Truncated
 * @param tree Objects specifying a tree structure
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-tree")
public record GitHubGitTree(String sha, @Nullable String url, boolean truncated, List<GitHubGitTreeEntry> tree) {

    public GitHubGitTree {
        requireNonNull(sha, "sha");
        requireNonNull(tree, "tree");
    }
}
