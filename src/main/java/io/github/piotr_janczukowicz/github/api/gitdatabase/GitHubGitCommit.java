package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Low-level Git commit operations within a repository.
 *
 * @param sha SHA for the commit
 * @param nodeId Node id
 * @param url Url
 * @param author Identifying information for the git-user
 * @param committer Identifying information for the git-user
 * @param message Message describing the purpose of the commit
 * @param tree Tree
 * @param parents Parents
 * @param verification Verification
 * @param htmlUrl Html url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-commit")
public record GitHubGitCommit(
        String sha,
        String nodeId,
        String url,
        GitHubGitCommitUser author,
        GitHubGitCommitUser committer,
        String message,
        GitHubGitCommitTree tree,
        List<GitHubGitCommitParent> parents,
        GitHubGitCommitVerification verification,
        String htmlUrl) {

    public GitHubGitCommit {
        requireNonNull(sha, "sha");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(author, "author");
        requireNonNull(committer, "committer");
        requireNonNull(message, "message");
        requireNonNull(tree, "tree");
        requireNonNull(parents, "parents");
        requireNonNull(verification, "verification");
        requireNonNull(htmlUrl, "htmlUrl");
    }
}
