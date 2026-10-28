package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Protected branch.
 *
 * @param url Url
 * @param requiredStatusChecks Required status checks
 * @param requiredPullRequestReviews Required pull request reviews
 * @param requiredSignatures Required signatures
 * @param enforceAdmins Enforce admins
 * @param requiredLinearHistory Required linear history
 * @param allowForcePushes Allow force pushes
 * @param allowDeletions Allow deletions
 * @param restrictions Restrictions
 * @param requiredConversationResolution Required conversation resolution
 * @param blockCreations Block creations
 * @param lockBranch Lock branch
 * @param allowForkSyncing Allow fork syncing
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/protected-branch")
public record GitHubProtectedBranch(
        String url,
        @Nullable GitHubStatusCheckPolicy requiredStatusChecks,
        @Nullable GitHubProtectedBranchRequiredPullRequestReviews requiredPullRequestReviews,
        @Nullable GitHubProtectedBranchRequiredSignatures requiredSignatures,
        @Nullable GitHubProtectedBranchEnforceAdmins enforceAdmins,
        @Nullable GitHubBranchProtectionSetting requiredLinearHistory,
        @Nullable GitHubBranchProtectionSetting allowForcePushes,
        @Nullable GitHubBranchProtectionSetting allowDeletions,
        @Nullable GitHubBranchRestrictionPolicy restrictions,
        @Nullable GitHubBranchProtectionSetting requiredConversationResolution,
        @Nullable GitHubBranchProtectionSetting blockCreations,
        @Nullable GitHubBranchProtectionSetting lockBranch,
        @Nullable GitHubBranchProtectionSetting allowForkSyncing) {

    public GitHubProtectedBranch {
        requireNonNull(url, "url");
    }
}
