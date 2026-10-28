package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCommit;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubDiffEntry;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Commit comparison.
 *
 * @param url Url
 * @param htmlUrl Html url
 * @param permalinkUrl Permalink url
 * @param diffUrl Diff url
 * @param patchUrl Patch url
 * @param baseCommit Base commit
 * @param mergeBaseCommit Merge base commit
 * @param status Status
 * @param aheadBy Ahead by
 * @param behindBy Behind by
 * @param totalCommits Total commits
 * @param commits Commits
 * @param files Files
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/commit-comparison")
@RecordBuilder
public record GitHubCommitComparison(
        String url,
        String htmlUrl,
        String permalinkUrl,
        String diffUrl,
        String patchUrl,
        GitHubCommit baseCommit,
        GitHubCommit mergeBaseCommit,
        GitHubCommitComparisonStatus status,
        int aheadBy,
        int behindBy,
        int totalCommits,
        List<GitHubCommit> commits,
        @Nullable List<GitHubDiffEntry> files) {

    public GitHubCommitComparison {
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(permalinkUrl, "permalinkUrl");
        requireNonNull(diffUrl, "diffUrl");
        requireNonNull(patchUrl, "patchUrl");
        requireNonNull(baseCommit, "baseCommit");
        requireNonNull(mergeBaseCommit, "mergeBaseCommit");
        requireNonNull(status, "status");
        requireNonNull(commits, "commits");
    }
}
