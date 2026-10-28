package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Commit.
 *
 * @param url Url
 * @param sha Sha
 * @param nodeId Node id
 * @param htmlUrl Html url
 * @param commentsUrl Comments url
 * @param commit Commit detail
 * @param author Author
 * @param committer Committer
 * @param parents Parents
 * @param stats Stats
 * @param files Files
 */
@CatalogSchema("components/schemas/commit")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCommit(
        String url,
        String sha,
        String nodeId,
        String htmlUrl,
        String commentsUrl,
        GitHubCommitDetail commit,

        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable GitHubSimpleUser author,

        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable GitHubSimpleUser committer,

        List<GitHubCommitParent> parents,
        @Nullable GitHubCommitStats stats,
        @Nullable List<GitHubDiffEntry> files) {
    public GitHubCommit {
        requireNonNull(url, "url");
        requireNonNull(sha, "sha");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(commentsUrl, "commentsUrl");
        requireNonNull(commit, "commit");
        requireNonNull(parents, "parents");
    }
}
