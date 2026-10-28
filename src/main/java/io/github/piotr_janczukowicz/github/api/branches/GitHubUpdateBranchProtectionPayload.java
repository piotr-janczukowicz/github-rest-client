package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update branch protection request body.
 *
 * @param requiredStatusChecks Require status checks to pass before merging. Set to {@code null} to disable.
 * @param enforceAdmins Enforce all configured restrictions for administrators. Set to {@code true} to enforce required
 *     status checks for repository administrators. Set to {@code null} to disable.
 * @param requiredPullRequestReviews Require at least one approving review on a pull request, before merging. Set to
 *     {@code null} to disable.
 * @param restrictions Restrict who can push to the protected branch. User, app, and team {@code restrictions} are only
 *     available for organization-owned repositories. Set to {@code null} to disable.
 * @param requiredLinearHistory Enforces a linear commit Git history, which prevents anyone from pushing merge commits
 *     to a branch.
 * @param allowForcePushes Permits force pushes to the protected branch by anyone with write access to the repository.
 * @param allowDeletions Allows deletion of the protected branch by anyone with write access to the repository.
 * @param blockCreations If set to {@code true}, the {@code restrictions} branch protection settings which limits who
 *     can push will also block pushes which create new branches, unless the push is initiated by a user, team, or app
 *     which has the ability to push.
 * @param requiredConversationResolution Requires all conversations on code to be resolved before a pull request can be
 *     merged into a branch that matches this rule.
 * @param lockBranch Whether to set the branch as read-only. If this is true, users will not be able to push to the
 *     branch.
 * @param allowForkSyncing Whether users can pull changes from upstream when the branch is locked.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-branch-protection/requestBody")
public record GitHubUpdateBranchProtectionPayload(
        OptionalNull<GitHubUpdateBranchProtectionRequiredStatusChecks> requiredStatusChecks,
        OptionalNull<Boolean> enforceAdmins,
        OptionalNull<GitHubUpdateBranchProtectionRequiredPullRequestReviews> requiredPullRequestReviews,
        OptionalNull<GitHubUpdateBranchProtectionRestrictions> restrictions,
        @Nullable Boolean requiredLinearHistory,
        @Nullable OptionalNull<Boolean> allowForcePushes,
        @Nullable Boolean allowDeletions,
        @Nullable Boolean blockCreations,
        @Nullable Boolean requiredConversationResolution,
        @Nullable Boolean lockBranch,
        @Nullable Boolean allowForkSyncing) {

    public GitHubUpdateBranchProtectionPayload {
        requireNonNull(requiredStatusChecks, "requiredStatusChecks");
        requireNonNull(enforceAdmins, "enforceAdmins");
        requireNonNull(requiredPullRequestReviews, "requiredPullRequestReviews");
        requireNonNull(restrictions, "restrictions");
    }
}
