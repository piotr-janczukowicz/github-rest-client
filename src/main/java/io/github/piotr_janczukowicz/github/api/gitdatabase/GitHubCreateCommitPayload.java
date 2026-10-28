package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create commit.
 *
 * @param message The commit message
 * @param tree The SHA of the tree object this commit points to
 * @param parents The full SHAs of the commits that were the parents of this commit. If omitted or empty, the commit
 *     will be written as a root commit. For a single parent, an array of one SHA should be provided; for a merge
 *     commit, an array of more than one should be provided.
 * @param author Information about the author of the commit. By default, the {@code author} will be the authenticated
 *     user and the current date. See the {@code author} and {@code committer} object below for details.
 * @param committer Information about the person who is making the commit. By default, {@code committer} will use the
 *     information set in {@code author}. See the {@code author} and {@code committer} object below for details.
 * @param signature The PGP signature of the commit. GitHub adds the signature to the {@code gpgsig} header of the
 *     created commit. For a commit signature to be verifiable by Git or GitHub, it must be an ASCII-armored detached
 *     PGP signature over the string commit as it would be written to the object database.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/create-commit/requestBody")
public record GitHubCreateCommitPayload(
        String message,
        String tree,
        @Nullable List<String> parents,
        @Nullable GitHubCreateCommitAuthor author,
        @Nullable GitHubCreateCommitCommitter committer,
        @Nullable String signature) {

    public GitHubCreateCommitPayload {
        requireNonNull(message, "message");
        requireNonNull(tree, "tree");
        if (parents != null) {
            parents = List.copyOf(parents);
        }
    }
}
