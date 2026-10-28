package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Conditions of an Actions policy: repository, organization or enterprise conditions. When workflow path targeting is
 * available, detailed responses represent an omitted stored workflow condition as {@code workflow_path} with
 * {@code include} set to {@code ["~ALL"]} and {@code exclude} set to {@code []}. When workflow path targeting is
 * unavailable, an omitted stored condition remains omitted.
 *
 * @param organizationName Organization name patterns the policy applies to (enterprise policies)
 * @param organizationId Organization ids the policy applies to (enterprise policies)
 * @param organizationProperty Organization properties the policy applies to (enterprise policies)
 * @param repositoryName Repository name patterns the policy applies to
 * @param repositoryId Repository ids the policy applies to (organization policies)
 * @param repositoryProperty Repository properties the policy applies to
 * @param workflowPath Workflow file paths or glob patterns the policy applies to
 */
@CatalogSchema("components/schemas/actions-policy/properties/conditions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsPolicyConditions(
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName organizationName,
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId organizationId,
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty organizationProperty,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId repositoryId,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty repositoryProperty,
        @Nullable GitHubActionsPolicyWorkflowPathConditionWorkflowPath workflowPath) {}
