package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create authenticated user repository.
 *
 * @param name The name of the repository.
 * @param description A short description of the repository.
 * @param homepage A URL with more information about the repository.
 * @param isPrivate Whether the repository is private.
 * @param hasIssues Either {@code true} to enable issues for this repository or {@code false} to disable them.
 * @param hasProjects Either {@code true} to enable projects for this repository or {@code false} to disable them.
 * @param hasWiki Either {@code true} to enable the wiki for this repository or {@code false} to disable it.
 * @param hasDiscussions Whether discussions are enabled.
 * @param teamId The id of the team that will be granted access to this repository.
 * @param autoInit Pass {@code true} to create an initial commit with empty README.
 * @param gitignoreTemplate Desired language or platform .gitignore template to apply.
 * @param licenseTemplate Open source license template keyword.
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
 * @param squashMergeCommitTitle Default squash merge commit title.
 * @param squashMergeCommitMessage Default squash merge commit message.
 * @param mergeCommitTitle Default merge commit title.
 * @param mergeCommitMessage Default merge commit message.
 * @param hasDownloads Whether downloads are enabled.
 * @param isTemplate Either {@code true} to make this repo available as a template repository or {@code false} to
 *     prevent it.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-for-authenticated-user/requestBody")
public record GitHubCreateForAuthenticatedUserPayload(
        String name,
        @Nullable String description,
        @Nullable String homepage,
        @Nullable @JsonProperty("private") Boolean isPrivate,
        @Nullable Boolean hasIssues,
        @Nullable Boolean hasProjects,
        @Nullable Boolean hasWiki,
        @Nullable Boolean hasDiscussions,
        @Nullable Long teamId,
        @Nullable Boolean autoInit,
        @Nullable String gitignoreTemplate,
        @Nullable String licenseTemplate,
        @Nullable Boolean allowSquashMerge,
        @Nullable Boolean allowMergeCommit,
        @Nullable Boolean allowRebaseMerge,
        @Nullable Boolean allowAutoMerge,
        @Nullable Boolean deleteBranchOnMerge,
        @Nullable GitHubSquashMergeCommitTitle squashMergeCommitTitle,
        @Nullable GitHubSquashMergeCommitMessage squashMergeCommitMessage,
        @Nullable GitHubMergeCommitTitle mergeCommitTitle,
        @Nullable GitHubMergeCommitMessage mergeCommitMessage,
        @Nullable Boolean hasDownloads,
        @Nullable Boolean isTemplate) {

    public GitHubCreateForAuthenticatedUserPayload {
        requireNonNull(name, "name");
    }
}
