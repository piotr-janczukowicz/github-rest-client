package io.github.piotr_janczukowicz.github.api.deployments;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The type of deployment branch policy for this environment. To allow all branches to deploy, set to {@code null}.
 *
 * @param protectedBranches Whether only branches with branch protection rules can deploy to this environment. If
 *     {@code protected_branches} is {@code true}, {@code custom_branch_policies} must be {@code false}; if
 *     {@code protected_branches} is {@code false}, {@code custom_branch_policies} must be {@code true}.
 * @param customBranchPolicies Whether only branches that match the specified name patterns can deploy to this
 *     environment. If {@code custom_branch_policies} is {@code true}, {@code protected_branches} must be {@code false};
 *     if {@code custom_branch_policies} is {@code false}, {@code protected_branches} must be {@code true}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/deployment-branch-policy-settings")
@RecordBuilder
public record GitHubDeploymentBranchPolicySettings(boolean protectedBranches, boolean customBranchPolicies) {}
