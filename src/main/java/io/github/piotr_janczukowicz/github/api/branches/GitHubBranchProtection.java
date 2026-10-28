package io.github.piotr_janczukowicz.github.api.branches;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Branch protection.
 *
 * @param url Url
 * @param enabled Enabled
 * @param requiredStatusChecks Required status checks
 * @param enforceAdmins Enforce admins
 * @param requiredPullRequestReviews Required pull request reviews
 * @param restrictions Restrictions
 * @param requiredLinearHistory Required linear history
 * @param allowForcePushes Allow force pushes
 * @param allowDeletions Allow deletions
 * @param blockCreations Block creations
 * @param requiredConversationResolution Required conversation resolution
 * @param name Name
 * @param protectionUrl Protection url
 * @param requiredSignatures Required signatures
 * @param lockBranch Lock branch
 * @param allowForkSyncing Allow fork syncing
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/branch-protection")
public record GitHubBranchProtection(
        @Nullable String url,
        @Nullable Boolean enabled,
        @Nullable GitHubProtectedBranchRequiredStatusCheck requiredStatusChecks,
        @Nullable GitHubProtectedBranchAdminEnforced enforceAdmins,
        @Nullable GitHubProtectedBranchPullRequestReview requiredPullRequestReviews,
        @Nullable GitHubBranchRestrictionPolicy restrictions,
        @Nullable GitHubBranchProtectionSetting requiredLinearHistory,
        @Nullable GitHubBranchProtectionSetting allowForcePushes,
        @Nullable GitHubBranchProtectionSetting allowDeletions,
        @Nullable GitHubBranchProtectionSetting blockCreations,
        @Nullable GitHubBranchProtectionSetting requiredConversationResolution,
        @Nullable String name,
        @Nullable String protectionUrl,
        @Nullable GitHubProtectedBranchRequiredSignatures requiredSignatures,
        @Nullable GitHubBranchProtectionSetting lockBranch,
        @Nullable GitHubBranchProtectionSetting allowForkSyncing) {}
