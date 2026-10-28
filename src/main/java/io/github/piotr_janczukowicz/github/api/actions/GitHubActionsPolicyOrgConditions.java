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
 * Conditions for an organization Actions policy. The conditions object should contain one of {@code repository_name},
 * {@code repository_id}, or {@code repository_property}, and may also contain {@code workflow_path}.
 *
 * @param repositoryName Repository name patterns the policy applies to
 * @param repositoryId Repository ids the policy applies to
 * @param repositoryProperty Repository properties the policy applies to
 * @param workflowPath Workflow file paths or glob patterns the policy applies to
 */
@CatalogSchema("components/schemas/actions-policy-org-conditions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubActionsPolicyOrgConditions(
        @Nullable GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId repositoryId,
        @Nullable GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty repositoryProperty,
        @Nullable GitHubActionsPolicyWorkflowPathConditionWorkflowPath workflowPath) {
    private static final int REQUIRED_TARGET_COUNT = 1;

    public GitHubActionsPolicyOrgConditions {
        final int targets = (repositoryName == null ? 0 : 1)
                + (repositoryId == null ? 0 : 1)
                + (repositoryProperty == null ? 0 : 1);
        if (targets != REQUIRED_TARGET_COUNT) {
            throw new IllegalArgumentException(
                    "exactly one of repositoryName, repositoryId or repositoryProperty is required");
        }
    }
}
