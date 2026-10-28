package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Conditions for an enterprise Actions policy. The conditions object supports one organization target
 * ({@code organization_name}, {@code organization_id}, or {@code organization_property}) combined with one repository
 * target ({@code repository_name} or {@code repository_property}), and may also contain {@code workflow_path}.
 *
 * @param organizationName Organization name patterns the policy applies to
 * @param organizationId Organization ids the policy applies to
 * @param organizationProperty Organization properties the policy applies to
 * @param repositoryName Repository name patterns the policy applies to
 * @param repositoryProperty Repository properties the policy applies to
 * @param workflowPath Workflow file paths or glob patterns the policy applies to
 */
@CatalogSchema("components/schemas/actions-policy-enterprise-conditions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsPolicyEnterpriseConditions(
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName organizationName,
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId organizationId,
        @Nullable GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty organizationProperty,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty repositoryProperty,
        @Nullable GitHubActionsPolicyWorkflowPathConditionWorkflowPath workflowPath) {
    private static final int REQUIRED_TARGET_COUNT = 1;

    public GitHubActionsPolicyEnterpriseConditions {
        final int organizationTargets = (organizationName == null ? 0 : 1)
                + (organizationId == null ? 0 : 1)
                + (organizationProperty == null ? 0 : 1);
        if (organizationTargets != REQUIRED_TARGET_COUNT) {
            throw new IllegalArgumentException(
                    "exactly one of organizationName, organizationId or organizationProperty is required");
        }
        final int repositoryTargets = (repositoryName == null ? 0 : 1) + (repositoryProperty == null ? 0 : 1);
        if (repositoryTargets != REQUIRED_TARGET_COUNT) {
            throw new IllegalArgumentException("exactly one of repositoryName or repositoryProperty is required");
        }
    }
}
