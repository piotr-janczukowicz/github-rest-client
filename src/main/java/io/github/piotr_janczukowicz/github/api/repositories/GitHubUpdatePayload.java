package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update repository.
 *
 * <p>Distinct from {@link io.github.piotr_janczukowicz.github.api.issues.GitHubUpdatePayload}.
 *
 * @param name The name of the repository.
 * @param description A short description of the repository.
 * @param homepage A URL with more information about the repository.
 * @param isPrivate Either {@code true} to make the repository private or {@code false} to make it public.
 * @param visibility The visibility of the repository.
 * @param securityAndAnalysis Security and analysis feature settings.
 * @param hasIssues Either {@code true} to enable issues for this repository or {@code false} to disable them.
 * @param hasProjects Either {@code true} to enable projects for this repository or {@code false} to disable them.
 * @param hasWiki Either {@code true} to enable the wiki for this repository or {@code false} to disable it.
 * @param hasPullRequests Either {@code true} to allow pull requests for this repository or {@code false} to prevent
 *     pull requests.
 * @param pullRequestCreationPolicy The policy that controls who can create pull requests.
 * @param isTemplate Either {@code true} to make this repo available as a template repository or {@code false} to
 *     prevent it.
 * @param defaultBranch Updates the default branch for this repository.
 * @param allowSquashMerge Either {@code true} to allow squash-merging pull requests, or {@code false} to prevent
 *     squash-merging.
 * @param allowMergeCommit Either {@code true} to allow merging pull requests with a merge commit, or {@code false} to
 *     prevent merging pull requests with merge commits.
 * @param allowRebaseMerge Either {@code true} to allow rebase-merging pull requests, or {@code false} to prevent
 *     rebase-merging.
 * @param allowAutoMerge Either {@code true} to allow auto-merge on pull requests, or {@code false} to disallow
 *     auto-merge.
 * @param deleteBranchOnMerge Either {@code true} to allow automatically deleting head branches when pull requests are
 *     merged, or {@code false} to prevent automatic deletion.
 * @param allowUpdateBranch Either {@code true} to always allow updating a pull request head branch.
 * @param useSquashPrTitleAsDefault Deprecated: this property is closing down. Please use
 *     {@code squash_merge_commit_title} instead.
 * @param squashMergeCommitTitle Default squash merge commit title.
 * @param squashMergeCommitMessage Default squash merge commit message.
 * @param mergeCommitTitle Default merge commit title.
 * @param mergeCommitMessage Default merge commit message.
 * @param archived Whether to archive this repository.
 * @param allowForking Either {@code true} to allow private forks, or {@code false} to prevent private forks.
 * @param webCommitSignoffRequired Either {@code true} to require contributors to sign off on web-based commits.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update/requestBody")
public record GitHubUpdatePayload(
        @Nullable String name,
        @Nullable String description,
        @Nullable String homepage,
        @Nullable @JsonProperty("private") Boolean isPrivate,
        @Nullable GitHubRepositoryVisibility visibility,
        @Nullable GitHubUpdateRepositorySecurityAndAnalysis securityAndAnalysis,
        @Nullable Boolean hasIssues,
        @Nullable Boolean hasProjects,
        @Nullable Boolean hasWiki,
        @Nullable Boolean hasPullRequests,
        @Nullable GitHubPullRequestCreationPolicy pullRequestCreationPolicy,
        @Nullable Boolean isTemplate,
        @Nullable String defaultBranch,
        @Nullable Boolean allowSquashMerge,
        @Nullable Boolean allowMergeCommit,
        @Nullable Boolean allowRebaseMerge,
        @Nullable Boolean allowAutoMerge,
        @Nullable Boolean deleteBranchOnMerge,
        @Nullable Boolean allowUpdateBranch,
        @Deprecated @Nullable Boolean useSquashPrTitleAsDefault,
        @Nullable GitHubSquashMergeCommitTitle squashMergeCommitTitle,
        @Nullable GitHubSquashMergeCommitMessage squashMergeCommitMessage,
        @Nullable GitHubMergeCommitTitle mergeCommitTitle,
        @Nullable GitHubMergeCommitMessage mergeCommitMessage,
        @Nullable Boolean archived,
        @Nullable Boolean allowForking,
        @Nullable Boolean webCommitSignoffRequired) {}
